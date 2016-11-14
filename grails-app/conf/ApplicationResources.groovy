modules = {
	json3 {
		resource url:'js/lib/json3.min.js'
	}
		
	dot {
		resource url:'js/lib/doT.min.js'
	}
	
	underscore {
		resource url:'js/lib/underscore-min.js'
	}
	
	moment {
		resource url:'js/lib/moment.min.js'
	}
	
	URI {
		resource url:'js/lib/URI-1.13.1.min.js'
	}
	
	'moment-with-lang' {
		resource url:'js/lib/moment-with-locales.min.js'
	}

	'jquery-ui' {
		dependsOn 'jquery'
		resource url:'js/lib/jquery-ui-1.10.4.custom.js', disposition: 'head' // only core and Widget
	}
	
	pager {
		dependsOn 'URI'
		resource url:'js/ui/pager.js', attrs:[charset:'GBK']
	}
	
	'main-css' {
		dependsOn 'bootstrap'
		resource url:'css/main.css'
	}
	
	base {
		dependsOn 'main-css', 'jquery', 'jquery-ui'
		resource url:'js/logo.js', attrs:[charset:'GBK']
	}

	tms {
		resource url:'js/core/tms.js', attrs:[charset:'GBK']
	}
	
	'tms-format' {
		dependsOn 'tms'
		resource url:'js/core/format.js', attrs:[charset:'GBK']
	}
	
	'tms-validate' {
		dependsOn 'tms'
		resource url:'js/core/validate.js', attrs:[charset:'GBK']
	}
	
	arrangement {
		dependsOn 'tms-format'
		resource url:'js/teach/model/arrangement.js', attrs:[charset:'GBK']
	}
	
	login {
		dependsOn 'bootstrap-css', 'jquery'
		resource url:'css/login/auth.css'
	}
	
	profile {
		dependsOn 'base'
		resource url:'js/profile/profile.js', attrs:[charset:'GBK']
	}
	
	departmentsAdmin {
		dependsOn 'base'
		resource url:'js/admin/departments.js', attrs:[charset:'GBK']
		resource url:'css/admin/departments.css'
	}
	
	teacherRole {
		dependsOn 'base'
		resource url:'js/admin/teacher-role.js', attrs:[charset:'GBK']
		resource url:'css/admin/teacher-role.css'
	}
	
	adminClasses {
		dependsOn 'base'
		resource url:'js/admin/admin-classes.js', attrs:[charset:'GBK']
		resource url:'css/admin/admin-classes.css'
	}
	
	index {
		dependsOn 'base'
		resource url:'css/index.css'
	}
	
	studentSchedule {
		dependsOn 'base', 'arrangement'
		resource url:'js/schedule/schedule.js', attrs:[charset:'GBK']
		resource url:'js/schedule/student.js', attrs:[charset:'GBK']
		resource url:'css/schedule/student.css'
	}
	
	teacherSchedule {
		dependsOn 'base', 'arrangement'
		resource url:'js/schedule/schedule.js', attrs:[charset:'GBK']
		resource url:'js/schedule/teacher.js', attrs:[charset:'GBK']
		resource url:'css/schedule/teacher.css'
	}
	
	'rollcall-format' {
		dependsOn 'tms-format'
		resource url:'js/rollcall/format.js', attrs:[charset:'GBK']
	}
	
	rollcallForm {
		dependsOn 'base', 'arrangement', 'rollcall-format', 'dot', 'json3'
		resource url:'js/ui/week-tabs.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/model.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/summary.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/toolbar.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/detail-view.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/list-view.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/tile-view.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/lock-view.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/form.js', attrs:[charset:'GBK']
		resource url:'css/rollcall/detail-view.css'
		resource url:'css/rollcall/list-view.css'
		resource url:'css/rollcall/tile-view.css'
		resource url:'css/rollcall/lock-view.css'
		resource url:'css/rollcall/form.css'
	}
	
	rollcallStudentsStatis {
		dependsOn 'base', 'arrangement', 'rollcall-format', 'dot'
		resource url:'js/rollcall-statis/model/rollcall-item.js', attrs:[charset:'GBK']
		resource url:'js/rollcall-statis/model/leave-item.js', attrs:[charset:'GBK']
		resource url:'js/rollcall-statis/students.js', attrs:[charset:'GBK']
		resource url:'css/rollcall-statis/students.css'
	}
	
	rollcallStudentPersonal {
		dependsOn 'base', 'arrangement', 'rollcall-format', 'dot'
		resource url:'js/rollcall-statis/model/rollcall-item.js', attrs:[charset:'GBK']
		resource url:'js/rollcall-statis/model/leave-item.js', attrs:[charset:'GBK']
		resource url:'js/rollcall-statis/student.js', attrs:[charset:'GBK']
		resource url:'css/rollcall-statis/student.css'
	}
	
	rollcallTeachersStatis {
		dependsOn 'base', 'arrangement', 'dot'
		resource url:'js/rollcall-statis/teachers.js', attrs:[charset:'GBK']
		resource url:'css/rollcall-statis/teachers.css'
	}
	
	rollcallTeacherPersonal {
		dependsOn 'base', 'arrangement', 'dot'
		resource url:'js/rollcall-statis/teacher.js', attrs:[charset:'GBK']
		resource url:'css/rollcall-statis/teacher.css'
	}
	
	rollcallGradeMajorAdminClass {
		dependsOn 'base'
		resource url:'js/lib/jquery.flot.min.js', attrs:[charset:'GBK']
		resource url:'js/lib/jquery.flot.categories.min.js', attrs:[charset:'GBK']
		resource url:'js/lib/jquery.flot.stack.min.js', attrs:[charset:'GBK']
		resource url:'js/rollcall-statis/gradeMajorAdminClass.js', attrs:[charset:'GBK']
		resource url:'css/rollcall-statis/gradeMajorAdminClass.css'
	}
	
	rollcallLock {
		dependsOn 'base'
		resource url:'js/ui/week-tabs.js', attrs:[charset:'GBK']
		resource url:'js/rollcall/admin-lock.js', attrs:[charset:'GBK']
		resource url:'css/rollcall/admin-lock.css'
	}
	
	cancelExam {
		dependsOn 'base'
		resource url:'js/cancel-exam/index.js', attrs:[charset:'GBK']
		resource url:'css/cancel-exam/index.css'
	}
	
	leaveForm {
		dependsOn 'base', 'tms-format', 'json3'
		resource url:'js/leave/model.js', attrs:[charset:'GBK']
		resource url:'js/ui/week-tabs.js', attrs:[charset:'GBK']
		resource url:'js/leave/schedule.js', attrs:[charset:'GBK']
		resource url:'js/leave/item-list.js', attrs:[charset:'GBK']
		resource url:'js/leave/form.js', attrs:[charset:'GBK']
		resource url:'css/leave/form.css'
	}
	
	leaveShow {
		dependsOn 'base'
		resource url:'js/leave/show.js', attrs:[charset:'GBK']
		resource url:'css/leave/show.css'
	}
	
	leaveApproval {
		dependsOn 'base'
		resource url:'js/leave/approval.js', attrs:[charset:'GBK']
		resource url:'css/leave/approval.css'
	}
	
	freeArrangementList {
		dependsOn 'base', 'dot', 'arrangement'
		resource url:'js/free-arr/list.js', attrs:[charset:'GBK']
		resource url:'css/free-arr/list.css'
	}
	
	freeArrangementForm {
		dependsOn 'base', 'dot', 'arrangement', 'json3'
		resource url:'js/free-arr/form.js', attrs:[charset:'GBK']
		resource url:'css/free-arr/form.css'
	}
	
	adminClassesAddressList {
		dependsOn 'base'
		resource url:'js/addresses/admin-classes.js', attrs:[charset:'GBK']
	}
	
	materialsAdmin {
		dependsOn 'base', 'dot', 'json3'
		resource url:'js/materials/admin.js', attrs:[charset:'GBK']
		resource url:'css/materials/admin.css'
	}
	
	termMaterialsAdmin {
		dependsOn 'base', 'dot'
		resource url:'js/materials/term.js', attrs:[charset:'GBK']
		resource url:'css/materials/term.css'
	}
	
	teachingMaterialList {
		dependsOn 'base', 'dot'
		resource url:'js/materials/teachers.js', attrs:[charset:'GBK']
		resource url:'css/materials/teachers.css'
	}
	
	personalMaterials {
		dependsOn 'base', 'dot'
		resource url:'js/materials/teacher.js', attrs:[charset:'GBK']
		resource url:'css/materials/teacher.css'
	}
	
	thesisTeachersAdmin {
		dependsOn 'base', 'dot'
		resource url:'js/thesis/teachers.js', attrs:[charset:'GBK']
		resource url:'css/thesis/teachers.css'
	}
	
	internshipTeachersAdmin {
		dependsOn 'base', 'dot'
		resource url:'js/internship/teachers.js', attrs:[charset:'GBK']
		resource url:'css/internship/teachers.css'
	}
	
	cardReissueRequestIndex {
		dependsOn 'base', 'dot', 'moment'
		resource url:'js/card/reissue/request/index.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/request/index.css'
	}
	
	cardReissueRequestForm {
		dependsOn 'base'
		resource url:'js/card/reissue/request/form.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/request/form.css'
	}
	
	cardReissueRequestShow {
		dependsOn 'base'
		resource url:'js/card/reissue/request/show.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/request/form.css'
	}
	
	cardReissueOrderIndex {
		dependsOn 'base'
		resource url:'css/card/reissue/order/index.css'
	}
	
	cardReissueOrderForm {
		dependsOn 'base', 'dot', 'tms', 'moment'
		resource url:'js/card/reissue/order/model.js', attrs:[charset:'GBK']
		resource url:'js/card/reissue/order/form.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/order/form.css'
	}
	
	cardReissueOrderShow {
		dependsOn 'base'
		resource url:'js/card/reissue/order/show.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/order/show.css'
	}
	
	cardReissueOrderReceive {
		dependsOn 'base', 'tms'
		resource url:'js/card/reissue/order/model.js', attrs:[charset:'GBK']
		resource url:'js/card/reissue/order/receive.js', attrs:[charset:'GBK']
		resource url:'css/card/reissue/order/receive.css'
	}
	
	issueList {
		dependsOn 'base', 'moment-with-lang'
		resource url:'js/issues/index.js', attrs:[charset:'GBK']
		resource url:'css/issues/index.css'
	}
	
	issueForm {
		dependsOn 'base', 'tms-validate'
		resource url:'js/lib/Markdown.Converter.min.js'
		resource url:'js/issues/form.js', attrs:[charset:'GBK']
		resource url:'css/issues/markdown.css'
		resource url:'css/issues/form.css'
	}
	
	issueShow {
		dependsOn 'base', 'dot', 'moment-with-lang', 'tms-validate'
		resource url:'js/lib/Markdown.Converter.min.js'
		resource url:'js/issues/show.js', attrs:[charset:'GBK']
		resource url:'css/issues/markdown.css'
		resource url:'css/issues/show.css'
	}
	
	issueAdmin {
		dependsOn 'issueShow'
		resource url:'js/issues/admin.js', attrs:[charset:'GBK']
		resource url:'css/issues/admin.css'
	}
	
	issueHelp {
		dependsOn 'base'
		resource url:'js/lib/Markdown.Converter.min.js'
		resource url:'js/issues/help.js', attrs:[charset:'GBK']
		resource url:'css/issues/markdown.css'
		resource url:'css/issues/help.css'
	}
	
	'booking-format' {
		dependsOn 'tms-format'
		resource url:'js/place/bookings/format.js', attrs:[charset:'GBK']
	}

	bookingList {
		dependsOn 'base', 'dot', 'booking-format', 'pager'
		resource url:'js/place/bookings/index.js', attrs:[charset:'GBK']
		resource url:'css/place/bookings/index.css'
	}
	
	bookingNotice {
		dependsOn 'base'
		resource url:'css/place/bookings/notice.css'
	}
	
	bookingForm {
		dependsOn 'base', 'dot', 'moment-with-lang', 'booking-format', 'tms-validate'
		resource url:'js/place/bookings/model.js', attrs:[charset:'GBK']
		resource url:'js/place/bookings/form.js', attrs:[charset:'GBK']
		resource url:'css/place/bookings/form.css'
	}
	
	bookingShow {
		dependsOn 'base', 'dot', 'moment-with-lang', 'booking-format'
		resource url:'js/place/bookings/model.js', attrs:[charset:'GBK']
		resource url:'js/place/bookings/show.js', attrs:[charset:'GBK']
		resource url:'css/place/bookings/show.css'
	}
	
	bookingCheck {
		dependsOn 'base', 'dot', 'moment-with-lang', 'booking-format', 'tms-validate'
		resource url:'js/place/bookings/model.js', attrs:[charset:'GBK']
		resource url:'js/place/booking-check/index.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-check/index.css'
	}
	
	bookingApprove {
		dependsOn 'base', 'dot', 'moment-with-lang', 'booking-format', 'tms-validate'
		resource url:'js/place/bookings/model.js', attrs:[charset:'GBK']
		resource url:'js/place/booking-approve/index.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-approve/index.css'
	}
	
	bookingAdCheckers {
		dependsOn 'base', 'dot'
		resource url:'js/place/booking-admin/ad-checkers.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-admin/ad-checkers.css'
	}
	
	bookingTdCheckers {
		dependsOn 'base', 'dot'
		resource url:'js/place/booking-admin/td-checkers.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-admin/td-checkers.css'
	}
	
	bookingReportIndex {
		dependsOn 'base', 'pager'
		resource url:'css/place/booking-report/index.css'
	}
	
	bookingReportForm {
		dependsOn 'base', 'dot', 'tms', 'moment', 'booking-format'
		resource url:'js/place/booking-report/model.js', attrs:[charset:'GBK']
		resource url:'js/place/booking-report/form.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-report/form.css'
	}
	
	bookingReportShow {
		dependsOn 'base', 'booking-format'
		resource url:'js/place/booking-report/show.js', attrs:[charset:'GBK']
		resource url:'css/place/booking-report/show.css'
	}
}