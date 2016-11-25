package cn.edu.bnuz.tpt

import java.util.Date;

class TptNotice {
	int id
	String title
	String content
	Date start	
	Date end
	String bn
	Date publishDate
	String publisher

	static mapping={
		table	'tpt_notice'
		title length: 100
		bn length: 10
		publisher length: 5
	}

    static constraints = {
		content(maxSize:1500)
		bn nullable:true	/*不再使用2015-11-20*/
		publisher	nullable:true
		publishDate nullable:true
    }
}
