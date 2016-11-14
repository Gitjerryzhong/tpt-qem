package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.card.ReissueRequest;
import cn.edu.bnuz.tms.organization.Student;
import cn.edu.bnuz.tms.rollcall.LeaveRequestCommand;

class CardReissueRequestsService {
	/**
	 * ��������ID��ȡReissueCard����
	 * @param id ����ID
	 * @return ReissueCard����
	 */
	ReissueRequest get(Long id) {
		ReissueRequest.get(id)
	}
	
	/**
	 * ��ȡѧ������ɵ��������
	 * @param studentId ѧ��ID
	 * @return �������
	 */
	int countByStudent(String studentId) {
		ReissueRequest.countByStudentAndStatus(Student.load(studentId), ReissueRequest.STATUS_FINISHED)
	}
		
	/**
	 * ����δ��������
	 * @param studentId ѧ��ID
	 * @return ����
	 */
	ReissueRequest findUnfinished(String studentId) {
		ReissueRequest.findByStudentAndStatusNotEqual(Student.load(studentId), ReissueRequest.STATUS_FINISHED);
	}
	
	/**
	 * ����ѧ����������
	 * @param studentId
	 * @return �����б�
	 */
	def getAll(String studentId) {
		ReissueRequest.findAllByStudent(Student.load(studentId));
	}
	
	/**
	 * ��״̬��������
	 * @return ��״̬��������
	 */
	def getCountsByStatus() {
		ReissueRequest.executeQuery("""
select status, count(*)
from ReissueRequest
group by status
""").collectEntries {[it[0], it[1]]}
	}
	
	/**
	 * ��������ָ��״̬�����루DTO��
	 * @param status
	 * @param offset
	 * @param max
	 * @return
	 */
	def findAllByStatus(int status, int offset, int max) {
		ReissueRequest.executeQuery """
select new map(
  id as id,
  student.id as studentId,
  student.name as name,
  student.sex as sex,
  student.adminClass.department.name as department,
  student.major.name as major,
  dateModified as applyDate,
  status as status,
  (select count(*) from ReissueRequest where student.id = rr.student.id) as count
)
from ReissueRequest rr
where status = :status
order by dateModified desc
""", [status: status], [offset: offset, max: max]
	}
	
	/**
	 * �½����롣
	 * @param studentId ѧ��ID
	 * @param reason ����
	 * @return ReissueCard����
	 */
	def create(String studentId, String reason) {
		ReissueRequest reissueRequest = new ReissueRequest(
			student: Student.load(studentId),
			reason: reason,
			status: ReissueRequest.STATUS_APPLYING,
			dateCreated: new Date(),
			dateModified: new Date()
		)
		reissueRequest.save(failOnError:true)
	}
	
	/**
	 * �������롣
	 * @param userId �û�ID
	 * @param reason ����
	 * @return �Ƿ�ɹ�
	 */
	boolean update(String userId, Long id, String reason) {
		ReissueRequest reissueRequest = ReissueRequest.get(id)

		// ֻ�е�ǰ�û����Ը���
		if(reissueRequest.student.id != userId) {
			return false
		}
		
		// �Ƿ��������
		if(!reissueRequest.allowUpdate())
			return false

		reissueRequest.reason = reason
		reissueRequest.dateModified = new Date()
		reissueRequest.save(failOnError:true)

		return true
	}
	
	/**
	 * �޸�״̬��
	 * @param userId �û�ID
	 * @param id ����ID
	 * @param status ״̬
	 * @return �Ƿ�ɹ�
	 */
	boolean changeStatus(String userId, Long id, Integer status) {
		ReissueRequest reissueRequest = ReissueRequest.get(id)
		if(!reissueRequest.allowStatus(status)) {
			return false
		}
		
		// ����������׼�����ִ������Controller
		if(status == ReissueRequest.STATUS_MAKING || 
		   status == ReissueRequest.STATUS_REJECTED || 
		   status == ReissueRequest.STATUS_FINISHED) {
			return false
		}
		
		if(reissueRequest?.student?.id == userId) {
			reissueRequest.status = status
			reissueRequest.save(failOnError:true)
			return true
		} else {
			return false
		}
	}
	
	/**
	 * ɾ���������
	 * @param id ����ID
	 * @return �Ƿ�ɹ�
	 */
	boolean delete(Long id) {
		ReissueRequest reissueRequest = ReissueRequest.get(id);
		if(reissueRequest.allowDelete()) {
			reissueRequest.delete(flush:true);
			return true
		} else {
			return false
		}
	}
}
