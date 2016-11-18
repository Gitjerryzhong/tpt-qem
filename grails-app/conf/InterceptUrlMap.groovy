import static cn.edu.bnuz.tms.system.Role.*

class InterceptUrlMap {
	static mappings = [
		'/qem/**': [
			 TEACHER
		 ],
	 '/qemTask/**': [
		  TEACHER
	  ],
	  '/qemTaskUpdate/**': [
		   TEACHER
	   ],
	 '/qemadmin/**': [
		  QEM_ADMIN
	  ],
	  '/qemUpdateAdmin/**': [
		   QEM_ADMIN
	   ],
	  '/qemCollegeCheck/**': [
		   QEM_CHECKER
	   ],
	   '/qemProjectAdmin/**': [
			QEM_ADMIN
		],
		'/qemTaskAdmin/**': [
		 QEM_ADMIN
		],
	  '/tpt/**': [
		   TPT_STUDENT
	   ],
	   '/tptAdmin/**': [
		   TPT_ADMIN
	   ],
	   '/tptCoPrjAdmin/**': [
			TPT_COPROJECT
		],
		'/tptCoPrjView/**': [
			 TPT_COPROJECTVIEW
		 ],
   		'/tptExport/**': [
		TPT_ADMIN
	   ],
		'/static/**': [
			'IS_AUTHENTICATED_ANONYMOUSLY'
		],
	
		'/assets/**': [
			 'IS_AUTHENTICATED_ANONYMOUSLY'
		],
	 
		'/fonts/**': [
			'IS_AUTHENTICATED_ANONYMOUSLY'
		],

		'/login/*': [
			'IS_AUTHENTICATED_ANONYMOUSLY'
		],
	
		'/schedule/teacher': [
			COURSE_TEACHER
		],
	
		'/schedule/student': [
			STUDENT
		],
	
		'/rollcall/adminClass': [
			ADMIN_CLASS_TEACHER
		],
	
		'/rollcall/grade': [
			GRADE_TEACHER
		],
	
		'/rollcall/department': [
			ROLLCALL_ADMIN,
			STUDENT_AFFAIRS,
			DEAN_OF_TEACHING,
			DEAN_OF_COLLEGE
		],
	
		'/rollcall/gmac' : [
			TEACHER
		],
	
		'/rollcall/student/**': [
			ADMIN_CLASS_TEACHER,
			GRADE_TEACHER,
			ROLLCALL_ADMIN,
			STUDENT_AFFAIRS,
			DEAN_OF_TEACHING,
			DEAN_OF_COLLEGE
		],
	
		'/rollcall/teachers/**': [
			ROLLCALL_ADMIN,
			STUDENT_AFFAIRS,
			DEAN_OF_TEACHING,
			DEAN_OF_COLLEGE
		],
	
		'/rollcall/lock': [
			ROLLCALL_ADMIN
		],
	
		'/rollcall/personal': [
			STUDENT,
			COURSE_TEACHER
		],
	
		'/rollcall/mycourse': [
			COURSE_TEACHER
		],
	
		'/rollcall/**': [
			COURSE_TEACHER
		],
	
		'/rollcallAdmin/**': [
			ROLLCALL_ADMIN
		],
	
		'/cancelExam/**': [
			ROLLCALL_ADMIN
		],
	
		'/leaves/new': [
			STUDENT
		],
	
		'/leaveApprovals/**': [
			GRADE_TEACHER
		],
	
		'/freeArrangements/**': [
			COURSE_TEACHER
		],
	
		'/addresses': [
			TEACHER,
			STUDENT
		],
	
		'/addresses/adminClasses/**': [
			STUDENT_AFFAIRS,
			ROLLCALL_ADMIN,
			ADMIN_CLASS_TEACHER,
			GRADE_TEACHER
		],
	
		'/admin/departments': [
			SYSTEM_ADMIN
		],
	
		'/admin/departmentAdmin': [
			SYSTEM_ADMIN
		],
	
		'/admin/teacherRoles': [
			DEPARTMENT_ADMIN
		],
	
		'/admin/teacherRole': [
			DEPARTMENT_ADMIN
		],
	
		'/admin/adminClasses': [
			DEPARTMENT_ADMIN
		],
	
		'/admin/adminClass': [
			DEPARTMENT_ADMIN
		],
	
		'/admin/departmentTeachers': [
			SYSTEM_ADMIN,
			DEPARTMENT_ADMIN,
			BOOKING_ADMIN
		],
	
		'/materials': [
			SYSTEM_ADMIN,
			ACADEMIC_SECRETARY
		],
	
		'/termMaterials': [
			ACADEMIC_SECRETARY
		],
	
		'/teachingMaterials': [
			ACADEMIC_SECRETARY
		],
	
		'/personalMaterials': [
			COURSE_TEACHER
		],
	
		'/pictures/*': [
			STUDENT,
			TEACHER
		],
	
		'/cardReissueOrders/**': [
			ENROLL_ADMIN
		],
		
		'/cardReissueRequests/**': [
			 ENROLL_ADMIN,
			 STUDENT
		],
	
		'/bookings/**': [
			STUDENT, 
			TEACHER
		],
	
		'/bookingCheck/**': [
			BOOKING_CHECKER
		],
	
		'/bookingApprove/**': [
			BOOKING_APPROVER
		],
	
		'/bookingReport/**': [
			BOOKING_APPROVER
		],
	  
		'/bookingAdmin/**': [
			BOOKING_ADMIN
		],
	
		'/**': [
			'IS_AUTHENTICATED_FULLY'
		],

	]
}
