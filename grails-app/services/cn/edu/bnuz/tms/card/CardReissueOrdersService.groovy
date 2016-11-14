package cn.edu.bnuz.tms.card
import cn.edu.bnuz.tms.card.CardReissueOrderCommand;
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.rollcall.LeaveRequestCommand;
import cn.edu.bnuz.tms.card.ReissueItem;
import cn.edu.bnuz.tms.card.ReissueOrder;
import cn.edu.bnuz.tms.card.ReissueRequest;

class CardReissueOrdersService {
	def getAll() {
		ReissueOrder.executeQuery """
select new map(
  ro.id as orderId,
  (select count(*) from ReissueItem ri where ri.order = ro) as requestCount,
  (select count(*) from ReissueItem ri where ri.order = ro and ri.request.status = 4) as finishedCount,
  creator.name as creatorName,
  ro.dateCreated as dateCreated,
  modifier.name as modifierName,
  ro.dateModified as dateModified
)
from ReissueOrder ro
join ro.creator creator
left join ro.modifier modifier
order by ro.id desc
"""
	}

	def getInfo(Long id) {
		def result = ReissueOrder.executeQuery """
select new map(
  ro.id as orderId,
  creator.name as creatorName,
  ro.dateCreated as dateCreated,
  modifier.name as modifierName,
  ro.dateModified as dateModified
)
from ReissueOrder ro
join ro.creator creator
left join ro.modifier modifier
where ro.id = :id
""", [id: id]
		if(result.size() > 0) {
			def reissueOrder = result[0]
			reissueOrder.items = ReissueItem.executeQuery """
select new map(
  ri.id as itemId,
  re.id as requestId,
  re.dateModified as applyDate,
  re.status as status,
  st.id as studentId,
  st.name as name,
  st.sex as sex,
  st.province as province,
  st.birthday as birthday,
  st.educationLevel as educationLevel,
  concat(st.grade + st.lengthOfSchooling, '-7-1') as validDate,
  st.adminClass.name as adminClass,
  st.adminClass.department.name as department,
  st.major.name as major
)
from ReissueItem ri
join ri.request re
join re.student st
where ri.order.id = :id
""", [id: id]
			return reissueOrder
		} else {
			return null
		}
	}
	
	ReissueOrder create(String teacherId, CardReissueOrderCommand rcoc) {
		ReissueOrder reissueOrder = new ReissueOrder(
			creator: Teacher.load(teacherId),
			dateModified: new Date(),
		)

		rcoc.addedItems.each {
			// 更新申请状态为处理中
			def reissueRequest = ReissueRequest.get(it.requestId)
			if(reissueRequest.allowStatus(ReissueRequest.STATUS_MAKING)) {
				reissueRequest.status = ReissueRequest.STATUS_MAKING
				reissueRequest.save(failOnError:true)
				// 添加订单项
				def orderItem = new ReissueItem(request: reissueRequest)
				reissueOrder.addToItems(orderItem)
			}
		}
		
		reissueOrder.save(failOnError:true)
	}
	
	boolean update(String teacherId, CardReissueOrderCommand rcoc) {
		ReissueOrder reissueOrder = ReissueOrder.get(rcoc.orderId)

		reissueOrder.modifier = Teacher.load(teacherId)
		reissueOrder.dateModified = new Date()

		rcoc.addedItems.each {
			// 更新申请状态为处理中
			def reissueRequest = ReissueRequest.get(it.requestId)
			if(reissueRequest.allowStatus(ReissueRequest.STATUS_MAKING)) {
				reissueRequest.status = ReissueRequest.STATUS_MAKING
				reissueRequest.save(failOnError:true)
				// 添加订单项
				def orderItem = new ReissueItem(request: reissueRequest)
				reissueOrder.addToItems(orderItem)
			}
		}

		rcoc.removedItems.each {
			// 更新申请状态为新申请
			def reissueRequest = ReissueRequest.get(it.requestId)
			if(reissueRequest.allowStatus(ReissueRequest.STATUS_APPLYING)) {
				reissueRequest.status = ReissueRequest.STATUS_APPLYING
				reissueRequest.save(failOnError:true)
				// 删除订单项
				def orderItem = ReissueItem.load(it.itemId)
				reissueOrder.removeFromItems(orderItem)
				orderItem.delete()
			}
		}
		
		reissueOrder.save(failOnError:true)

		return true
	}
	
	boolean delete(Long id) {
		def reissueOrder = ReissueOrder.get(id)
		if(!reissueOrder) {
			return false;
		}
		
		boolean allowStatus = reissueOrder.items.every { item ->
			item.request.allowStatus(ReissueRequest.STATUS_APPLYING)
		}
		
		if(!allowStatus) {
			return false;
		}
		
		reissueOrder.items.each { item ->
			item.request.status = ReissueRequest.STATUS_APPLYING
			item.request.save(failOnError:true)
		}
		
		reissueOrder.delete()
		
		return true
	}
	
	def receive(Long requestId, boolean received) {
		def reissueRequest = ReissueRequest.get(requestId)
		def status = received ? ReissueRequest.STATUS_FINISHED : ReissueRequest.STATUS_MAKING;
		if(!reissueRequest.allowStatus(status)) {
			return false
		}
		
		reissueRequest.status = status
		reissueRequest.save(failOnError:true)
		return true
	}
	
	def receiveAll(Long id, boolean received) {
		ReissueOrder.executeUpdate """
update ReissueRequest
set status = :newStatus
where id in (
    select ri.request.id
    from ReissueOrder ro
    join ro.items ri
    where ro.id = :id
) and status = :oldStatus
""", [id: id, 
	  oldStatus: received ? ReissueRequest.STATUS_MAKING : ReissueRequest.STATUS_FINISHED,
	  newStatus: received ? ReissueRequest.STATUS_FINISHED : ReissueRequest.STATUS_MAKING]
		
		return true
	}
}
