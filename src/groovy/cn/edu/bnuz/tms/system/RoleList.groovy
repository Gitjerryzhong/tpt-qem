package cn.edu.bnuz.tms.system

class RoleList {
	private List<String> roles
	
	RoleList(List<String> roles) {
		this.roles = roles
	}
	
	void add(String role) {
		if(!roles.contains(role)) {
			roles.add(role)
		}
	}
	
	void remove(String role) {
		roles.remove(role)
	}
	
	@Override
	public String toString() {
		return roles.join(",");
	}
}
