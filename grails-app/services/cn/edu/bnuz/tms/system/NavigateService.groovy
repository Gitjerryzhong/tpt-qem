package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.system.Menu;
import cn.edu.bnuz.tms.util.MenuBuilder

class NavigateService  {
	def servletContext  
	private List<Menu> _menus 	
	private long _lastModified

	String path

	private List<Menu> loadMenus() {
		path = path ?: servletContext.getRealPath("/WEB-INF/system.mnu")
		File file = new File(path)
		long lastModified = file.lastModified()
		if(!_menus || lastModified > _lastModified) {
			MenuBuilder builder = new MenuBuilder()
			_menus =  builder.load(path)
			_lastModified = lastModified
		}
		return _menus;
	}

	List<Menu> getMenus(List<String> roles) {
		List<Menu> menus = loadMenus();

		// 根据用户角色进行过滤
		def filter
		filter = { Menu menu, List<Menu> collector ->
			if(!menu.roles || menu.roles?.intersect(roles)) {
				Menu clone = new Menu(label:menu.label, url:menu.url, module:menu.module);
				collector << clone
				menu.submenus.each {
					filter(it, clone.submenus)
				}
			}
		}

		def result = []
		menus.each { filter(it, result) }

		// 菜单压缩：如果菜单项只包含一项子菜单，则在替代上级菜单项
		def reduce
		reduce = {Menu menu ->
			if(menu.level != 0 && menu.submenus.size() == 1) {
				Menu sub = menu.submenus[0]
				menu.label = sub.label
				menu.url = sub.url
				menu.module = sub.module
				menu.submenus = sub.submenus
			}

			menu.submenus.each { reduce(it) }
		}

		result.each { reduce(it) }

		return result
	}
}
