package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.rollcall.LeaveRequest;

class LeaveApprocalService {
	/**
	 * 根据状态查询请假列表。
	 * @param teacherId 年级教师ID
	 * @param term 学期
	 * @param status 如果1，返回待审核；如果2，返回已审核
	 * @return 请假列表
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

		// 不存在
		if(leaveRequest == null) {
			return false
		}

		// 批准/不批准
		if(status != LeaveRequest.STATUS_APPROVED && status != LeaveRequest.STATUS_REJECTED) {
			return false
		}

		// 状态机
		if(!leaveRequest.allowStatus(status)) {
			return false
		}

		// 权限
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
