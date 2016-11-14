package cn.edu.bnuz.tms.place

import groovy.sql.Sql
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingApproveCommand;
import cn.edu.bnuz.tms.teaching.SectionService
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.place.BookingAudit;
import cn.edu.bnuz.tms.place.BookingForm;

class BookingApproveService {
	private static processedActions = [
		BookingAudit.ACTION_APPROVE_YES, 
		BookingAudit.ACTION_APPROVE_NO, 
		BookingAudit.ACTION_CLOSE
	]
	private static pendingStatus = BookingForm.STATUS_CHECKED
	private static uncheckedStatus = BookingForm.STATUS_APPLYING
	
	def dataSource_es
	def messageSource
	SectionService sectionService
	
	/**
	 * 获取审核人统计数据
	 * @param teacherId
	 * @return [unchecked:待审核, pending:待审批, process:已审核]
	 */
	def getStatis(String teacherId) {
		def statis = [(-1):0, (0): 0, (1): 0]
		
		def results = BookingForm.executeQuery '''
select count(*) from BookingForm where status = :status
''', [status: uncheckedStatus]
		if(results) {
			statis[-1] = results[0]
		}
		
		results = BookingForm.executeQuery '''
select count(*) from BookingForm where status = :status
''',[status: pendingStatus]
		if(results) {
			statis[0] = results[0]
		}
		
		results = BookingForm.executeQuery '''
select count(*)
from BookingForm form
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
)''',[teacherId: teacherId, actions: processedActions]
		if(results) {
			statis[1] = results[0]
		}
		return statis
	}
	
	/**
	 * 查询待审批表单
	 * @param teacherId 教师ID
	 * @param offset 分页偏移量
	 * @param max 每页记录
	 * @return 待审核表单列表
	 */
	def findUncheckedForms(int offset, int max) {
		BookingForm.executeQuery '''
select new map(
  form.id as formId,
  form.userId as userId,
  form.userName as userName,
  form.dateModified as date,
  dept.name as department,
  form.reason as reason,
  type.name as type
)
from BookingForm form
join form.type type
join form.department dept 
where form.status = :status
order by form.dateModified
''',[status: uncheckedStatus], [offset: offset, max: max]
	}
	
