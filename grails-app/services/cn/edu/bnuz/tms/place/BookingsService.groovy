package cn.edu.bnuz.tms.place

import java.util.Date;

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingFormCommand;
import cn.edu.bnuz.tms.teaching.SectionService
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.place.BookingAudit;
import cn.edu.bnuz.tms.place.BookingForm;
import cn.edu.bnuz.tms.place.BookingItem;
import cn.edu.bnuz.tms.place.BookingType;
import groovy.sql.Sql

class BookingsService {
	def dataSource_es
	SectionService sectionService
	
	/**
	 * 获取指定用户ID的申请单用于显示
	 * @param userId 用户ID
	 * @return 申请单列表
	 */
	def list(String userId, int offset, int max) {
		BookingForm.executeQuery '''
select new map(
  b.id as formId,
  d.name as department,
  t.name as type,
  b.reason as reason,
  b.dateModified as dateModified,
  ck.name as checker,
  b.dateChecked as dateChecked,
  ap.name as approver,
  b.dateApproved as dateApproved,
  b.status as status
)
from BookingForm b
join b.department d
join b.type t
left join b.checker ck
left join b.approver ap
where b.userId = :userId
order by dateModified desc
''', [userId: userId], [offset:offset, max: max]
	}
	
	/**
	 * 获取用户申请单数量
	 * @param userId 用户ID
	 * @return 数量
	 */
	def total(String userId) {
		return BookingForm.countByUserId(userId)
	}
	
	/**
	 * 根据部门查询申请类型
	 * @param departmentId 部门ID
	 * @return 申请类型列表
	 */
	def findBookingTypes(String departmentId) {
		def department = Department.get(departmentId)
		if(department.isAdminDept) { // 行政部门
			BookingType.executeQuery '''
select new map(bt.id as id , bt.name as name)
from BookingType bt
where bt.isAdminDept = true
and exists (
  from BookingAuth ba
  where ba.type = bt
  and ba.department.id = :departmentId
)
''', [departmentId: departmentId]
		} else { // 教学部门
			BookingType.executeQuery '''
select new map(bt.id as id , bt.name as name)
from BookingType bt
where bt.isAdminDept = false
'''
		}
	}
	
	/**
	 * 获取申请单信息，用于显示
	 * @param id 申请ID
	 * @return 申请信息
	 */
	def getInfo(Long id) {
		def results = BookingForm.executeQuery '''
select new map(
  b.id as formId,
  term.id as term,
  b.userId as userId,
  b.userName as userName,
  b.phoneNumber as phoneNumber,
  d.id as departmentId,
  d.name as departmentName,
  t.id as typeId,
  t.name as typeName,
  b.reason as reason,
  b.dateCreated as dateCreated,
  b.dateModified as dateModified,
  ck.name as checker,
  b.dateChecked as dateChecked,
  ap.name as approver,
  b.dateApproved as dateApproved,
  b.status as status
)
from BookingForm b
join b.department d
join b.type t
join b.term term
left join b.checker ck
left join b.approver ap
where b.id = :id
''', [id: id]
		if(!results) {
			return null
		}

		def form = results[0]
		form.items = BookingItem.executeQuery '''
select new map(
  i.id as itemId,
  r.id as roomId,
  r.name as roomName,
  r.seat as roomSeat,
  r.type as roomType,
  i.startWeek as startWeek,
  i.endWeek as endWeek,
  i.dayOfWeek as dayOfWeek,
  i.sectionType as sectionType,
  i.occupied as occupied
)
from BookingItem i
join i.room r
where i.form.id = :formId
''', [formId: id]

		form.audits = BookingAudit.executeQuery '''
select new map(
  userName as userName,
  action as action,
  content as content,
  date as date
)
from BookingAudit a
where a.form.id = :formId
''', [formId: id]

		return form
	}

