package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.card.ReissueRequest;
import cn.edu.bnuz.tms.organization.Student;
import cn.edu.bnuz.tms.rollcall.LeaveRequestCommand;

class CardReissueRequestsService {
	/**
	 * 根据申请ID获取ReissueCard对象
	 * @param id 申请ID
	 * @return ReissueCard对象
	 */
	ReissueRequest get(Long id) {
		ReissueRequest.get(id)
	}
	
	/**
	 * 获取学生已完成的申请次数
	 * @param studentId 学生ID
	 * @return 申请次数
	 */
	int countByStudent(String studentId) {
		ReissueRequest.countByStudentAndStatus(Student.load(studentId), ReissueRequest.STATUS_FINISHED)
	}
		
	/**
	 * 查找未办结的申请
	 * @param studentId 学生ID
	 * @return 申请
	 */
	ReissueRequest findUnfinished(String studentId) {
		ReissueRequest.findByStudentAndStatusNotEqual(Student.load(studentId), ReissueRequest.STATUS_FINISHED);
	}
	
	/**
	 * 查找学生所有申请
	 * @param studentId
	 * @return 申请列表
	 */
	def getAll(String studentId) {
		ReissueRequest.findAllByStudent(Student.load(studentId));
	}
	
	/**
	 * 各状态申请数量
	 * @return 各状态申请数量
	 */
	def getCountsByStatus() {
		ReissueRequest.executeQuery("""
select status, count(*)
from ReissueRequest
group by status
""").collectEntries {[it[0], it[1]]}
	}
	
	/**
	 * 查找所有指定状态的申请（DTO）
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
	 * 新建申请。
	 * @param studentId 学生ID
	 * @param reason 事由
	 * @return ReissueCard对象
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
	 * 更新申请。
	 * @param userId 用户ID
	 * @param reason 事由
	 * @return 是否成功
	 */
	boolean update(String userId, Long id, String reason) {
		ReissueRequest reissueRequest = ReissueRequest.get(id)

		// 只有当前用户可以更新
		if(reissueRequest.student.id != userId) {
			return false
		}
		
		// 是否允许更新
		if(!reissueRequest.allowUpdate())
			return false

		reissueRequest.reason = reason
		reissueRequest.dateModified = new Date()
		reissueRequest.save(failOnError:true)

		return true
	}
	
	/**
	 * 修改状态。
	 * @param userId 用户ID
	 * @param id 申请ID
	 * @param status 状态
	 * @return 是否成功
	 */
	boolean changeStatus(String userId, Long id, Integer status) {
		ReissueRequest reissueRequest = ReissueRequest.get(id)
		if(!reissueRequest.allowStatus(status)) {
			return false
		}
		
		// 制作、不批准和完成执行其它Controller
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
	 * 删除请假条。
	 * @param id 申请ID
	 * @return 是否成功
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
