package cn.edu.bnuz.qem.project

class MajorType {
	String id
	String majorTypeName
	static mapping = {
		table	'qem_major_type'
		id 		generator: 'assigned', length: 4
		majorTypeName	length:50
	}
    static constraints = {
    }
}
