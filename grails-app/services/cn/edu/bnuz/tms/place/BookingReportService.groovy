package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.card.CardReissueOrderCommand;
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingReportCommand;
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.place.BookingForm;
import cn.edu.bnuz.tms.place.BookingReport;

class BookingReportService {
	def total() {
		BookingReport.count()	
	}
	
	def list(int offset, int max) {
		BookingReport.executeQuery '''
select new map(
  br.id as reportId,
  cr.name as creator,
  br.dateCreated as dateCreated,
  mo.name as modifier,
  br.dateModified as dateModified
) from BookingReport br
join br.creator as cr
left join br.modifier as mo
order by br.id desc
''', [offset:offset, max: max]
	}
	
	def findUnprocessed(Term term) {
		BookingForm.executeQuery '''
select new map(
  bf.id as bookingId,
  bf.userName as userName,
  bf.phoneNumber as phoneNumber,
  dept.name as department,
  bf.reason as reason,
  ck.name as checker,
  bf.dateChecked as dateChecked,
  ap.name as approver,
  bf.dateApproved as dateApproved,
  bf.status as status,
  0 as flag
)from BookingForm bf
join bf.department dept
join bf.checker ck
join bf.approver ap
where bf.report is null
and bf.status = :status
''', [status: BookingForm.STATUS_APPROVED]
	}
	
	BookingReport create(String teacherId, BookingReportCommand brc) {
		BookingReport report = new BookingReport(
			creator: Teacher.load(teacherId)
		)

		brc.addedItems.each {
			report.addToBookings(BookingForm.load(it))
		}
		
		report.save(failOnError:true)
	}
	
	def getInfo(Long id) {
		def result = BookingReport.executeQuery """
select new map(
  br.id as reportId,
  cr.name as creator,
  br.dateCreated as dateCreated,
  mo.name as modifier,
  br.dateModified as dateModified
)
from BookingReport br
join br.creator cr
left join br.modifier mo
where br.id = :id
""", [id: id]
		if(result.size() > 0) {
			def report = result[0]
			report.items = BookingForm.executeQuery """
select new map(
  bf.id as bookingId,
  bf.userName as userName,
  bf.phoneNumber as phoneNumber,
  dept.name as department,
  bf.reason as reason,
  ck.name as checker,
  bf.dateChecked as dateChecked,
  ap.name as approver,
  bf.dateApproved as dateApproved,
  bf.status as status,
  1 as flag
)from BookingForm bf
join bf.department dept
join bf.checker ck
join bf.approver ap
join bf.report re
where re.id = :id
""", [id: id]
			return report
		} else {
			return null
		}
	}
	
	boolean update(String teacherId, BookingReportCommand brc) {
		BookingReport report = BookingReport.get(brc.reportId)

		report.modifier = Teacher.load(teacherId)
		report.dateModified = new Date()

		brc.addedItems.each {
			report.addToBookings(BookingForm.load(it))
		}

		brc.removedItems.each {
			report.removeFromBookings(BookingForm.load(it))
		}
		
		report.save(failOnError:true)

		return true
	}
	
	def export(Long id) {
		def results = BookingReport.executeQuery '''
select new map(
  bf.id as formId,
  rm.building as building,
  rm.name as room,
  rm.openGroup as openGroup,
  bi.startWeek as startWeek,
  bi.endWeek as endWeek,
  bi.dayOfWeek as dayOfWeek,
  bi.sectionType as sectionType,
  dp.name as department,
  bf.userName as userName,
  bf.phoneNumber as userPhone,
  ck.name as checker,
  ck.officePhone as checkerPhone,
  bf.reason as reason,
  bf.status as status
)
from BookingReport br
join br.bookings bf
join bf.items bi
join bi.room rm
join bf.checker ck
join bf.department dp
where br.id = :id
''', [id: id]
		return [
			reportId: id,
			items: results
		]
	}
}
