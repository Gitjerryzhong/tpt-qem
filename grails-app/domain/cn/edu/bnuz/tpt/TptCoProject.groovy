package cn.edu.bnuz.tpt

import cn.edu.bnuz.qem.project.MajorType
/**
 * ������Ŀ��Э�飩
 * @author zhongqin
 *
 */
class TptCoProject {
	String name
//	int beginYear
	/**
	 * ��Ч��ݣ�����ʼ��Ϊ��׼
	 */
//	int effeYears 
//	String effeYearStr
	/**
	 * ��ǰ�Ƿ���Ч
	 */
	boolean	effective
	
	/**
	 * �Ƿ�˫ѧλ����˫ѧλЭ��ѧ��������������ѧλ
	 */
	boolean	ifTowDegree

	/**
	 * Э�����
	 */
	TptCoType	tptCoType
	
	static hasMany =[tptCoPrjItem:TptCoPrjItem]
	
	/**
	 * ������Ŀ
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
