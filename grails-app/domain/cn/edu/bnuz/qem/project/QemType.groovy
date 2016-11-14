package cn.edu.bnuz.qem.project

class QemType {
    
	QemParentType parentType
	
	String name
	
	int cycle
	boolean actived
	
	String downLoadUrl
	static mapping={
		table	'qem_type'		
		name	lenght:50
	}
    static constraints = {	
		parentType()
		name(unique:'parentType')
		actived nullable:true
		cycle()
		downLoadUrl()
    }
}
