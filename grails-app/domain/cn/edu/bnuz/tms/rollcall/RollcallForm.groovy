package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Arrangement


/**
 * ���ڱ�
 * @author young
 *
 */
class RollcallForm {
	public static final int NEW = 0
	public static final int LOCKED = 1

	int week
	int status
	Date dateCreated
	
	/**
	 * �޸�ʱ�䡣��ʹ��lastUpdated���ֹ������ֶθ��¡�
	 */
	Date dateModified
	Arrangement arrangement
	Teacher teacher
	
	static hasMany = [
		items : RollcallItem
	]

	static belongsTo = [
		Arrangement,
		Teacher
	]

	// ��������:
	// arrangement_week_idx(arrangement_id, week)
	// arrangement_idx(arrangement_id)
	static mapping = { 
		table 		'rc_rollcall_form'
		arrangement index: 'arrangement_week_idx,arrangement_idx'
		week 		index: 'arrangement_week_idx'
		status		defaultValue: RollcallForm.NEW
	}

	static constraints = { 
		week 		unique:'arrangement'
	}
	
}
