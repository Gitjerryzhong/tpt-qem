package cn.edu.bnuz.tpt

class CoProjectItem {
	int id
	int projectId
	String collegeNameEn
	String collegeNameCn
	String departmentName
	String majorsId
	String coDegreeOrMajor
	int beginYear
	int effeYears
	String effeYearStr
	String memo
	
	String toString(){
		return "collegeNameEn:${collegeNameEn},collegeNameCn:${collegeNameCn},majorsId:${majorsId},beginYear:${beginYear},effeYears:${effeYears},effeYearStr:${effeYearStr},memo:${memo}"
	}
}
