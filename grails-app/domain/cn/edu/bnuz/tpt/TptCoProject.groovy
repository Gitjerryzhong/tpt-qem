package cn.edu.bnuz.tpt

import cn.edu.bnuz.qem.project.MajorType
/**
 * 合作项目（协议）
 * @author zhongqin
 *
 */
class TptCoProject {
	String name
//	int beginYear
	/**
	 * 有效年份：以起始年为基准
	 */
//	int effeYears 
//	String effeYearStr
	/**
	 * 当前是否有效
	 */
	boolean	effective
	
	/**
	 * 是否双学位，非双学位协议学生不能在线申请学位
	 */
	boolean	ifTowDegree

	/**
	 * 协议分类
	 */
	TptCoType	tptCoType
	
	static hasMany =[tptCoPrjItem:TptCoPrjItem]
	
	/**
	 * 所属项目
	 */
	TptCoCountry	tptCoCountry
	String memo
	static mapping={
		table	'tpt_co_project'
//		name 	length:100
		columns {
			beginYear defaultValue:2002
			effeYears defaultValue:0
			effective defaultValue:0
		}
	}
    static constraints = {
		name  unique:true
		memo	(maxSize:1500,nullable:true)
    }
}
