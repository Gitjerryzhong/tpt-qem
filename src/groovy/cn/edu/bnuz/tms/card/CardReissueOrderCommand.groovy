package cn.edu.bnuz.tms.card

class CardReissueOrderCommand {
	Long orderId
	
	List<OrderItem> addedItems
	
	List<OrderItem> removedItems
	
	class OrderItem {
		Long itemId
		Long requestId
	}
}
