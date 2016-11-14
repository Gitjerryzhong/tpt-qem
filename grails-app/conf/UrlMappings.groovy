class UrlMappings {
	static mappings = {

		"/" (controller:"index")
		
		"/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

		// Rails风格URL Mapping，见http://guides.rubyonrails.org/routing.html
		// PUT是幂等的，POST是非幂等的
		"/$controller" {
			action = [GET: "index", POST: "create", PUT: "update"]
		}
		
		"/$controller/new" {
			action = [GET: "newForm"]
		}
		
		"/$controller/$action" {
			
		}
		
		name item:
		"/$controller/$id" {
			action = [GET:"show", PUT: "update", DELETE: "delete", POST: "modify"]
			constraints {
		        id matches: /\d+/
		    }
		}
		"/$controller/$id/edit" {
			action = [GET: "editForm"]
		}

		// 考勤特殊处理
		"/rollcall/$arrangementId/$week"(controller: "rollcall", action: "form")
		"/rollcall/form"                (controller: "rollcall") {action = [POST: "createForm"]}
		"/rollcall/item"                (controller: "rollcall") {action = [POST: "createItem"]}
		"/rollcall/item/$id"            (controller: "rollcall") {action = [PUT: "updateItem", DELETE: "deleteItem"]}
		"/rollcall/lock"				(controller: "rollcall") {action = [GET: "lockList", POST:"lock"]}
		"/rollcall/adminclass"          (controller: "rollcallStatis", action: "adminClass")
		"/rollcall/grade"               (controller: "rollcallStatis", action: "grade")
		"/rollcall/department"          (controller: "rollcallStatis", action: "department")
		"/rollcall/gmac"                (controller: "rollcallStatis", action: "gradeMajorAdminClass")
		"/rollcall/student/$id"         (controller: "rollcallStatis", action: "student")
		"/rollcall/teachers"          	(controller: "rollcallStatis", action: "teachers")
		"/rollcall/teachers/$id/$week"  (controller: "rollcallStatis", action: "teacher")
		"/rollcall/personal"   			(controller: "rollcallStatis", action: "personal")
		"/rollcall/mycourse"   			(controller: "rollcallStatis", action: "courseClass")
		"/rollcall/personal/export/$id"	(controller: "rollcallStatis", action: "personalExport")
		"/rollcall/arrangements/$week"	(controller: "rollcallStatis", action: "rollcalled")

		"/teacher/setting"(controller: "teacher"){ action = [POST: "changeSetting"]}
		
		"/issues/help"							(controller: "issues", action:"help")
		"/issues/$issueId/comments"				(controller: "issues") { action = [POST: "createComment"]}
		"/issues/$issueId/comments/$commentId"	(controller: "issues") { action = [PUT: "updateComment", DELETE: "deleteComment", POST: "modifyComment"]}

		"/bookings/findRoom"					(controller: 'bookings', action:"findRoom")
		"/bookings/export"						(controller: 'bookings', action:"export")
		"/placeUsage/rooms"						(controller: 'placeUsage', action:"rooms")
		

		"404"(view:"/errors/not-found")
		"500"(view:'/errors/error')
	}
}
