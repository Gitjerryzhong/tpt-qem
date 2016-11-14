package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.system.TeacherSetting;

class TeacherService {

	void changeSetting(String userId, String name, String value) {
		Teacher teacher = Teacher.load(userId)
		TeacherSetting setting = TeacherSetting.findByTeacherAndName(teacher, name)
		if(setting == null) {
			setting = new TeacherSetting(teacher:teacher, name: name, value: value)
		} else {
			setting.value = value;
		}
		setting.save(failOnError:true);
	}

	String getSetting(String userId, String name) {
		TeacherSetting setting = TeacherSetting.findByTeacherAndName(Teacher.load(userId), name)
		return setting?.value
	}
}
