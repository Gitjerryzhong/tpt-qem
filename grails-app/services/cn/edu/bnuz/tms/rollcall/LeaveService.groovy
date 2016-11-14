package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.rollcall.LeaveRequestCommand;
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.rollcall.LeaveItem;
import cn.edu.bnuz.tms.rollcall.LeaveRequest;

/**
 * 学生请假服务。
 * @author 杨林
 * @since 0.1
 */
class LeaveService {
	/**
	 * 创建请假条
	 * @param studentId 学生ID
	 * @param lrc 参数
	 * @return 已创建请假条对象
	 */
	def create(studentId, LeaveRequestCommand lrc) {
		LeaveRequest leaveRequest = new LeaveRequest(
			type: lrc.type,
			reason: lrc.reason,
			student: Student.load(studentId),
			status: LeaveRequest.STATUS_APPLYING,
			dateModified: new Date(),
			term: Term.load(lrc.term)
		)

		lrc.items.each {
			def leaveItem =  new LeaveItem()
			leaveItem.week = it.week
			if(it.day != null) {
				leaveItem.dayOfWeek = it.day
			} else if(it.arr != null) {
				leaveItem.arrangement = Arrangement.load(it.arr)
			}
			leaveRequest.addToItems(leaveItem)
		}
		
		leaveRequest.save(failOnError:true)
	}

	/**
	 * 更新请假条。
	 * @param studentId 学生ID
	 * @param lrc 参数
	 * @return 是否成功
	 */
	boolean update(String studentId, LeaveRequestCommand lrc) {
		LeaveRequest leaveRequest = LeaveRequest.get(lrc.requestId)

		// 只有当前用户可以更新
		if(leaveRequest.student.id != studentId) {
			return false
		}
		
		// 是否允许更新
		if(!leaveRequest.allowUpdate())
			return false

		leaveRequest.type = lrc.type
		leaveRequest.reason = lrc.reason
		leaveRequest.dateModified = new Date()

		lrc.items.each {
			def leaveItem = it.id ? LeaveItem.load(it.id) : new LeaveItem()
			leaveItem.week = it.week
			if(it.day != null) {
				leaveItem.dayOfWeek = it.day
				leaveItem.arrangement = null
			} else if(it.arr != null) {
				leaveItem.arrangement = Arrangement.load(it.arr)
				leaveItem.dayOfWeek = null
			} else {
				leaveItem.dayOfWeek = null
				leaveItem.arrangement = null
			}
			leaveRequest.addToItems(leaveItem)
		}

		lrc.removedItems.each {
			LeaveItem leaveItem = LeaveItem.load(it.id)
			leaveRequest.removeFromItems(leaveItem)
			leaveItem.delete()
		}
		
		leaveRequest.save(failOnError:true)

		return true
	}
	