	/**
	 * 创建申请单
	 * @param userId
	 * @param userName
	 * @param term
	 * @param pbfc
	 * @return
	 */
	def create(String userId, String userName, String phoneNumber, Term term, BookingFormCommand pbfc) {
		def now = new Date()

		BookingForm form = new BookingForm(
				userId: userId,
				userName: userName,
				phoneNumber: phoneNumber,
				term: term,
				department: Department.load(pbfc.departmentId),
				type: BookingType.load(pbfc.bookingTypeId),
				reason: (pbfc.reason),
				dateCreated: now,
				dateModified: now,
				status: BookingForm.STATUS_APPLYING
				)

		pbfc.addedItems.each { item ->
			form.addToItems(new BookingItem(
				room:         Room.load(item.roomId),
				startWeek:    item.startWeek,
				endWeek:      item.endWeek,
				dayOfWeek:    item.dayOfWeek,
				sectionType:  item.sectionType,
				occupied:     false,
			))
		}

		form.addToAudits(new BookingAudit(
				userId: userId,
				userName: userName,
				action: BookingAudit.ACTION_COMMIT,
				date: now
				))

		form.save(failOnError:true)
	}

	def update(String userId, BookingFormCommand cmd) {
		BookingForm form = BookingForm.get(cmd.formId)
		if(!form || form.userId != userId || !form.allowUpdate()) {
			return false
		}

		def now = new Date()

		form.department = Department.load(cmd.departmentId)
		form.type = BookingType.load(cmd.bookingTypeId)
		form.reason = cmd.reason
		form.dateModified = now

		cmd.addedItems.each { item ->
			def bookingItem = new BookingItem(
				room:         Room.load(item.roomId),
				startWeek:    item.startWeek,
				endWeek:      item.endWeek,
				dayOfWeek:    item.dayOfWeek,
				sectionType:  item.sectionType,
				occupied:     false,
			)
			form.addToItems(bookingItem)
		}

		cmd.removedItems.each {
			def bookingItem = BookingItem.load(it.itemId)
			form.removeFromItems(bookingItem)
			bookingItem.delete()
		}

		form.addToAudits(new BookingAudit(
				userId: userId,
				userName: form.userName,
				action: BookingAudit.ACTION_MODIFY,
				date: now
				))

		form.save(failOnError:true)

		return true
	}
	
	def delete(Long id) {
		BookingForm form = BookingForm.get(id);
		if(form.allowDelete()) {
			form.delete(flush:true);
			return true
		} else {
			return false
		}
	}

	def changeStatus(String userId, Long id, Integer status) {
		def form = BookingForm.get(id)
		if(!form || !form.allowStatus(status) || form.userId != userId) {
			return false
		}
		// 审核、不批准和批准执行其它Controller
		if(status == BookingForm.STATUS_CHECKED ||
		   status == BookingForm.STATUS_REJECTED ||
		   status == BookingForm.STATUS_APPROVED) {
			return false
		}
		def action
		switch(status) {
			case BookingForm.STATUS_APPLYING:
				action = BookingAudit.ACTION_COMMIT
				break;
			case BookingForm.STATUS_NEW:
				action = BookingAudit.ACTION_CANCEL
				break;
		}
		def now = new Date()

		form.status = status
		form.dateModified = now

		form.addToAudits(new BookingAudit(
				userId: userId,
				userName: form.userName,
				action: action,
				date: now
				))

		form.save(failOnError:true)
		return true
	}

	/**
	 * 获取用户其它信息
	 * @param checkerId 审核人ID
	 * @param checkerDepartmentId 审核人所在部门ID
	 * @param applicantId 申请人ID
	 * @return 其它信息
	 */
	def getUserExtraInfo(String checkerId, String checkerDepartmentId, String applicantId) {
		def extraInfo = []
		if(applicantId.length() == Student.ID_LEN) {
			Student student = Student.get(applicantId)
			if(student.adminClass.department.id != checkerDepartmentId) {
				extraInfo << student.adminClass.department.name
			}
			extraInfo << student.adminClass.name
			extraInfo << applicantId
		} else {
			Teacher teacher = Teacher.get(applicantId)
			if(teacher.department.id != checkerDepartmentId) {
				extraInfo << teacher.department.name
			}
		}
		return extraInfo
	}


