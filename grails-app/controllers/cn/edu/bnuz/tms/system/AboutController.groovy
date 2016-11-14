package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.system.WhatIsNew;

class AboutController {
	def index() {}
	def news() {
		def items = WhatIsNew.listOrderByDate(order:'desc')
		return [items: items]
	}
}
