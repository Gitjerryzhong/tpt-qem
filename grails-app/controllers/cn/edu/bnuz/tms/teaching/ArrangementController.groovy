package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Student

class ArrangementController {
	def arrangementService
	
	def index() {
		render 'should empty'
	}
		
	/**
	 * ���ݽ�ѧ����id��ȡ���пγ̰�ѧ��
	 * @param id ��ѧ����id
	 * @return JSON��ʽѧ���б�
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
