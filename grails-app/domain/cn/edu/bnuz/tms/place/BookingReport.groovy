package cn.edu.bnuz.tms.place

import java.util.Date;

import cn.edu.bnuz.tms.organization.Teacher

class BookingReport {
	/**
	 * ������
	 */
	Teacher creator
	
	/**
	 * �޸�ʱ��
	 */
	Teacher modifier
	
	/**
	 * ����ʱ��
	 */
	Date dateCreated
	
	/**
	 * �޸�ʱ��
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
