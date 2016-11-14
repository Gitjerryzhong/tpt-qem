package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.teaching.Arrangement

/**
 * ��������԰��ܡ��졢������١�
 * <ul>
 * <li>���������٣���week��Ϊ�գ�day��arrangementΪ��</li>
 * <li>���������٣���week��dayOfWeek��Ϊ�գ�arrangementΪ��</li>
 * <li>�����������٣���week��arrangement��Ϊ�գ�dayOfWeekΪ��</li>
 * </ul>
 * @author ����
 * @since 0.1
 * @see LeaveRequest
 */
class LeaveItem implements Comparable<LeaveItem> {
	/**
	 * �ܴΡ�
	 */
	Integer week
	
	/**
	 * ������١�ע�⣺�Ƚ�ʱ����ʹ��if(dayOfWeek)��Ҫʹ��if(dayOfWeek != null)��
	 * ��Ϊ�����յ�ֵΪ0��if(dayOfWeek)Ϊ�١�
	 */
	Integer dayOfWeek // ������������Ϊ��ʱ�����ͱ���ΪLong/Integer������Ϊlong/int

	/**
	 * ����ѧ�������
	 */
	Arrangement arrangement
	
	static belongsTo = [
		leaveRequest : LeaveRequest
	]
	
	static mapping = {
		table 		'rc_leave_item'
	}
	
	static constraints = {
		week		range:1..24
		dayOfWeek 	nullable:true, range:0..6
		arrangement nullable:true
	}
	
	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("[week: $week")
		if(dayOfWeek != null)
		  	sb.append(" dayOfWeek: $dayOfWeek]")
		else if(arrangement != null)
		  	sb.append(" arrangement: $arrangement]")
		else 
			sb.append("]")  
		return sb.toString()
	}

	@Override
	public int compareTo(LeaveItem o) {
		return week <=> o.week ?:
		       (dayOfWeek != null ? (dayOfWeek?:7) : arrangement.dayOfWeek)  <=> 
			   (o.dayOfWeek != null ? (o.dayOfWeek?:7) :o.arrangement.dayOfWeek) ?:
			   arrangement.startSection <=> o.arrangement.startSection;
	}
}
