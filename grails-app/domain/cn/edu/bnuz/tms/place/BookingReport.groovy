package cn.edu.bnuz.tms.place

import java.util.Date;

import cn.edu.bnuz.tms.organization.Teacher

class BookingReport {
	/**
	 * 创建人
	 */
	Teacher creator
	
	/**
	 * 修改时间
	 */
	Teacher modifier
	
	/**
	 * 创建时间
	 */
	Date dateCreated
	
	/**
	 * 修改时间
	 */
	Date dateModified
	static hasMany = [
		bookings: BookingForm
	]
	
	static mapping = {
		table 		'aff_booking_report'
	}
	
	static constraints = {
		modifier     nullable: true
		dateModified nullable: true
	}
}
