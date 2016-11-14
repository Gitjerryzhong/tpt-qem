package cn.edu.bnuz.qem.organization

class ReviewExperts {
	static belongsTo =[experts:Experts]
	
	String options
	
	String score
	
	Date finishDate
	static mapping={
		table	'qem_review_experts'		
	}
    static constraints = {
		options(maxSize:1500)
    }
}
