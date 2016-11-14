//= require lib/doT.min
//= require teach/model/arrangement
//= require_self

(function($, doT, Arrangement, undefined) {
$.widget("ui.freeArrangementList", {
	_create: function () {
		var arrangements = []
		  , map = {}
		  , template = doT.template($("#table-template").html())
		  ;
		if(this.options.students.length == 0) {
			this.element.append("<p>暂无数据</p>")
		} else {
			$.each(this.options.arrangements, function() {
				var arr = new Arrangement(this);
				arrangements.push(arr);
				map[arr.id] = arr;
			});
			
			$.each(this.options.students, function() {
				var arr = map[this.arrangementId]
				if(arr) {
					if(!arr.students) {
						arr.students = [];
					}
					arr.students.push(this);
				}
			});
			
			this.element.html(template({
				arrangements: arrangements
			}));
		}
	}
});
}(jQuery, doT, tms.teach.model.Arrangement));