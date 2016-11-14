//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.materialList", {
		_create : function() {
			var that = this
			  , template = this.template = doT.template($("#row-template").html())
			  , $listBody = this.$listBody = $("#listBody")
			  ;	
			
			$.each(this.options.materials, function() {
				that._insertRow(this);
			});
			
			$("#btnCreate").click(function() {
				that.activeRow = null;
				$("#dialog").modal("show");
			});

			$(".table").on("click", "i.edit", function() {
				that.activeRow = $(this).parents("tr");
				$("#dialog").modal("show");
			});
			
			$(".table").on("click", "i.delete", function() {
				that.activeRow = $(this).parents("tr");
				$("#confirm").modal("show");
			});
			
			$("#dialog").on("show.bs.modal", function() {
				that._onDialogShow();
			});
			
			$("#btnSave").click(function() {
				that._saveItem();
			});
			
			$("#btnDelete").click(function() {
				that._deleteItem();
			})
		},
		
		_insertRow: function(item) {
			var $tr = $("<tr/>")
				.html(this.template(item))
				.data("item", item)
				.appendTo(this.$listBody);
			if(this.options.system && item.deptId != null ||
			  !this.options.system && item.deptId == null) {
				$("i", $tr).hide();
			}
		},
		
		_onDialogShow: function() {
			var item = this.activeItem = this.activeRow ? this.activeRow.data("item") : {
				name: "",
				category: 1,
				type: 0,
				form: 0,
				description: ""
			};
			$("#material-name").val(item.name);
			$("#material-category").val(item.categoryId);
			$("#material-type").val(item.type);
			$("#material-form").val(item.form);
			$("#material-description").val(item.description);
		},
		
		_saveItem: function() {
			var that = this
			  , item = {}
			  ;
			
			item.name = $("#material-name").val();
			item.categoryId = parseInt($("#material-category").val());
			item.categoryName = $("#material-category option[value='" + item.categoryId + "']").text();
			item.type = parseInt($("#material-type").val());
			item.form = parseInt($("#material-form").val());
			item.description = $("#material-description").val();
			
			$("#dialog").modal("hide");
			
			if(this.activeRow == null) {
				$.ajax({
    				type: "POST",
    				url: window.location.href,
    				data: JSON.stringify({
    					name: item.name,
    					mcid : item.categoryId,
    					type: item.type,
    					form: item.form,
    					description: item.description
    				}),
    				contentType: 'application/json',
        			dataType: 'json'
    			}).done(function(data) {
    				$.extend(item, data);
					that._insertRow(item);	
				}).fail(function() {
					alert("无法插入");
				});
			} else {
				$.ajax({
    				type: "PUT",
    				url: window.location.href + "/" + this.activeItem.id,
    				data: JSON.stringify({
    					name: item.name,
    					mcid : item.categoryId,
    					type: item.type,
    					form: item.form,
    					description: item.description
    				}),
    				contentType: 'application/json'
    			}). done(function(data) {
    				$.extend(that.activeItem, item)
    				that.activeRow.html(that.template(that.activeItem));
				}).fail(function() {
					alert("无法更新");
				});
			}
		},
		
		_deleteItem: function() {
			var $activeRow = this.activeRow
			  , id = $activeRow.data("item").id;
			$.ajax({
				type : "DELETE",
				url : window.location.href + "/" + id
			}).done(function() {
				$activeRow.remove();
			}).fail(function() {
				alert("无法删除");
			});
		}
	});
}(jQuery, doT));