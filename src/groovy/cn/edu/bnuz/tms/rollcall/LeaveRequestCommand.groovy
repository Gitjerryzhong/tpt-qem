package cn.edu.bnuz.tms.rollcall

class LeaveRequestCommand {
	/**
	 * ֻ���ڸ��²���
	 */
	String requestId
	String reason
	Integer type
	Long term
	List<LeaveItem> items

	/**
	 * ֻ���ڸ��²���
	 */
	List<LeaveItem> removedItems
	
	class LeaveItem {
		/**
		 * ֻ���ڸ��²���
		 */
		Long id
		Integer week
		Integer day
		String arr
	}
}
