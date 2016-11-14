package cn.edu.bnuz.tpt

import cn.edu.bnuz.qem.project.MajorType;
import cn.edu.bnuz.tpt.teaching.Subject

class TptCoPrjItem {
//	Major major
	/**
	 * У��רҵ
	 */
	Subject major
	int beginYear
	/**
	 * ��Ч��ݣ�����ʼ��Ϊ��׼
	 */
//	static hasMany =[tptProStudent:TptProStudent]
	int effeYears
	String effeYearStr
	String collegeNameEn
	String collegeNameCn
	String coDegreeOrMajor
	String memo
	static belongsTo = [
		project : TptCoProject
	]
	
	static mapping={
		table	'tpt_co_prjitem'
		columns {
			beginYear defaultValue:2002
			effeYears defaultValue:0
		}
	}
    static constraints = {
		major 	unique:'project'
		memo	(maxSize:1500, nullable:true)
		coDegreeOrMajor nullable:true
    }
}
