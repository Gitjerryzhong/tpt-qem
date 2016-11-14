package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student


/**
 * ¿¼ÇÚÏî
 * @author young
 *
 */
class RollcallItem {
	public static final int ABSENT = 1
	public static final int LATE = 2
	public static final int EARLY = 3
	public static final int LEAVE = 4
	public static final int EARLY_LATE = 5
	public static final int ATTEND = 6

	int type
	Date dateCreated
	Date lastUpdated
	Student student
	
	static belongsTo = [
		form : RollcallForm
	]
	
	static mapping = { 
		table 'rc_rollcall_item' 
	}
	
	static constraints = {
		student unique:'form'
	}
}
