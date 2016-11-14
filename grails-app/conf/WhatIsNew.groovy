//import static cn.edu.bnuz.tms.system.Role.*
//
//import grails.plugin.springsecurity.SpringSecurityUtils;
//
//class WhatIsNew {
//	static news = [
//		[
//			date:  '2013-5-4',
//			label: 'menu.adminThesisTeachers',
//			roles: [ACADEMIC_SECRETARY],
//			uri:   '/thesisTeachers',
//		],
//		[
//			date:  '2013-5-4',
//			label: 'menu.adminInternshipTeachers',
//			roles: [ACADEMIC_SECRETARY],
//			uri:   '/internshipTeachers',
//		],
//		[
//			date:  '2013-4-25',
//			label: 'menu.queryMyMaterials',
//			roles: [COURSE_TEACHER],
//			uri:   '/personalMaterials',
//		],
//		[
//			date:  '2013-4-22',
//			label: 'menu.teachingMaterials',
//			roles: [ACADEMIC_SECRETARY],
//			uri:   '/teachingMaterials',
//		],
//		[
//			date:  '2013-4-20',
//			label: 'menu.adminTermMaterials',
//			roles: [ACADEMIC_SECRETARY],
//			uri:   '/termMaterials',
//		],
//		[
//			date:  '2013-4-18',
//			label: 'menu.adminMaterials',
//			roles: [ACADEMIC_SECRETARY],
//			uri:   '/materials',
//		],
//		[
//			date:  '2013-4-6',
//			label: 'menu.queryRollcallByGmac',
//			roles: [TEACHER],
//			uri:   '/rollcall/gmac',
//		],
//		[
//			date:  '2013-4-2',
//			label: 'news.exportRollcallStatisToExcel',
//		],
//		[
//			date:  '2013-4-1',
//			label: 'news.exportTeacherRollcallToExcel',
//			roles: [COURSE_TEACHER],
//			uri:   '/rollcall/personal',
//		],
//		[
//			date:  '2013-3-30',
//			label: 'menu.addressListOfAdminClasses',
//			roles: [
//				ADMIN_CLASS_TEACHER,
//				GRADE_TEACHER,
//				ROLLCALL_ADMIN,
//				STUDENT_AFFAIRS
//			],
//			uri:   '/addresses/adminClasses',
//		],
//		[
//			date:  '2013-3-26',
//			label: 'menu.cancelExam',
//			roles: [ROLLCALL_ADMIN],
//			uri:   '/cancelExam',
//		],
//		[
//			date:  '2013-3-18',
//			label: 'menu.adminAdminClasses',
//			roles: [DEPARTMENT_ADMIN],
//			uri:   '/admin/adminClasses',
//		],
//		[
//			date:  '2013-3-17',
//			label: 'menu.adminTeacherRoles',
//			roles: [DEPARTMENT_ADMIN],
//			uri:   '/admin/teacherRoles',
//		],
//		[
//			date:  '2013-3-14',
//			label: 'menu.adminDepartments',
//			roles: [SYSTEM_ADMIN],
//			uri:   '/admin/departments',
//		],
//		[
//			date:  '2013-3-13',
//			label: 'menu.repairReport',
//			roles: [TEACHER],
//			uri:   'http://itc.bnuep.com/syzxnew/syzx_baoxiu.asp',
//		],
//		[
//			date:  '2013-3-10',
//			label: 'menu.queryRollcallPersonal',
//			roles: [TEACHER],
//			uri:   '/rollcall/personal',
//		],
//		[
//			date:  '2013-3-9',
//			label: 'news.studentRollcallPersonal'
//		],
//		[
//			date:  '2013-3-8',
//			label: 'menu.lockRollcall',
//			roles: [ROLLCALL_ADMIN],
//			uri:   '/rollcall/lock',
//		],
//		[
//			date:  '2013-3-6',
//			label: 'news.randomRollcall'
//		],
//		[
//			date:  '2013-3-3',
//			label: 'menu.addressListOfDepartment',
//			roles: [TEACHER],
//			uri:   '/addresses',
//		],
//		[
//			date:  '2013-3-3',
//			label: 'menu.queryFreeArrangements',
//			roles: [COURSE_TEACHER],
//			uri:   '/freeArrangements',
//		],
//		[
//			date:  '2013-3-1',
//			label: 'menu.queryRollcallByTeacher',
//			roles: [
//				STUDENT_AFFAIRS,
//				DEAN_OF_TEACHING,
//				DEAN_OF_COLLEGE
//			],
//			uri:   '/rollcall/teachers',
//		],
//		[
//			date:  '2013-2-28',
//			label: 'menu.queryRollcallByDepartment',
//			roles: [
//				STUDENT_AFFAIRS,
//				DEAN_OF_TEACHING,
//				DEAN_OF_COLLEGE
//			],
//			uri:   '/rollcall/department',
//		],
//		[
//			date:  '2013-2-25',
//			label: 'menu.queryRollcallByGrade',
//			roles: [GRADE_TEACHER],
//			uri:   '/rollcall/grade',
//		],
//		[
//			date:  '2013-2-25',
//			label: 'menu.queryRollcallByAdminClass',
//			roles: [ADMIN_CLASS_TEACHER],
//			uri:   '/rollcall/adminclass',
//		],
//		[
//			date:  '2013-2-25',
//			label: 'menu.checkLeaves',
//			roles: [GRADE_TEACHER],
//			uri:   '/leaveApprovals',
//		],
//		[
//			date:  '2013-3-25',
//			label: 'news.studentLeaveRequest'
//		],
//		[
//			date:  '2013-2-25',
//			label: 'menu.startRollcall',
//			roles: [COURSE_TEACHER],
//			uri:   '/schedule/teacher',
//		],
//		[
//			date:  '2013-3-25',
//			label: 'news.userLogin'
//		],
//		[
//			date:  '2013-3-25',
//			label: 'news.dataImport'
//		]
//	]
//	
//	static boolean hasRoles(item) {
//		item.roles && SpringSecurityUtils.ifAllGranted(item.roles.join())
//	}
//}