	/**
	 * 获取下一个待审核表单ID
	 * @param formId 当前表单ID
	 * @return 下一个待审核表单ID
	 */
	def nextUnchecked(Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where form.status = :status
and form.dateModified > (select dateModified from BookingForm where id = :formId)
order by form.dateModified asc
''', [formId: formId, status: uncheckedStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 获取上一个待审核表单ID
	 * @param formId 当前表单ID
	 * @return 上一个待审核表单ID
	 */
	def prevUnchecked(Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where form.status = :status
and form.dateModified < (select dateModified from BookingForm where id = :formId)
order by form.dateModified desc
''', [formId: formId, status: uncheckedStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	
	/**
	 * 查询待审批表单
	 * @param teacherId 教师ID
	 * @param offset 分页偏移量
	 * @param max 每页记录
	 * @return 待审核表单列表
	 */
	def findPendingForms(int offset, int max) {
		BookingForm.executeQuery '''
select new map(
  form.id as formId,
  form.userId as userId,
  form.userName as userName,
  form.dateChecked as date,
  dept.name as department,
  form.reason as reason,
  type.name as type
)
from BookingForm form
join form.type type
join form.department dept 
where form.status = :status
order by form.dateChecked
''',[status: pendingStatus], [offset: offset, max: max]
	}

	/**
	 * 获取下一个待审批表单ID
	 * @param formId 当前表单ID
	 * @return 下一个待审批表单ID
	 */
	def nextPending(Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where form.status = :status
and form.dateChecked > (select dateChecked from BookingForm where id = :formId)
order by form.dateChecked asc
''', [formId: formId, status: pendingStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 获取上一个待审批表单ID
	 * @param formId 当前表单ID
	 * @return 上一个待审批表单ID
	 */
	def prevPending(Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where form.status = :status
and form.dateChecked < (select dateChecked from BookingForm where id = :formId)
order by form.dateChecked desc
''', [formId: formId, status: pendingStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	/**
	 * 查询已审核申请单
	 * @param teacherId 教师ID
	 * @return 申请单列表
	 */
	def getProcessedForms(String teacherId, int offset, int max) {
		BookingForm.executeQuery '''
select new map(
  form.id as formId,
  form.userId as userId,
  form.userName as userName,
  form.dateApproved as date,
  dept.name as department,
  form.reason as reason,
  type.name as type,
  form.status as status,
  form.report.id as reportId
)
from BookingForm form
join form.type type
join form.department dept
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
) 
order by form.dateApproved desc
''',[teacherId: teacherId, actions: processedActions], [offset: offset, max: max]
	}
	
	/**
	 * 获取下一个已审核表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 下一个已审核表单ID
	 */
	def nextProcessed(String teacherId, Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
)
and form.dateApproved < (select dateApproved from BookingForm where id = :formId)
order by form.dateApproved desc
''', [teacherId: teacherId, actions: processedActions, formId: formId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 获取上一个已审核表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 上一个已审核表单ID
	 */
	def prevProcessed(String teacherId, Long formId) {
		def results = BookingForm.executeQuery '''
select form.id
from BookingForm form
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
)
and form.dateApproved > (select dateApproved from BookingForm where id = :formId)
order by form.dateApproved asc
''', [teacherId: teacherId, actions: processedActions, formId: formId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	/**
	 * 按条件查询记录数量
	 * @param teacherId
	 * @param query
	 * @return 记录数量
	 */
	def getSearchCount(String teacherId, String query) {
		def result = BookingForm.executeQuery '''
select count(*)
from BookingForm form
join form.type type
join form.department dept
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
) and (form.userName = :query)
order by form.dateApproved desc
''',[teacherId: teacherId, actions: processedActions, query: query]
		return result[0]
	}
	
	/**
	 * 按条件查询已审核申请单
	 * @param teacherId 教师ID
	 * @return 申请单列表
	 */
	def searchProcessedForms(String teacherId, int offset, int max, String query) {
		BookingForm.executeQuery '''
select new map(
  form.id as formId,
  form.userId as userId,
  form.userName as userName,
  form.dateApproved as date,
  dept.name as department,
  form.reason as reason,
  type.name as type,
  form.status as status,
  form.report.id as reportId
)
from BookingForm form
join form.type type
join form.department dept
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
) and (form.userName = :query)
order by form.dateApproved desc
''',[teacherId: teacherId, actions: processedActions, query: query], [offset: offset, max: max]
	}

	/**
	 * 获取下一个查询核表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 下一个已审核表单ID
	 */
	def nextSearch(String teacherId, Long formId, String query) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
)
and form.dateApproved < (select dateApproved from BookingForm where id = :formId)
and (form.userName = :query)
order by form.dateApproved desc
''', [teacherId: teacherId, actions: processedActions, formId: formId, query: query], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 获取上一个查询表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 上一个已审核表单ID
	 */
	def prevSearch(String teacherId, Long formId, String query) {
		def results = BookingForm.executeQuery '''
select form.id
from BookingForm form
where exists (
  from BookingAudit audit
  where audit.form = form
  and audit.userId = :teacherId
  and audit.action in(:actions)
)
and form.dateApproved > (select dateApproved from BookingForm where id = :formId)
and (form.userName = :query)
order by form.dateApproved asc
''', [teacherId: teacherId, actions: processedActions, formId: formId, query: query], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	/**
	 * 修改状态
	 * @param userId 修改人用户ID
	 * @param userName 修改人用户名
	 * @param formId 表单ID
	 * @param approved 是否通过
	 * @param comment 备注
	 * @return 是否成功
	 */
	def changeStatus(String userId, String userName, Long formId, BookingApproveCommand bac) {
		def form = BookingForm.get(formId)
		if(!form || !form.allowStatus(bac.status)) {
			return false
		}

		def now = new Date()
		def action = null

		switch(bac.status) {
			case BookingForm.STATUS_APPROVED:
				form.approver = Teacher.load(userId)
				form.dateApproved = now
				action = BookingAudit.ACTION_APPROVE_YES
				break;
			case BookingForm.STATUS_REJECTED:
				form.approver = Teacher.load(userId)
				form.dateApproved = now
				action = BookingAudit.ACTION_APPROVE_NO
				break;
			case BookingForm.STATUS_CLOSED:
				form.approver = Teacher.load(userId)
				form.dateApproved = now
				action = BookingAudit.ACTION_CLOSE
				break;
			case BookingForm.STATUS_REVOKED:
				deleteFromEs(form)
				action = BookingAudit.ACTION_REVOKE
				break;
		}
		
		form.status = bac.status
		
		form.addToAudits(new BookingAudit(
			userId: userId,
			userName: userName,
			action: action,
			date: now,
			content: bac.comment
		))
		
		if(bac.occupied) {
			bac.occupied.each { itemId ->
				def item = form.items.find {it.id == itemId}
				if(item) {
					item.occupied = true
				}
			}
		}

		form.save(failOnError:true)
		
		switch(bac.status) {
			case BookingForm.STATUS_APPROVED:
				insertIntoEs(form)
				break;
		}
		return true
	}
	
	/**
	 * 插入到教务系统
	 * @param form 申请
	 */
	def insertIntoEs(BookingForm form) {
		Sql sql =  new Sql(dataSource_es)
		def year = form.term.esYear
		def term = form.term.esTerm.toString()
		form.items.each { item ->
			def chinese = messageSource.getMessage("tms.booking.section.${item.sectionType}", null, Locale.CHINA)
			sectionService.getSubSections(item.sectionType).each { section ->
				sql.execute '''
insert into zfxfzb.jxcdyyb(
xn, xq, jsbh, jsmc, ksz, jsz, xqj, 
sjd, skcd, jydw, dh, jyjs, sjgr, grdh, 
jyly, jysj, zwsjd, ly, xuh, shr, bz
)values(
?,?,?,?,?,?,?,
?,?,?,?,?,?,?,
?,?,?,?,?,?,?
)''', [
year, term, item.room.id, item.room.name, item.startWeek, item.endWeek, item.dayOfWeek,
section.start, section.total, form.department.name, form.checker.officePhone, form.userId, form.userName, form.phoneNumber,
form.reason, form.dateApproved.format("yyyy-MM-dd HH:mm:ss"), chinese, 'Tm', form.id, form.approver.id, 4
]
			}
		}
	}
	
	/**
	 * 从教务系统中删除
	 * @param form 申请
	 */
	def deleteFromEs(BookingForm form) {
		Sql sql = new Sql(dataSource_es)
		sql.execute "delete from zfxfzb.jxcdyyb where xuh = ? and ly = 'Tm'", [form.id]
	}
			
	def checkConflict(Term term, form) {
		Sql sql =  new Sql(dataSource_es)
		form.items.each {item ->
			if(!item.occupied) {
				def section = sectionService.getSection(item.sectionType)
				def rows = sql.rows """
select room_id
from place_usage 
where term = :termId
and room_id = :roomId
and day_of_week = :dayOfWeek 
and start_section in (${section.subs.join(',')})
and exists (
  select * from (select Rownum r From dual Connect By Rownum <= 30) where r between :startWeek and :endWeek
  intersect
  select * from (select Rownum r From dual Connect By Rownum <= 30) where r between start_week and end_week
  and (odd_even = 0 or (odd_even = 1 and mod(r, 2) = 1) or (odd_even = 2 and mod(r, 2) = 0))
)""", [termId:term.id.toString(), roomId: item.roomId, startWeek: item.startWeek, endWeek: item.endWeek, dayOfWeek: item.dayOfWeek]
				if(rows.size() > 0) {
					item.occupied = true // 标记已占用
				}
			}
		}
	}
}
