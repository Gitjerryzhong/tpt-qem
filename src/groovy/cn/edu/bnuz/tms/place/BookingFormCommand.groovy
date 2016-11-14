package cn.edu.bnuz.tms.place

import java.util.List;

class BookingFormCommand {
	Long formId
	String departmentId
	Long bookingTypeId
	String reason
	
	List<FormItem> addedItems
	
	List<FormItem> removedItems
	
	class FormItem {
		Long itemId
		String roomId
		Integer startWeek
		Integer endWeek
		Integer dayOfWeek
		Integer sectionType
	}
}
