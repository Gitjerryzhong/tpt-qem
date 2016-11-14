//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
$.widget("ui.adCheckers", {
	_create: function() {
		var that = this;
		this.rowsTemplate = doT.template($("#rows-template").html());
		this.rowTemplate = doT.template($("#row-template").html());
		this.$tbody = this.element.find("tbody")
		
		
		
		this.element.on("click", ".add-item", function() {
			that.showDialog();
		}).on("click", ".remove-item", function() {
			if(confirm("确定要删除？")) {
				var $row = $(this).parents("tr")
				  , item = {
						departmentId: $row.data("department"),
						typeId: $row.data("type")
					};
				that.post(item).done(function(data) {
					that.updateRows(data);
				});;
			}
		});
		
		this.$dialog = $("#dialog").addAuthDialog({
			change: function(e, item) {
				that.post(item).done(function(data) {
					that.updateRows(data);
				});;
			}
		});
		
		this.updateRows(this.options.auths);
	},
	
	updateRows: function(auths) {
		this.$tbody.html(this.rowsTemplate({
			auths: auths
		}));
	},
	
	showDialog: function() {
		this.$dialog.modal("show");
	},
	
	post: function(item) {
		return $.ajax({
			type: "POST",
			url: "./checker",
			data: {
				departmentId: item.departmentId,
				typeId: item.typeId, 
				checkerId: item.checkerId
			}
		});		
	}
});

$.widget("ui.addAuthDialog", {
	_create: function() {
		var that = this;
		this.template = doT.template($("#checkers-template").html())
		this.$department = this.element.find("select[name='department']");
		this.$bookingType = this.element.find("select[name='bookingType']");
		this.$checker = this.element.find("select[name='checker']");
		
		this.$department.change(function() {
			var deptId = that.$department.find("option:selected").val();
			that.loadTeacher(deptId);
		});
		
		this.element.on("click", "button.ok", function() {
			that._trigger("change", null, {
				departmentId: that.$department.find("option:selected").val(),
				departmentName: that.$department.find("option:selected").text(),
				typeId: that.$bookingType.find("option:selected").val(),
				typeName: that.$bookingType.find("option:selected").text(),
				checkerId: that.$checker.find("option:selected").val(),
				checkerName: that.$checker.find("option:selected").text(),
			});
			that.element.modal("hide");
		});
		
		this.loadTeacher(this.$department.first("option").val());
	},
	
	loadTeacher: function(deptId) {
		var that = this;
		$.ajax({
			type: "GET",
			url: "/tms/admin/departmentTeachers/" + deptId,
			cache: false
		}).done(function(data) {
			that.$checker.html(that.template(data));
		});				
	}
});
}(window.jQuery, doT));