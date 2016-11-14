package cn.edu.bnuz.tpt

class TptPaperMtlRgn {
	/**
	 * 本科=1
	 */
	public static final String TYPE_UNDERGRADUATE = "1"
	/**
	 * 硕士=2
	 */
	public static final String TYPE_MASTER = "2"
	/**
	 * 课程论文=3
	 */
	public static final String TYPE_COURSE = "3"
	/**
	 * 学生学号
	 */
	String studentID
	/**
	 * 学生姓名
	 */
	String name
	/**
	 * 类型：本科还是硕士或是课程论文
	 */
	String type
	/**
	 * 国内专业
	 */
	String major
	/**
	 * 合作院校名称、本科合作院校名称
	 */
	String foreignCollege
	/**
	 * 合作院校就读专业、本科合作院校就读专业
	 */
	String f_c_major
	/**
	 * 合作院校论文(课程)名称、国外硕士毕业院校
	 */
	String masterCollege
	/**
	 * 论文(课程)成绩
	 */
	String master_result
	/**
	 * 中文论文题目
	 */
	String paperName
	/**
	 * 英文论文题目
	 */
	String paperName_en
	/**
	 * 中文论文摘要
	 */
	String abstrct
	/**
	 * 英文论文摘要
	 */
	String abstrct_en
	String bn
	static mapping = {
		table 			'tpt_paper_mtl_rgn'
		studentID		length: 10
		name			length: 50
		major 			length: 50
		foreignCollege	length: 255
		f_c_major		length: 50
		masterCollege	length: 255
		master_result	length: 10
		paperName		length: 100
		paperName_en	length: 500
		abstrct			length: 500
		bn				length: 10
		type			length: 10
	}
    static constraints = {
		abstrct_en(maxSize:3000)
		masterCollege	nullable:true
		abstrct			nullable:true
		abstrct_en		nullable:true
    }
}
