package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingAudit;
import cn.edu.bnuz.tms.place.BookingForm;

class BookingCheckService {
	private static processedActions = [BookingAudit.ACTION_CHECK_YES, BookingAudit.ACTION_CHECK_NO]
	private static pendingStatus = BookingForm.STATUS_APPLYING
	
	/**
	 * ��ȡ�����ͳ������
	 * @param teacherId
	 * @return [pending:�����, process:�����]
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
	 * ��ѯ����˱�
	 * @param teacherId ��ʦID
	 * @param offset ��ҳƫ����
	 * @param max ÿҳ��¼
	 * @return ����˱��б�
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
	 * ��ȡ��һ������˱�ID
	 * @param teacherId ��ʦID
	 * @param formId ��ǰ��ID
	 * @return ��һ������˱�ID
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
	 * ��ȡ��һ������˱�ID
	 * @param teacherId ��ʦID
	 * @param formId ��ǰ��ID
	 * @return ��һ������˱�ID
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
	 * �жϽ�ʦ�����뵥�Ƿ������Ȩ��
	 * @param teacherId ��ʦID
	 * @param formId ����ID
	 * @return �Ƿ���Ȩ��
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
	 * ��ѯ��������뵥
	 * @param teacherId ��ʦID
	 * @return ���뵥�б�
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
	 * ��ȡ��һ������˱�ID
	 * @param teacherId ��ʦID
	 * @param formId ��ǰ��ID
	 * @return ��һ������˱�ID
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
	 * ��ȡ��һ������˱�ID
	 * @param teacherId ��ʦID
	 * @param formId ��ǰ��ID
	 * @return ��һ������˱�ID
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
	 * �Ƿ���Բ鿴�����֮ǰ��˹������������true
	 * @param teacherId ��ʦID
	 * @param formId ����ID
	 * @return �Ƿ���Բ鿴
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
	 * �޸�״̬
	 * @param userId �޸����û�ID
	 * @param userName �޸����û���
	 * @param formId ��ID
	 * @param approved �Ƿ�ͨ��
	 * @param comment ��ע
	 * @return �Ƿ�ɹ�
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
