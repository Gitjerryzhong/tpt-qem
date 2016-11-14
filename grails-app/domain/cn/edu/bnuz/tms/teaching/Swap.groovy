package cn.edu.bnuz.tms.teaching
import org.joda.time.LocalDate

/**
 * 校历日期调换
 * @author yanglin
 *
 */
class Swap {
	LocalDate fromDate
	LocalDate toDate
	
	static belongsTo = [term: Term]
	
	static mapping = {
		table 		'edu_swap'
	}
	
	@Override
	String toString() {
		"Swap[$id, from:${fromDate}, to:${toDate}"
	}
}
