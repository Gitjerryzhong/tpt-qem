package cn.edu.bnuz.tms.teaching

import org.joda.time.LocalDate
import org.joda.time.Weeks
/**
 * 学期。
 * @author yanglin
 *
 */
class Term {
	/**
	 * 开始日期
	 */
	LocalDate startDate
	
	/**
	 * 结束日期（上课结束）
	 */
	LocalDate endDate
	
	/**
	 * 最大日期（假期结束）
	 */
	LocalDate maxDate
	
	/**
	 * 开始周
	 */
	int startWeek
	
	/**
	 * 结束周（上课结束）
	 */
	int endWeek
	
	
	/**
	 * 最大周（假期结束）
	 */
	int maxWeek
	
	
	boolean active
	
	/**
	 * 根据当前日期计算当前周。
	 * @return 当前周。如果小于开始周，则取开始周；如果大于结束周，则取结束周。
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
	 * 根据当前日期计算当前周。
	 * 在借教室时使用。
	 * @return 当前周。如果小于开始周，则取开始周；如果大于最大周，则取最大周。
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
		swaps : Swap // 校历日期调换
	]
	
	static mapping = {
		table 		'edu_term'
		id 			generator:'assigned' // 2012-2013-1 -> 20121
		active		default: false
		cache		true
	}
}
