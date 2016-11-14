package cn.edu.bnuz.tms.util

import java.lang.reflect.Modifier

import org.codehaus.groovy.reflection.ReflectionUtils

import cn.edu.bnuz.tms.system.Menu
import cn.edu.bnuz.tms.system.Role

class MenuBuilder {
	// 缩进，调试输出使用
	private int indent = 0

	// 对象栈，使用parent获取栈顶对象，表示当前对象的父对象
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
	 * 调用闭包。将当前结点压栈，作为下级结点的父结点，可通过getParent获得
	 * @param current 当前结点
	 * @param c 当前结点的闭包（下级结点）
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
	 * 获取父结点
	 * @return 父结点
	 */
	private getParent() {
		return stack.empty ? null : stack.last()
	}

	/**
	 * 加载外部文件
	 * @param fileName 文件名
	 */
	def load(String fileName, Binding binding = null) {
		return getShell(binding).evaluate(new File(fileName))
	}

	/**
	 * 从Reader中加载
	 * @param reader Reader对象
	 */
	def loadFromClassPath(String fileName, Binding binding = null) {
		def clazz = ReflectionUtils.getCallingClass(2)
		def input = clazz.getResourceAsStream(fileName)
		Reader reader = new InputStreamReader(input)
		return getShell(binding).evaluate(reader)
	}

	/**
	 * 获取GroovyShell
	 * @return GroovyShell对象
	 */
	private GroovyShell getShell(Binding inBinding) {
		// 获取所有角色
		def roles = Role.fields.findAll {
			def mod = it.modifiers
			Modifier.isFinal(mod) && Modifier.isPublic(mod) && Modifier.isStatic(mod)
		}.collectEntries {
			[(it.name): it.get(null)]
		} 
		
		// 绑定顶层函数
		Binding bindings = new Binding([
			menus: this.&menus
		] << roles)

		// 合并绑定
		if(inBinding)
			bindings.variables.putAll(inBinding.variables)

		return new GroovyShell(this.class.classLoader, bindings)
	}
}
