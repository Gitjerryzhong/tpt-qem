package cn.edu.bnuz.tpt

class TptCoCountry {

    String name
	String memo
	static mapping={
		table	'tpt_co_country'
		name length:32
	}
    static constraints = {
		name	unique:true
		memo	nullable:true
    }
}
