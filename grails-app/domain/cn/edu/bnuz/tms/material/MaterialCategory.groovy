package cn.edu.bnuz.tms.material

class MaterialCategory {
	String name
	/**
	 * �������͡�
	 * 0: �ǿγ̲���
	 * 1���γ̲���
	 */
	int type
	
	/**
	 * ��ʾ˳��
	 */
	int displayOrder
	
	static mapping = {
		table 		'edu_material_category'
		name 		length:50
	}
}
