package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingAuth;
import cn.edu.bnuz.tms.place.BookingType;

class BookingAdminService {
	/**
	 * 获取借用类型及审核人
	 * @param departmentId 部门ID
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
	 * 获取教学单位借用类型
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
	 * 获取教学单位审核人
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
	 * 获取行政部门借用类型
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
	 * 获取行政部门审核人
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
	 * 设置审核人
	 * @param departmentId 部门ID
	 * @param typeId 类型ID
	 * @param checkerId 审核人ID
	 */
	def setChecker(String departmentId, Long typeId, String checkerId) {
		// 删除已有
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
	 * 是否判断教师是否为审核人
	 * @param teacher 教师
	 * @return 是否为审核人
	 */
	boolean isChecker(Teacher teacher) {
		BookingAuth.countByCheckerAndDepartment(teacher, teacher.department) > 0
	}
}
