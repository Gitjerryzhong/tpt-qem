package cn.edu.bnuz.qem.project

import java.util.Date;

class Attention {
	String title
	
	String content
	
	Date publishDate
	
	String author
	static mapping ={
		table	'qem_attention'
	}
    static constraints = {
		content(maxSize:1500,nullable: true)
    }
}
