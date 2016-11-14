package cn.edu.bnuz.tms.system

/**
 * 角色常量
 * @author 杨林
 * @version 0.1
 * @since 0.1
 */
final class Role {
	private Role() {}
	/**
	 * 系统管理员
	 */
	public static final String SYSTEM_ADMIN = 'ROLE_SYSTEM_ADMIN'
	
	/**
	 * 部门管理员
	 */
	public static final String DEPARTMENT_ADMIN = 'ROLE_DEPARTMENT_ADMIN'
	
	/**
	 * 学生
	 */
	public static final String STUDENT = 'ROLE_STUDENT'
	
	/**
	 * 教师
	 */
	public static final String TEACHER = 'ROLE_TEACHER'
	
	/**
	 * 学院教师（有学生的部门）
	 */
	public static final String COLLEGE_TEACHER = 'ROLE_COLLEGE_TEACHER'
	
	/**
	 * 任课教师
	 */
	public static final String COURSE_TEACHER = 'ROLE_COURSE_TEACHER'
	
	/**
	 * 班主任
	 */
	public static final String ADMIN_CLASS_TEACHER = 'ROLE_ADMIN_CLASS_TEACHER'
	
	/**
	 * 年级辅导员
	 */
	public static final String GRADE_TEACHER = 'ROLE_GRADE_TEACHER'
	
	/**
	 * 学务管理人员
	 */
	public static final String STUDENT_AFFAIRS = 'ROLE_STUDENT_AFFAIRS'
	
	/**
	 * 考勤管理员
	 */
	public static final String ROLLCALL_ADMIN = 'ROLE_ROLLCALL_ADMIN'
	
	/**
	 * 教学院长
	 */
	public static final String DEAN_OF_TEACHING = 'ROLE_DEAN_OF_TEACHING'
	
	/**
	 * 院长
	 */
	public static final String DEAN_OF_COLLEGE = 'ROLE_DEAN_OF_COLLEGE'

	/**
	 * 教务秘书
	 */
	public static final String ACADEMIC_SECRETARY = 'ROLE_ACADEMIC_SECRETARY'
	
	/**
	 * 学籍管理
	 */
	public static final String ENROLL_ADMIN = 'ROLE_ENROLL_ADMIN'
	
	/**
	 * 问题管理
	 */
	public static final String ISSUE_ADMIN = 'ROLE_ISSUE_ADMIN'
	
	/**
	 * 借用教室审核
	 */
	public static final String BOOKING_CHECKER = 'ROLE_BOOKING_CHECKER'
	
	/**
	 * 借用教室审批
	 */
	public static final String BOOKING_APPROVER = 'ROLE_BOOKING_APPROVER'
	
	/**
	 * 借用教室管理
	 */
	public static final String BOOKING_ADMIN = 'ROLE_BOOKING_ADMIN'
	
	/**
	 * 借用教室-高级用户（不限周次）
	 */
	public static final String BOOKING_ADV_USER = 'ROLE_BOOKING_ADV_USER'
	/**
	 * 2+2用户管理
	 */
	public static final String TPT_ADMIN = 'ROLE_TPT_ADMIN'
	
	/**
	 * 2+2用户
	 */
	public static final String TPT_STUDENT = 'ROLE_TPT_STUDENT'
	/**
	 * 质量工程系统管理员
	 */
	public static final String QEM_ADMIN = 'ROLE_QEM_ADMIN'
	/**
	 * 质量工程项目学院审核员
	 */
	public static final String QEM_CHECKER = 'ROLE_QEM_CHECKER'
	/**
	 * 质量工程项目专家组成员
	 */
	public static final String QEM_EXPERT = 'ROLE_QEM_EXPERT'
	/**
	 * 合作项目管理员
	 */
	public static final String TPT_COPROJECT = 'ROLE_TPT_COPROJECT'
	/**
	 * 2+2论文导师
	 */
	public static final String TPT_MENTOR = 'ROLE_TPT_MENTOR'
	/**
	 * 合作项目浏览者
	 */
	public static final String TPT_COPROJECTVIEW = 'ROLE_TPT_COPROJECTVIEW'
	
}
