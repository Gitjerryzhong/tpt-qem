package cn.edu.bnuz.tms.taglib

class RequireTagLib {
	static namespace = 't'
	
	def require = {attrs, body ->
		def page = attrs['page'] ?: "app/$controllerName/$actionName";
		out << """<script type="text/javascript" src="${resource(dir: 'js', file: 'require-config.js')}" data-page="$page"></script>
<script type="text/javascript" data-main="page/main.js" src="${resource(dir: 'js/lib', file: 'require.js')}"></script>"""
	}
}
