package cn.edu.bnuz.qem.project

import java.util.Date;

class Attention {
	String title
	
	String content
	String experList
	
	Date publishDate
	
	String author
	static mapping ={
		table	'qem_attention'
	}
    static constraints = {
		experList nullable:true
		content(maxSize:1500,nullable: true)
    }
}
