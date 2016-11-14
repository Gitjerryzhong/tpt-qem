package cn.edu.bnuz.tms.material

class MaterialCategory {
	String name
	/**
	 * 材料类型。
	 * 0: 非课程材料
	 * 1：课程材料
	 */
	int type
	
	/**
	 * 显示顺序。
	 */
	int displayOrder
	
	static mapping = {
		table 		'edu_material_category'
		name 		length:50
	}
}
