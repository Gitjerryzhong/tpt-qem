package cn.edu.bnuz.tpt

class TptCoType {
	String name
	static mapping={
		table	'tpt_co_type'
		name length:32
	}
    static constraints = {
		name	unique:true
    }
}
