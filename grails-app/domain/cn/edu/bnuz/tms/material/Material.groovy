package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Department

/**
 * 教学材料
 * @author 杨林
 * @since 0.4
 */
class Material {
	/**
	 * 学院。如果为空，表示校级材料。不为空，表示院级材料。 
	 */
	Department department
	
	/**
	 * 教学材料名称
	 */
	String name
	
	/**
	 * 分类
	 */
	MaterialCategory category
	
	/**
	 * 说明
	 */
	String description
	
	/**
	 * 类型。
	 * 0.按学期
	 * 1.按课程班
	 * 2.按课程
	 */
	int type
	
	/**
	 * 提交形式。
	 * 1.纸质版
	 * 2.电子版（线下提交）
	 * 3.电子版（其它系统）
	 * 4.电子版（在线提交）暂不支持
	 */
	int form
	
	static mapping = {
		table 		'edu_material'
		name 		length:50
		category 	length:50
	}
	
	static constraints = {
		department		nullable:true
		description 	nullable:true
	}
}
