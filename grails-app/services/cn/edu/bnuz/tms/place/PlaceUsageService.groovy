package cn.edu.bnuz.tms.place

import groovy.sql.Sql

class PlaceUsageService {
	def dataSource_es
	
	def findBuildings(Boolean isTeacher, Long termId) {
		new Sql(dataSource_es).rows("""
select distinct building
from room
where ((user_type = '11' and 0 = :isTeacher) or
      ((user_type = '01' or user_type = '11') and 1 = :isTeacher))
  and (enabled = 1 or (enabled = 0 and booking_term = :termId))
order by building
""", [isTeacher: isTeacher ? 1 : 0, termId: termId]).collect {
			it.BUILDING
		}
	}
	
	def findRooms(Boolean isTeacher, Long termId, String building) {
		new Sql(dataSource_es).rows("""
select distinct id, name, seat, type
from room
where ((user_type = '11' and 0 = :isTeacher) or
      ((user_type = '01' or user_type = '11') and 1 = :isTeacher))
  and (enabled = 1 or (enabled = 0 and booking_term = :termId))
  and building = :building
order by name
""", [isTeacher: isTeacher ? 1 : 0, termId: termId, building: building]).collect {[
			id:   it.ID,
			name: it.NAME,
			seat: it.SEAT,
			type: it.TYPE
		]}
	}
	
	def findUsages(Long termId, String roomId) {
		new Sql(dataSource_es).rows("""
select start_week, end_week, odd_even, day_of_week, 
start_section, total_section, type, department, description 
from place_usage
where term = :termId
  and room_id = :roomId
order by start_week, day_of_week, start_section
""", [termId: termId, roomId: roomId]).collect {[
			startWeek:    it.START_WEEK,
			endWeek:      it.END_WEEK,
			oddEven:      it.ODD_EVEN,
			dayOfWeek:    it.DAY_OF_WEEK,
			startSection: it.START_SECTION,
			totalSection: it.TOTAL_SECTION,
			type:         it.TYPE,
			department:   it.DEPARTMENT,
			description:  it.DESCRIPTION
		]}
	}
}
