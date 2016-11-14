package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.rollcall.LeaveRequest;

class LeaveApprocalService {
	/**
	 * ����״̬��ѯ����б�
	 * @param teacherId �꼶��ʦID
	 * @param term ѧ��
	 * @param status ���1�����ش���ˣ����2�����������
	 * @return ����б�
	 */
	def getRequestsByStatus(String teacherId, Term term, int status) {
		if(status == 1) {
			def requests = LeaveRequest.executeQuery """
FROM LeaveRequest leaveRequest
WHERE leaveRequest.status = 1
AND leaveRequest.term = :term
AND leaveRequest.student.adminClass.gradeTeacher.id = :teacherId
ORDER by dateModified""", [teacherId: teacherId, term: term]
			return requests
		} else {
			def requests = LeaveRequest.executeQuery """
FROM LeaveRequest leaveRequest
WHERE leaveRequest.status in (2,3,4)
AND leaveRequest.term = :term
AND leaveRequest.approver.id = :teacherId
ORDER by dateApproved desc""", [teacherId: teacherId, term: term]
			return requests
		}
		
	}

	LeaveRequest get(String userId, Long requestId) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId);
		if(leaveRequest == null) {
			return null
		} else if(leaveRequest.student.adminClass.gradeTeacher.id == userId) {
			return leaveRequest
		} else {
			return null
		}
	}

	boolean changeStatus(String userId, Long requestId, Integer status) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId)

		// ������
		if(leaveRequest == null) {
			return false
		}

		// ��׼/����׼
		if(status != LeaveRequest.STATUS_APPROVED && status != LeaveRequest.STATUS_REJECTED) {
			return false
		}

		// ״̬��
		if(!leaveRequest.allowStatus(status)) {
			return false
		}

		// Ȩ��
		if(leaveRequest.student.adminClass.gradeTeacher.id == userId) {
			leaveRequest.approver = Teacher.load(userId)
			leaveRequest.dateApproved = new Date()
			leaveRequest.status = status
			leaveRequest.save(failOnError:true)
			return true
		} else {
			return false
		}
	}
}