	def findRoomTypes(boolean isTeacher) {
		Sql sql =  new Sql(dataSource_es)
		sql.rows """
select distinct type
from room
where ((user_type = '11' and 0 = :isTeacher) or
      ((user_type = '01' or user_type = '11') and 1 = :isTeacher))
order by type
""", [isTeacher: isTeacher ? 1 : 0]
	}

	def findRooms(Term term, boolean isTeacher, int startWeek,
			int endWeek, int dayOfWeek, int sectionType, String type) {
		def section = sectionService.getSection(sectionType)
		Sql sql =  new Sql(dataSource_es)
		def rooms = sql.rows """
select id, name, seat 
from room 
where id in (
  select id
  from room
  where ((user_type = '11' and 0 = :isTeacher) or
        ((user_type = '01' or user_type = '11') and 1 = :isTeacher))
    and type = :type
    and (enabled = 1 or (enabled = 0 and booking_term = :termId))
  minus
  select room_id
  from place_usage 
  where term = :termId
    and day_of_week = :dayOfWeek 
    and (start_section between :startSection and :endSection
      or start_section + total_section - 1 between :start_section and :endSection
      or :startSection between start_section and start_section + total_section - 1
      or :endSection between start_section and start_section + total_section - 1)
	and exists (
      select * from (
        select Rownum r from dual Connect By Rownum <= 30
      ) where r between :startWeek and :endWeek
      intersect
      select * from (
        select Rownum r From dual Connect By Rownum <= 30
      ) where r between start_week and end_week
      and (odd_even = 0 or (odd_even = 1 and mod(r, 2) = 1) or (odd_even = 2 and mod(r, 2) = 0))
    )
)
order by name
""", [
			termId:term.id.toString(),
			startWeek: startWeek,
			endWeek: endWeek,
			dayOfWeek: dayOfWeek.toString(),
			startSection: section.start,
			endSection: section.start + section.total - 1,
			type: type,
			isTeacher: isTeacher ? 1 : 0
		]
		
		def roomIds = rooms.collect {it.ID}
		def items =	BookingForm.executeQuery '''
select new map(
  r.id as roomId,
  bi.sectionType as sectionType
)
from BookingForm b
join b.items bi
join bi.room r
where b.term = :term
and bi.dayOfWeek = :dayOfWeek
and (bi.startWeek <= :startWeek and bi.endWeek >= :startWeek or bi.endWeek >= :endWeek and bi.startWeek <= :endWeek) 
and r.id in(:roomIds)
and b.status in (:status)
''', [term: term, startWeek: startWeek, endWeek: endWeek, dayOfWeek: dayOfWeek, roomIds: roomIds, 
		status: [BookingForm.STATUS_APPLYING, BookingForm.STATUS_CHECKED]]
		
		rooms.each { room ->
			def count = 0;
			items.each { item ->
				if(item.roomId == room.ID && sectionService.isConfilict(item.sectionType, sectionType)) {
					count++;
				}
			}
			room.RESERVED_COUNT = count
		}
		
		return rooms
	}
			
	def export(String userId, Date start, Date end) {
		BookingForm.executeQuery '''
select new map(
  form.id as formId,
  form.term.id as term,
  item.startWeek as startWeek,
  item.endWeek as endWeek,
  item.dayOfWeek as dayOfWeek,
  item.sectionType as sectionType,
  room.name as room,
  form.status as status,
  type.name as type,
  dept.name as department,
  form.userName as userName,
  form.dateCreated as dateCreated,
  form.reason as reason
)
from BookingForm form
join form.department dept
join form.type type
join form.items item
join item.room room
where cast(form.dateCreated as date) between :start and :end
and form.userId = :userId
and form.status != 0
order by form.id, item.startWeek, item.dayOfWeek, item.sectionType, room.name
''', [userId: userId, start: start, end: end]
	}
}
