package cn.edu.bnuz.tms.teaching

import org.joda.time.LocalDate
import org.joda.time.Weeks
/**
 * ѧ�ڡ�
 * @author yanglin
 *
 */
class Term {
	/**
	 * ��ʼ����
	 */
	LocalDate startDate
	
	/**
	 * �������ڣ��Ͽν�����
	 */
	LocalDate endDate
	
	/**
	 * ������ڣ����ڽ�����
	 */
	LocalDate maxDate
	
	/**
	 * ��ʼ��
	 */
	int startWeek
	
	/**
	 * �����ܣ��Ͽν�����
	 */
	int endWeek
	
	
	/**
	 * ����ܣ����ڽ�����
	 */
	int maxWeek
	
	
	boolean active
	
	/**
	 * ���ݵ�ǰ���ڼ��㵱ǰ�ܡ�
	 * @return ��ǰ�ܡ����С�ڿ�ʼ�ܣ���ȡ��ʼ�ܣ�������ڽ����ܣ���ȡ�����ܡ�
	 */
	def getCurrentWeek() {
		def current =  Weeks.weeksBetween(startDate, LocalDate.now()).value + startWeek
		if(current < startWeek) {
			return startWeek
		} else if(current > endWeek){
			return endWeek
		} else {
			return current
		}
	}
	
	/**
	 * ���ݵ�ǰ���ڼ��㵱ǰ�ܡ�
	 * �ڽ����ʱʹ�á�
	 * @return ��ǰ�ܡ����С�ڿ�ʼ�ܣ���ȡ��ʼ�ܣ������������ܣ���ȡ����ܡ�
	 */
	def getActualWeek() {
		def current =  Weeks.weeksBetween(startDate, LocalDate.now()).value + startWeek
		if(current < startWeek) {
			return startWeek
		} else if(current > maxWeek){
			return maxWeek
		} else {
			return current
		}
	}
	
	String getEsYear() {
		def year = (int)(this.id / 10) 
		return "${year}-${year + 1}"
	}
	
	int getEsTerm() {
		return this.id % 10
	}
	
	List<LocalDate> getSwapToDates() {
		swaps.collect {
			it.toDate
		}
	}
	
	@Override
	String toString() {
		"Term[$id, from:${startDate.format('yyyy-MM-dd')}, to:${endDate.format('yyyy-MM-dd')}, week:$startWeek-$endWeek]"
	}

	static hasMany = [
		swaps : Swap // У�����ڵ���
	]
	
	static mapping = {
		table 		'edu_term'
		id 			generator:'assigned' // 2012-2013-1 -> 20121
		active		default: false
		cache		true
	}
}
