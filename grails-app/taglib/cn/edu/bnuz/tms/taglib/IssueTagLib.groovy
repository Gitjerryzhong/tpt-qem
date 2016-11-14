package cn.edu.bnuz.tms.taglib

class IssueTagLib {
	static namespace = 't'
	private static infos = [
		[], 
		[icon: "minus-sign",      color: "danger"],
		[icon: "circle-arrow-up", color: "info"],
		[icon: "plus-sign",       color: "success"]
	]
	def issueType = {attrs, body ->
		def type = attrs["type"]
		def closed = attrs["closed"]
		if(type < infos.size()) {
			def info = infos[type]
			out << '<span class="glyphicon glyphicon-' << info.icon
			if(closed) {
				out << ' closed'
			} else {
				out << ' text-' << info.color
			}
			out << '" title="' << message(code: "tms.issue.type.$type") << '"></span>'
		}
	}
}
