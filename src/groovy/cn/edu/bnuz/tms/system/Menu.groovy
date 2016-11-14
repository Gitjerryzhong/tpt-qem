package cn.edu.bnuz.tms.system

class Menu implements Serializable {
	String label
	String module
	String url
	List<String> roles
	List<Menu> submenus = []
	int level = 0;
	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("\n${'\t'*level}$label, $module, $url, $roles")
		submenus.each {
			sb.append("$it")
		}
		return sb.toString()
	}
}
