//= require lib/doT.min
//= require_self

(function($, undefined) {
$.widget("ui.tdCheckers", {
	departmentTeachers: {},
	
	_create: function() {
		var that = this;
		this.element.find("tbody tr").each(function() {
			var $tr = $(this)
			  , departmentId = $tr.data("department-id")
			  ;
			$tr.find("td").each(function() {
				var $td = $(this)
				  , $span = $td.find("span")
				  , typeId = $td.data("type-id")
				  , checker = that.findChecker(departmentId, typeId)
				  ;
				
				if(checker) {
					$td.data("checker-id", checker.id).find("span").text(checker.name);
				} else {
					$td.find("span").text("<空>");
				}
			});
		});
		
		this.element.on("click", "td.booking-type span", function() {
			var $span = $(this)
			  , $select = $span.siblings("select")
			  , departmentId = $span.parents("tr").data("department-id")
			  , checkerId = $span.parent("td").data("checker-id")
			  ;
			
			$(".table select").hide();
			$(".table span").show();
			
			if($("option", $select).length == 0) {
				if(that.departmentTeachers[departmentId] == null) {
					that.loadTeachers(departmentId).done(function(data) {
						that.departmentTeachers[departmentId] = data;
						that.fillSelect($select, that.departmentTeachers[departmentId], checkerId);
						$select.val(checkerId);
						$span.hide();
						$select.show().focus();
					});							
				} else {
					that.fillSelect($select, that.departmentTeachers[departmentId], checkerId);
					$select.val(checkerId);
					$span.hide();
					$select.show().focus();
				}
			} else {
				$span.hide();
				$select.show().focus();
			}		
		}).on("blur", "td.booking-type select", function() {
			var $select = $(this)
			  , $span = $select.siblings("span")
			  , $td = $select.parent("td")
			  , departmentId = $select.parents("tr").data("department-id")
			  , prevCheckerId = $td.data("checker-id")
			  , currCheckerId = $select.val()
			  , typeId = $td.data("type-id")
			  ;
			if(prevCheckerId != currCheckerId) {
				that.saveChecker(departmentId, typeId, currCheckerId).done(function(data) {
					var name = $select.find("option:selected").text()
					$span.text(name != "" ? name:"<空>").show();
					$td.data("checker-id", currCheckerId)
					$select.hide();
				});			
			} else {
				$span.show();
				$select.hide();
			}
		});
	},
	
	findChecker: function(departmentId, typeId) {
		for(var i = 0; i < this.options.auths.length; i++) {
			var auth = this.options.auths[i];
			if(auth.departmentId == departmentId && auth.typeId == typeId) {
				return {id: auth.checkerId, name: auth.checkerName}
			}
		}
		return null;
	},
	
	fillSelect: function($select, teachers) {
		$select.append("<option></option>");
		$.each(teachers, function(index, teacher) {
			$select.append("<option value='" + teacher.id + "'>" + teacher.name + "</option>");
		});	
	},
	
	loadTeachers: function(departmentId) {
		return $.ajax({
			type: "GET",
			url: "/tms/admin/departmentTeachers",
			data: {id: departmentId},
			cache: false
		});
	},
	
	saveChecker: function(departmentId, typeId, checkerId) {
		return $.ajax({
			type: "POST",
			url: "./checker",
			data: {
				departmentId: departmentId,
				typeId: typeId, 
				checkerId: checkerId
			}
		});
	}
});
}(window.jQuery));