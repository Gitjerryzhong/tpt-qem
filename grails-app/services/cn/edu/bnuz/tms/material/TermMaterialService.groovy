package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.material.Material;
import cn.edu.bnuz.tms.material.TermMaterial;

class TermMaterialService {
	def getMaterials(String departmentId) {
		def results = Material.executeQuery '''
SELECT m.id, m.name, c.name, m.type, m.form, m.description
FROM Material m
JOIN m.category c
WHERE m.department is null OR m.department.id = :departmentId
ORDER by c.displayOrder, m.name
''', [departmentId:departmentId]
		results.collect {[
			id:          it[0],
			name:        it[1],
			category:    it[2],
			type:        it[3],
			form:        it[4],
			description: it[5],
		]}
	}
	
	def get(String departmentId, Long termId) {
		def results = TermMaterial.executeQuery '''
SELECT tm.id, m.id 
FROM TermMaterial tm
JOIN tm.term t
JOIN tm.department d
JOIN tm.material m
WHERE t.id = :termId
AND d.id = :departmentId 
''', [termId:termId, departmentId:departmentId]
		return results.collect{[
			id: it[0],
			mid: it[1]
		]}
	}
	
	def create(String departmentId, Long materialId, Long termId) {
		TermMaterial tm = new TermMaterial(
			term : Term.load(termId),
			department: Department.load(departmentId),
			material: Material.load(materialId)
		)
		tm.save(failOnError:true)
	}
	
	
	def delete(Long id) {
		TermMaterial tm = TermMaterial.load(id)
		try {
			tm.delete(flush:true)
			return true
		} catch(Exception ex) {
			return false
		}
	}
	
	/**
	 * 获取指定分类的学期材料数量
	 * @param department 学院
	 * @param term 学期
	 * @param categoryId 分类ID
	 * @return 材料数量
	 */
	def getCountByCategory(Department department, Term term, Long categoryId) {
		def results = TermMaterial.executeQuery '''
SELECT count(*)
FROM TermMaterial termMaterial
JOIN termMaterial.material material
JOIN material.category category
WHERE termMaterial.term = :term
AND termMaterial.department = :department
AND category.id = :categoryId
''', [department:department, term:term, categoryId: categoryId]
		
		return results[0]
	}
}
