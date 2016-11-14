package cn.edu.bnuz.tpt

import java.text.SimpleDateFormat
import java.util.Date;

class NoticeForm {
	int id
	String title
	String content
	String start	
	String end
	String bn
	private Date toDate(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dateStr!=null && dateStr.length()>=10){
			Date date=sdf.parse(dateStr.substring(0,10))
			return date
		}else{
			return null
		}
	}
	def Date getStartDate(){
		return toDate(start)
	}
	def Date getEndDate(){
		return toDate(end)
	}
}
