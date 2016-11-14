package cn.edu.bnuz.tms.security

import org.hibernate.FetchMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

import cn.edu.bnuz.qem.ProjectAdminService
import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.BookingAdminService;
import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.system.TeacherRole
import cn.edu.bnuz.tms.teaching.CourseClassService
import cn.edu.bnuz.tpt.TptAdminService
import cn.edu.bnuz.tpt.TptStudent

class TmsUserDetailsService implements UserDetailsService {
	@Autowired // 必须嘀
	CourseClassService courseClassService
	
	@Autowired // 必须嘀
	BookingAdminService bookingAdminService
	
	@Autowired // 必须嘀
	ProjectAdminService projectAdminService
	
	@Autowired // 必须嘀
	TptAdminService tptAdminService

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException, DataAccessException {
		if(uid.length() == Student.ID_LEN) {
			def c = Student.createCriteria()
			Student student = c.get {
				eq 'id', uid
				fetchMode 'adminClass', FetchMode.JOIN
				fetchMode 'adminClass.department', FetchMode.JOIN
			}
			if(student) {
				return new StudentDetails(student, getAuthorities(student))
			}
		} else if(uid.length() == Teacher.ID_LEN) {
			def c = Teacher.createCriteria()
			Teacher teacher = c.get {
				eq 'id', uid
				fetchMode 'department', FetchMode.JOIN
			}
			if(teacher) {
				return new TeacherDetails(teacher, getAuthorities(teacher))
			}
		}
		throw new UsernameNotFoundException("未找到用户：" + uid);
	}


	List<GrantedAuthority> getAuthorities(Teacher teacher) {
		List<GrantedAuthority> gas = []

		gas << new SimpleGrantedAuthority(Role.TEACHER)
		
		try {
			if(teacher.isCollegeTeacher()) {
				gas << new SimpleGrantedAuthority(Role.COLLEGE_TEACHER)
			}
			
			if(courseClassService.isCourseTeacher(teacher)) {
				gas << new SimpleGrantedAuthority(Role.COURSE_TEACHER)
			}
			
			if(teacher.isAdminClassTeacher()) {
				gas << new SimpleGrantedAuthority(Role.ADMIN_CLASS_TEACHER)
			}
	
			if(teacher.isGradeTeacher()) {
				gas << new SimpleGrantedAuthority(Role.GRADE_TEACHER)
			}
			
			if(bookingAdminService.isChecker(teacher)) {
				gas << new SimpleGrantedAuthority(Role.BOOKING_CHECKER)
			}
			
			//如果是专家，则授予专家权限
			if(projectAdminService.isExpert(teacher)){
				gas << new SimpleGrantedAuthority(Role.QEM_EXPERT)
			}
			
			//如果是导师，则授权导师权限
			if(tptAdminService.isMentor(teacher)){
				gas << new SimpleGrantedAuthority(Role.TPT_MENTOR)
			}
			TeacherRole.getRoles(teacher.id).each {
				gas << new SimpleGrantedAuthority(it)
			}
		
		} catch (Exception ex) {
			log.debug(ex.message)
			ex.printStackTrace()
		}
		return gas
	}

	List<GrantedAuthority> getAuthorities(Student student) {
		List<GrantedAuthority> gas = []
		TptStudent tptStudent=  TptStudent.get(student.id)
		if( tptStudent!=null){
			gas.add(new SimpleGrantedAuthority(Role.TPT_STUDENT))
		}else{
			gas.add(new SimpleGrantedAuthority(Role.STUDENT))
		}
		
		return gas;
	}
}