	/**
	 * 删除请假条
	 * @param requestId 请假条ID
	 * @return 是否成功
	 */
	boolean delete(Long requestId) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId);
		if(leaveRequest.allowDelete()) {
			leaveRequest.delete(flush:true);
			return true
		} else {
			return false
		}
	}

	/**
	 * 获取指定ID的请假条。没有权限检查，用于显示。
	 * @param requestId 请假条ID
	 * @return 请假条
	 */
	LeaveRequest get(Long requestId) {
		return LeaveRequest.get(requestId);
	}
	
	/**
	 * 获取指定学生和ID的请假条，如果学生ID不匹配返回null。主要用于编辑请假条。
	 * @param studentId 学生ID
	 * @param requestId 请假条ID
	 * @return 请假条
	 */
	LeaveRequest get(String studentId, Long requestId) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId);
		if(leaveRequest.student.id == studentId) {
			return leaveRequest
		} else {
			return null;
		}
	}

	/**
	 * 获取指定学生和学期的所有请假条	
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return 请假条列表
	 */
	List<LeaveRequest> findAllLeaveRequests(String studentId, Term term) {
		return LeaveRequest.findAllByStudentAndTerm(Student.load(studentId), term);
	}
	
	/**
	 * 获取指定学生和学期的所有请假项，用于前台判断请假是否冲突。
	 * leaveRequestId指定不包含的请假条的ID，用于编辑表单；新建请假条时，可省略此参数。
	 * 审核不通过（status = 3）的请假条允许再次申请，不包含在返回值中。
	 * @param studentId 学生ID
	 * @param term 学期
	 * @param requestId 不包含的此请假条ID
	 * @return 请假项列表
	 */
	List<LeaveItem> findAllLeaveItems(String studentId, Term term, Long requestId = -1L) {
		Student student = Student.load(studentId)
		def results = LeaveItem.executeQuery '''
SELECT leaveItem
FROM LeaveItem leaveItem
JOIN leaveItem.leaveRequest leaveRequest
WHERE leaveRequest.student = :student
AND leaveRequest.term = :term
AND leaveRequest.status != 3
AND leaveRequest.id != :requestId
''', [student: student, term: term, requestId: requestId]
		return results;
	}

	/**
	 * 修改状态。
	 * @param userId 用户ID 
	 * @param requestId 请假条ID
	 * @param status 状态
	 * @return 是否成功
	 */
	boolean changeStatus(String userId, Long requestId, Integer status) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId)
		if(!leaveRequest.allowStatus(status)) {
			return false
		}
		
		// 是否批准执行其它Controller
		if(status == LeaveRequest.STATUS_APPROVED || status == LeaveRequest.STATUS_REJECTED) {
			return false 
		}
		
		if(leaveRequest?.student?.id == userId) {
			leaveRequest.status = status
			leaveRequest.save(failOnError:true)
			return true
		} else {
			return false
		}
	}
	
	/**
	 * 根据安排和周查找学生请假情况，用于点名。查询逻辑
	 * <pre>
	 * 1. 假条为已批准或销假状态（2，4）。
	 * 2. 安排学期与请假学期相同。
	 * 3. 请假项为指定周。
	 * 4. 请假项还满足以下条件之一：
	 *    4.1. 请假项为课次，则要求安排ID相同；
	 *    4.2. 或者，学生为此次安排上课的学生，且满足以下条件之一：
	 *         4.2.1 请假项为天，则安排的dayOfWeek与请假项相同；
	 *         4.2.2 请假项为周，则请假项的周次与参数week相同。
	 * </pre>
	 * @param arrangementId 安排ID
	 * @param week 周次
	 * @return list of map [id, studentId, type]
	 */
	def getLeaveRequests(String arrangementId, int week) {
		def result = LeaveRequest.executeQuery '''
SELECT distinct leaveRequest.id, leaveRequest.student.id as studentId, leaveRequest.type 
FROM LeaveRequest leaveRequest 
JOIN leaveRequest.items leaveItem 
WHERE leaveRequest.status in (2,4) 
  AND leaveRequest.term = (SELECT DISTINCT term 
    FROM Arrangement arrangement 
    JOIN arrangement.courseClasses courseClass 
    JOIN courseClass.term term
    WHERE arrangement.id = :arrangementId) 
  AND leaveItem.week = :week 
  AND (leaveItem.arrangement.id = :arrangementId OR ( 
    leaveRequest.student in (SELECT student 
	  FROM Arrangement arrangement 
	  JOIN arrangement.courseClasses courseClass 
	  JOIN courseClass.students courseClassStudent 
	  JOIN courseClassStudent.student student 
	  WHERE arrangement.id = :arrangementId ) AND ( 
	  ( 
        leaveItem.dayOfWeek is not null AND 
	    leaveItem.dayOfWeek = (SELECT arr.dayOfWeek FROM Arrangement arr WHERE arr.id=:arrangementId) 
      ) OR ( 
	    leaveItem.dayOfWeek is null AND 
		leaveItem.arrangement is null 
	  ) 
	) 
  ) 
)''', [arrangementId: arrangementId, week: week]
		
		def leaveRequests = []
		result.each {
			leaveRequests.add([
				id: it[0],
				studentId: it[1],
				type: it[2]
			])
		}
		
		return leaveRequests
	}
}
