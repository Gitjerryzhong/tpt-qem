package cn.edu.bnuz.tms.card

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService

class CardReissueOrdersController {
	SecurityService securityService
	CardReissueOrdersService cardReissueOrdersService
	CardReissueOrderExportService cardReissueOrderExportService
	
	def index() {
		[orders: cardReissueOrdersService.getAll()]
	}
	
	def newForm() {
		render view:'form'
	}
	
	def create() {
		def teacherId =  securityService.userId
		def rcoc = new CardReissueOrderCommand(request.JSON)
		def reissueOrder = cardReissueOrdersService.create(teacherId, rcoc)
		render(contentType:"text/json") { id = reissueOrder.id }
	}
	
	def show(Long id) {
		def reissueOrder = cardReissueOrdersService.getInfo(id)
		if(reissueOrder) {
			[order: reissueOrder]
		} else {
			response.sendError(404)
		}
	}
	
	def editForm(Long id) {
		def reissueOrder = cardReissueOrdersService.getInfo(id)
		if(reissueOrder) {
			render view:'form', model:[
				order: reissueOrder
			]
		} else {
			response.sendError(404)
		}
	}
	
	def update() {
		def userId =  securityService.userId
		def lrc = new CardReissueOrderCommand(request.JSON)
		def result = cardReissueOrdersService.update(userId, lrc)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete(Long id) {
		def result = cardReissueOrdersService.delete(id)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def receive(Long id) {
		def reissueOrder = cardReissueOrdersService.getInfo(id)
		if(reissueOrder) {
			[order: reissueOrder]
		} else {
			response.sendError(404)
		}
	}
	
	def modify(Long id) {
		def requestId = params.long('requestId')
		def received = params.boolean('received')
		def result
		if(requestId){
			result = cardReissueOrdersService.receive(requestId, received)
		} else {
			result = cardReissueOrdersService.receiveAll(id, received)
		}
		
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def photos(Long id) {
		def order = cardReissueOrdersService.getInfo(id)
		if(!order) {
			render status: HttpStatus.NOT_FOUND
		}
		
		response.setHeader("Content-disposition", "filename=\"${order.orderId}.zip\"")
		response.contentType = "application/zip"
		response.outputStream << cardReissueOrderExportService.exportPhotos(order)
		response.outputStream.flush()
	}
	
	def export(Long id) {
		def order = cardReissueOrdersService.getInfo(id)
		if(!order) {
			render status: HttpStatus.NOT_FOUND
		}
		def type = params.type
		def template
		def workbook
		if(type == "order") {
			template = grailsApplication.mainContext.getResource("excel/card-reissue-order.xls").getFile()
			workbook = cardReissueOrderExportService.exportOrder(template, order)
		} else if (type == "distribute") {
			template = grailsApplication.mainContext.getResource("excel/card-reissue-distribute.xls").getFile()
			workbook = cardReissueOrderExportService.exportDistribute(template, order)
		} else {
			render status: HttpStatus.BAD_REQUEST
			return
		}
		response.setContentType("application/excel")
		response.setHeader("Content-disposition", "attachment;filename=\"${type}-${order.orderId}.xls\"")
		workbook.write(response.outputStream)
		return
	}
}
