package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.rollcall.LeaveRequestCommand;
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.rollcall.LeaveItem;
import cn.edu.bnuz.tms.rollcall.LeaveRequest;

/**
 * ѧ����ٷ���
 * @author ����
 * @since 0.1
 */
class LeaveService {
	/**
	 * ���������
	 * @param studentId ѧ��ID
	 * @param lrc ����
	 * @return �Ѵ������������
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
	 * �����������
	 * @param studentId ѧ��ID
	 * @param lrc ����
	 * @return �Ƿ�ɹ�
	 */
	boolean update(String studentId, LeaveRequestCommand lrc) {
		LeaveRequest leaveRequest = LeaveRequest.get(lrc.requestId)

		// ֻ�е�ǰ�û����Ը���
		if(leaveRequest.student.id != studentId) {
			return false
		}
		
		// �Ƿ��������
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
	 * ɾ�������
	 * @param requestId �����ID
	 * @return �Ƿ�ɹ�
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
	 * ��ȡָ��ID���������û��Ȩ�޼�飬������ʾ��
	 * @param requestId �����ID
	 * @return �����
	 */
	LeaveRequest get(Long requestId) {
		return LeaveRequest.get(requestId);
	}
	
	/**
	 * ��ȡָ��ѧ����ID������������ѧ��ID��ƥ�䷵��null����Ҫ���ڱ༭�������
	 * @param studentId ѧ��ID
	 * @param requestId �����ID
	 * @return �����
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
	 * ��ȡָ��ѧ����ѧ�ڵ����������	
	 * @param studentId ѧ��ID
	 * @param term ѧ��
	 * @return ������б�
	 */
	List<LeaveRequest> findAllLeaveRequests(String studentId, Term term) {
		return LeaveRequest.findAllByStudentAndTerm(Student.load(studentId), term);
	}
	
	/**
	 * ��ȡָ��ѧ����ѧ�ڵ�������������ǰ̨�ж�����Ƿ��ͻ��
	 * leaveRequestIdָ�����������������ID�����ڱ༭�����½������ʱ����ʡ�Դ˲�����
	 * ��˲�ͨ����status = 3��������������ٴ����룬�������ڷ���ֵ�С�
	 * @param studentId ѧ��ID
	 * @param term ѧ��
	 * @param requestId �������Ĵ������ID
	 * @return ������б�
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
	 * �޸�״̬��
	 * @param userId �û�ID 
	 * @param requestId �����ID
	 * @param status ״̬
	 * @return �Ƿ�ɹ�
	 */
	boolean changeStatus(String userId, Long requestId, Integer status) {
		LeaveRequest leaveRequest = LeaveRequest.get(requestId)
		if(!leaveRequest.allowStatus(status)) {
			return false
		}
		
		// �Ƿ���׼ִ������Controller
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
	 * ���ݰ��ź��ܲ���ѧ�������������ڵ�������ѯ�߼�
	 * <pre>
	 * 1. ����Ϊ����׼������״̬��2��4����
	 * 2. ����ѧ�������ѧ����ͬ��
	 * 3. �����Ϊָ���ܡ�
	 * 4. ����������������֮һ��
	 *    4.1. �����Ϊ�δΣ���Ҫ����ID��ͬ��
	 *    4.2. ���ߣ�ѧ��Ϊ�˴ΰ����Ͽε�ѧ������������������֮һ��
	 *         4.2.1 �����Ϊ�죬���ŵ�dayOfWeek���������ͬ��
	 *         4.2.2 �����Ϊ�ܣ����������ܴ������week��ͬ��
	 * </pre>
	 * @param arrangementId ����ID
	 * @param week �ܴ�
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
