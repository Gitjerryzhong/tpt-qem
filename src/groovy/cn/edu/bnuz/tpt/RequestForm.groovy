package cn.edu.bnuz.tpt

import java.util.Date;

class RequestForm {
	Long formId
	String contact
	String phoneNumber
	String email
	int  foreignCollege	//学院不再维护国外大学表2016-09-07
	String collegeName	//增加国外大学名称
	String foreignMajor
	String photoUrl
	String resultsUrl
	String certificateUrl
	String paperUrl
	String bn
	boolean allIn
	int status
}
