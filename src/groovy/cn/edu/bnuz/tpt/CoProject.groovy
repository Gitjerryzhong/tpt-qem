package cn.edu.bnuz.tpt

class CoProject {
	int id
	String name
	int coTypeId
	int coCountryId
	int beginYear
	boolean actived
	boolean ifTowDegree
	int effeYears
	String effeYearStr
	String memo
	
	String toString(){
		return "name:${name},coTypeId:${coTypeId},beginYear:${beginYear},actived:${actived},ifTowDegree:${ifTowDegree},effeYears:${effeYears},effeYearStr:${effeYearStr},memo:${memo}"
	}
	
}
