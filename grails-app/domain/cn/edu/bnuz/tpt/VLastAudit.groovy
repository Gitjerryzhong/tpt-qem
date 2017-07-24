package cn.edu.bnuz.tpt

class VLastAudit {
	Long formId
	Date maxdate
	static mapping = {
		table 		'v_last_audit'
	}
    static constraints = {
    }
}
