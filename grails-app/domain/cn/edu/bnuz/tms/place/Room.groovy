package cn.edu.bnuz.tms.place

class Room {
	String id
	String name
	String type
	Integer seat
	String building
	Integer floorNumber
	String roomNumber
	Double floorArea
	String openGroup
	
	@Override
	String toString() {
		"Room[$name]"
	}
		
	static mapping = {
		table 		'edu_room'
		id 			generator: 'assigned', length: 6
		name		length: 50
		type        length: 50
		building    length: 50
		roomNumber  length: 50
		floorArea   sqlType : 'numeric(6,2)'
		openGroup	 	length: 50
	}
	
	static constraints = {
		seat		nullable: true
		roomNumber	nullable: true
		floorArea 	nullable: true
		openGroup	nullable: true
	}
}
