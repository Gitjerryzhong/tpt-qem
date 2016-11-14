package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingAuth;
import cn.edu.bnuz.tms.place.BookingType;

class BookingAdminService {
	/**
	 * ��ȡ�������ͼ������
	 * @param departmentId ����ID
	 * @return List of [typeId, typeName, checkerId, checkerName]
	 */
	def getBookingTypes(String departmentId) {
		BookingType.executeQuery '''
select new map (
  bt.id as id,
  bt.name as name,
  au.checker.id as checkerId,
  te.name as checkerName
) from BookingType bt
left join bt.auths au with au.department.id = :departmentId
left join au.checker te
where bt.isAdminDept = false
''', [departmentId: departmentId]
	}
	

	
	/**
	 * ��ȡ��ѧ��λ��������
	 * @return list of [id, name]
	 */
	def getTdBookingTypes() {
		BookingType.executeQuery '''
select new map (
  id as id,
  name as name
) from BookingType 
where isAdminDept = false
'''
	}
	
	/**
	 * ��ȡ��ѧ��λ�����
	 */
	def getTdBookingCheckers() {
		BookingAuth.executeQuery '''
select new Map(
  dp.id as departmentId,
  tp.id as typeId,
  ck.id as checkerId,
  ck.name as checkerName
)
from BookingAuth ba
join ba.department dp
join ba.type tp
join ba.checker ck
where dp.isAdminDept = false
'''
	}
	
	/**
	 * ��ȡ�������Ž�������
	 * @return list of [id, name]
	 */
	def getAdBookingTypes() {
		BookingType.executeQuery '''
select new map(
  id as id,
  name as name
)
from BookingType bt
where isAdminDept = true
'''
	}
	
	/**
	 * ��ȡ�������������
	 */
	def getAdBookingCheckers() {
		BookingAuth.executeQuery '''
select new Map(
  bd.id as departmentId,
  bd.name as departmentName,
  bt.id as typeId,
  bt.name as typeName,
  bc.id as checkerId,
  bc.name as checkerName
)
from BookingAuth ba
join ba.type bt
join ba.department bd
join ba.checker bc
where bt.isAdminDept = true
'''
	}
	
	/**
	 * ���������
	 * @param departmentId ����ID
	 * @param typeId ����ID
	 * @param checkerId �����ID
	 */
	def setChecker(String departmentId, Long typeId, String checkerId) {
		// ɾ������
		BookingAuth.executeUpdate '''
DELETE BookingAuth bookingAuth
WHERE bookingAuth.department.id = :departmentId
AND bookingAuth.type.id = :typeId
''', [departmentId:departmentId, typeId: typeId]
		if(checkerId) {
			def auth = new BookingAuth(
				department: Department.load(departmentId),
				type: BookingType.load(typeId),
				checker: Teacher.load(checkerId)
			)
			
			auth.save(failOnError: true)
		}
	}
	
	/**
	 * �Ƿ��жϽ�ʦ�Ƿ�Ϊ�����
	 * @param teacher ��ʦ
	 * @return �Ƿ�Ϊ�����
	 */
	boolean isChecker(Teacher teacher) {
		BookingAuth.countByCheckerAndDepartment(teacher, teacher.department) > 0
	}
}
