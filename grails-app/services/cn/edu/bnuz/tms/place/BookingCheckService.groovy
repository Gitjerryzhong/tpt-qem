package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingAudit;
import cn.edu.bnuz.tms.place.BookingForm;

class BookingCheckService {
	private static processedActions = [BookingAudit.ACTION_CHECK_YES, BookingAudit.ACTION_CHECK_NO]
	private static pendingStatus = BookingForm.STATUS_APPLYING
	
	/**
	 * 获取审核人统计数据
	 * @param teacherId
	 * @return [pending:等审核, process:已审核]
	 */
	def getStatis(String teacherId) {
		def statis = [(0): 0, (1): 0]
		def results = BookingForm.executeQuery '''
select count(*)
from BookingForm form
join form.type type
join form.department dept
join type.auths auth 
where auth.department = dept
and auth.checker.id = :teacherId
and form.status = :status
''',[teacherId: teacherId, status: pendingStatus]
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
	 * 查询待审核表单
	 * @param teacherId 教师ID
	 * @param offset 分页偏移量
	 * @param max 每页记录
	 * @return 待审核表单列表
	 */
	def findPendingForms(String teacherId, int offset, int max) {
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
join type.auths auth 
where auth.department = dept
and auth.checker.id = :teacherId
and form.status = :status
order by form.dateModified
''',[teacherId: teacherId, status: pendingStatus], [offset: offset, max: max]
	}

	/**
	 * 获取下一个待审核表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 下一个待审核表单ID
	 */
	def nextPending(String teacherId, Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
join form.type type
join form.department dept
join type.auths auth 
where auth.department = dept
and auth.checker.id = :teacherId
and form.status = :status
and form.dateModified > (select dateModified from BookingForm where id = :formId)
order by form.dateModified asc
''', [teacherId: teacherId, formId: formId, status: pendingStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 获取上一个待审核表单ID
	 * @param teacherId 教师ID
	 * @param formId 当前表单ID
	 * @return 上一个待审核表单ID
	 */
	def prevPending(String teacherId, Long formId) {
		def results = BookingForm.executeQuery '''
select form.id 
from BookingForm form
join form.type type
join form.department dept
join type.auths auth 
where auth.department = dept
and auth.checker.id = :teacherId
and form.status = :status
and form.dateModified < (select dateModified from BookingForm where id = :formId)
order by form.dateModified desc
''', [teacherId: teacherId, formId: formId, status: pendingStatus], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}

	/**
	 * 判断教师对申请单是否有审核权限
	 * @param teacherId 教师ID
	 * @param formId 申请ID
	 * @return 是否有权限
	 */
	boolean canCheckForm(String teacherId, Long formId) {
		BookingForm.executeQuery '''
select count(*)
from BookingForm form
join form.type type
join form.department dept
join type.auths auth 
where auth.department = dept
and auth.checker.id = :teacherId 
and form.id = :formId
''', [teacherId: teacherId, formId: formId]
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
  form.dateChecked as date,
  dept.name as department,
  form.reason as reason,
  type.name as type,
  form.status as status
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
order by form.dateChecked desc
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
and form.dateChecked < (select dateChecked from BookingForm where id = :formId)
order by form.dateChecked desc
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
and form.dateChecked > (select dateChecked from BookingForm where id = :formId)
order by form.dateChecked asc
''', [teacherId: teacherId, actions: processedActions, formId: formId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	/**
	 * 是否可以查看，如果之前审核过该申请表，返回true
	 * @param teacherId 教师ID
	 * @param formId 申请ID
	 * @return 是否可以查看
	 */
	boolean canViewForm(String teacherId, Long formId) {
		BookingForm.executeQuery '''
select count(*)
from BookingForm form
join form.audits audit
where audit.userId = :teacherId 
and form.id = :formId
''',[teacherId: teacherId, formId: formId]
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
	def changeStatus(String userId, String userName,
			Long formId, boolean approved, String comment) {
		def form = BookingForm.get(formId)
		def status = approved ? BookingForm.STATUS_CHECKED : BookingForm.STATUS_REJECTED
		if(!form || !form.allowStatus(status)) {
			return false
		}

		def now = new Date()

		form.checker = Teacher.load(userId)
		form.dateChecked = now
		form.status = status
		
		form.addToAudits(new BookingAudit(
				userId: userId,
				userName: userName,
				action: approved ? BookingAudit.ACTION_CHECK_YES : BookingAudit.ACTION_CHECK_NO,
				date: now,
				content: comment
				))

		form.save(failOnError:true)
		return true
	}
}
