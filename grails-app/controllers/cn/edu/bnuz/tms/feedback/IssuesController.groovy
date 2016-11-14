package cn.edu.bnuz.tms.feedback

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role

class IssuesController {
	SecurityService securityService
	IssuesService issuesService
	
	def index() {
		String userId = securityService.userId
		def statis = issuesService.statis(userId)
		def issues
		if(params.containsKey("personal")) {
			statis.active = -1
			issues = issuesService.personal(userId)
		} else if(params.containsKey("invisible")){
			if(securityService.hasRole(Role.ISSUE_ADMIN) ||
				securityService.hasRole(Role.SYSTEM_ADMIN)) {
				statis.active = -2
				issues = issuesService.list(false)
			} else {
				response.sendError(404)
				return
			}
		} else if(params.containsKey("type")){		
			int type = params.int("type")
			statis.active = type
			issues = issuesService.listByType(type)
		} else {
			statis.active = 0
			issues = issuesService.list(true)
		}
		return [statis:statis, issues:issues]
	}
	
	def newForm() {
		String userId = securityService.userId
		def statis = issuesService.statis(userId)
		render view:'form', model:[statis:statis]
	}
	
	def create() {
		String userId = securityService.userId
		String userName = securityService.userName
		def issueCommand = new IssueCommand(request.JSON)
		def issue = issuesService.create(userId, userName, issueCommand)
		render(contentType:"text/json") { id = issue.id }
	}
	
	def editForm(Long id) {
		String userId = securityService.userId
		def statis = issuesService.statis(userId)
		def issue = issuesService.get(id)
		if(issue.userId == userId) {
			render view:'form', model:[statis: statis, issue: issue]
		} else {
			response.sendError(404)
		}
	}
	
	def update(Long id) {
		def userId =  securityService.userId
		def issueCommand = new IssueCommand(request.JSON)
		def result = issuesService.update(id, userId, issueCommand)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	} 
	
	def show(Long id) {
		String userId = securityService.userId
		String userName = securityService.userName
		def statis = issuesService.statis(userId)
		def issue = issuesService.getInfo(id, userId)
		if(issue && (issue.visible || 
			securityService.hasRole(Role.ISSUE_ADMIN) ||
			securityService.hasRole(Role.SYSTEM_ADMIN))) {
			return [
				statis: statis,
				issue: issue,
				currentUserId: securityService.userId,
				currentUserName: securityService.userName,
			]
		} else {
			response.sendError(404)
		}
	}
	
	def modify(Long id, String type) {
		String userId = securityService.userId
		def result = false
		switch(type) {
			case "support":
				result = issuesService.support(id, userId)
				break;
			case "cancel-support":
				result = issuesService.cancelSupport(id, userId)
				break;
			case "delete":
				if(securityService.hasRole(Role.ISSUE_ADMIN)) {
					result = issuesService.delete(id)
				}
				break;
			case "hide":
				if(securityService.hasRole(Role.ISSUE_ADMIN)) {
					result = issuesService.hide(id)
				}
				break;
			case "close":
				if(securityService.hasRole(Role.ISSUE_ADMIN)) {
					result = issuesService.close(id)
				}
				break;
			case "open":
				if(securityService.hasRole(Role.ISSUE_ADMIN)) {
					result = issuesService.open(id)
				}
				break;
		}
		
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def createComment(Long issueId) {
		String userId = securityService.userId
		String userName = securityService.userName
		def comment = issuesService.createComment(
			issueId, 
			userId, 
			userName, 
			request.JSON.content
		)
		
		render template: 'comment', model:[
			comment:comment,
			currentUserId: userId,
			currentUserName: userName,
		]
	}
	
	def updateComment(Long issueId, Long commentId) {
		String userId = securityService.userId
		String userName = securityService.userName
		def result = issuesService.updateComment(
			issueId,
			commentId,
			request.JSON.content
		)
		
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def modifyComment(Long issueId, Long commentId, String type) {
		String userId = securityService.userId
		def result
		switch(type) {
			case "support":
				result = issuesService.supportComment(issueId, commentId, userId)
				break;
			case "cancel-support":
				result = issuesService.cancelSupportComment(issueId, commentId, userId)
				break;
			case "hide":
				if(securityService.hasRole(Role.ISSUE_ADMIN)) {
					result = issuesService.hideComment(issueId, commentId)
				}
				break;
		}
		
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def help() {
		String userId = securityService.userId
		def statis = issuesService.statis(userId)
		return [statis: statis]
	}
}
