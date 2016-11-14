package cn.edu.bnuz.tms.rollcall

class LeaveRequestCommand {
	/**
	 * 只用于更新操作
	 */
	String requestId
	String reason
	Integer type
	Long term
	List<LeaveItem> items

	/**
	 * 只用于更新操作
	 */
	List<LeaveItem> removedItems
	
	class LeaveItem {
		/**
		 * 只用于更新操作
		 */
		Long id
		Integer week
		Integer day
		String arr
	}
}
