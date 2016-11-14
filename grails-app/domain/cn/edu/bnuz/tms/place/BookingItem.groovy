package cn.edu.bnuz.tms.place


class BookingItem {
	Room room
	Integer startWeek
	Integer endWeek
	Integer dayOfWeek
	Integer sectionType
	boolean occupied
	
	static belongsTo = [
		form: BookingForm
	]
	
	static mapping = {
		table 		'aff_booking_item'
	}
}
