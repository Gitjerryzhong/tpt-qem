package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.teaching.MaterialCommand
import cn.edu.bnuz.tms.material.Material;
import cn.edu.bnuz.tms.material.MaterialCategory;

class MaterialService {
	def getCategories() {
		def results = MaterialCategory.executeQuery '''
SELECT c.id, c.name
FROM MaterialCategory c
ORDER BY c.displayOrder
'''		
		return results.collect {[
			id: it[0],
			name: it[1]
		]}
	}
	
	def getAll() {
		def results = Material.executeQuery '''
SELECT m.id, m.name, c.id, c.name, m.type, m.form, m.description, d.id, d.name
FROM Material m
JOIN m.category c
LEFT JOIN m.department d
ORDER by m.department, c.displayOrder, m.id
'''		
		results.collect {[
			id:          it[0],
			name:        it[1],
			categoryId:  it[2],
			categoryName:it[3],
			type:        it[4],
			form:        it[5],
			description: it[6],
			deptId:      it[7],
			deptName:    it[8],
		]}
	}
	
	def getAll(String departmentId) {
		def results = Material.executeQuery '''
SELECT m.id, m.name, c.id, c.name, m.type, m.form, m.description, d.id, d.name
FROM Material m
JOIN m.category c
LEFT JOIN m.department d
WHERE m.department is null OR m.department.id = :departmentId
ORDER by m.department, c.displayOrder, m.id
''', [departmentId:departmentId]
		results.collect {[
			id:          it[0],
			name:        it[1],
			categoryId:  it[2],
			categoryName:it[3],
			type:        it[4],
			form:        it[5],
			description: it[6],
			deptId:      it[7],
			deptName:    it[8],
		]}
	}
	
	def create(String departmentId, MaterialCommand materialCommand) {
		MaterialCategory category = MaterialCategory.get(materialCommand.mcid)
		Material material = new Material()
		materialCommand.copyTo(material)
		material.category = category
		if(category.type == 0) { // 非课程教材
			material.type = 0
		}
		material.department = departmentId ? Department.load(departmentId) : null
		material.save(failOnError:true)
		
		return material
	}
	
	boolean update(Long id, MaterialCommand materialCommand) {
		MaterialCategory category = MaterialCategory.get(materialCommand.mcid)
		Material material = Material.get(id)
		materialCommand.copyTo(material)
		material.category = category
		if(category.type == 0) { // 非课程教材
			material.type = 0
		}
		material.save(failOnError:true)
		return true;
	}
	
	boolean delete(Long id) {
		Material material = Material.load(id)
		material.delete();
		return true;
	}
}
