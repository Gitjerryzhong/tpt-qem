package cn.edu.bnuz.tms.util

import java.lang.reflect.Modifier

import org.codehaus.groovy.reflection.ReflectionUtils

import cn.edu.bnuz.tms.system.Menu
import cn.edu.bnuz.tms.system.Role

class MenuBuilder {
	// �������������ʹ��
	private int indent = 0

	// ����ջ��ʹ��parent��ȡջ�����󣬱�ʾ��ǰ����ĸ�����
	private List stack = []
	
	def menus(Closure c) {
		def ms = []
		call(ms, c)
		return ms;
	}
	
	def invokeMethod(String name, args) {
		Menu menu = new Menu(label: name, level:indent)
		def a0, a1, a2, a3
		switch(args.length) {
		case 1: 
			a0 = args[0]
			switch(a0) {
			case String: // label "url"
				menu.url = a0
				break;
			case Closure: // label {...}
				call(menu, a0)
				break;
			}
			break;
		case 3:
			a0 = args[0]
			a1 = args[1]
			a2 = args[2]
			assert a0 instanceof String 
			assert a1 instanceof String
			menu.module= a0
			menu.url = a1
			switch(a2) {
			case List: // label "module", "url", [roles] 
				menu.roles = a2
				break;
			case Closure:  // label "module", "url", {...}
				call(menu, a2)
				break;
			}
			break;
		case 4: // label "module", "url", [roles], {...}
			a0 = args[0]
			a1 = args[1]
			a2 = args[2]
			a3 = args[3]
			assert a0 instanceof String
			assert a1 instanceof String
			assert a2 instanceof List
			assert a3 instanceof Closure
			menu.module= a0
			menu.url = a1
			menu.roles = a2
			call(menu, a3)
			break;
		}
		if(parent instanceof Menu) {
			parent.submenus << menu
		} else {
			parent << menu
		}
	}


	/**
	 * ���ñհ�������ǰ���ѹջ����Ϊ�¼����ĸ���㣬��ͨ��getParent���
	 * @param current ��ǰ���
	 * @param c ��ǰ���ıհ����¼���㣩
	 */
	private void call(current, Closure c) {
		stack.push(current)
		indent++
		c.delegate = this
		c.call()
		indent--
		stack.pop()
	}


	/**
	 * ��ȡ�����
	 * @return �����
	 */
	private getParent() {
		return stack.empty ? null : stack.last()
	}

	/**
	 * �����ⲿ�ļ�
	 * @param fileName �ļ���
	 */
	def load(String fileName, Binding binding = null) {
		return getShell(binding).evaluate(new File(fileName))
	}

	/**
	 * ��Reader�м���
	 * @param reader Reader����
	 */
	def loadFromClassPath(String fileName, Binding binding = null) {
		def clazz = ReflectionUtils.getCallingClass(2)
		def input = clazz.getResourceAsStream(fileName)
		Reader reader = new InputStreamReader(input)
		return getShell(binding).evaluate(reader)
	}

	/**
	 * ��ȡGroovyShell
	 * @return GroovyShell����
	 */
	private GroovyShell getShell(Binding inBinding) {
		// ��ȡ���н�ɫ
		def roles = Role.fields.findAll {
			def mod = it.modifiers
			Modifier.isFinal(mod) && Modifier.isPublic(mod) && Modifier.isStatic(mod)
		}.collectEntries {
			[(it.name): it.get(null)]
		} 
		
		// �󶨶��㺯��
		Binding bindings = new Binding([
			menus: this.&menus
		] << roles)

		// �ϲ���
		if(inBinding)
			bindings.variables.putAll(inBinding.variables)

		return new GroovyShell(this.class.classLoader, bindings)
	}
}
