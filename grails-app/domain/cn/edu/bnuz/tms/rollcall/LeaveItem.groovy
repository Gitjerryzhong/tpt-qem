package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.teaching.Arrangement

/**
 * 假条项。可以按周、天、安排请假。
 * <ul>
 * <li>如果按周请假，则week不为空，day和arrangement为空</li>
 * <li>如果按天请假，则week和dayOfWeek不为空，arrangement为空</li>
 * <li>如果按安排请假，则week和arrangement不为空，dayOfWeek为空</li>
 * </ul>
 * @author 杨林
 * @since 0.1
 * @see LeaveRequest
 */
class LeaveItem implements Comparable<LeaveItem> {
	/**
	 * 周次。
	 */
	Integer week
	
	/**
	 * 按天请假。注意：比较时不能使用if(dayOfWeek)，要使用if(dayOfWeek != null)，
	 * 因为星期日的值为0，if(dayOfWeek)为假。
	 */
	Integer dayOfWeek // 数字类型允许为空时，类型必须为Long/Integer，不能为long/int

	/**
	 * 按教学安排请假
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
