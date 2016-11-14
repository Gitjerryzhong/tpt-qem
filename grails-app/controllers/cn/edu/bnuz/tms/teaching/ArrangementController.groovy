package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Student

class ArrangementController {
	def arrangementService
	
	def index() {
		render 'should empty'
	}
		
	/**
	 * 根据教学安排id获取所有课程班学生
	 * @param id 教学安排id
	 * @return JSON格式学生列表
	 */
	def students(String id) {
		List<Student> students = arrangementService.getStudents(id)
		
		render(contentType: 'text/json') {
			array {
				students.each {
					student id: it.id,
						name: it.name,
						adminClass: it.adminClass.name,
						major: it.major.name
				}
			}
		}
	}
	
}
