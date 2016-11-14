//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.teachingMaterialList", {
		_create : function() {
			var that = this
			  , optionTemplate = doT.template($("#option-template").html())
			  , $terms = this.$terms = $("#terms")
			  ;	
			
			this.template = doT.template($("#rows-template").html())
			this.$listBody = $("#listBody")
			
			$terms.html(optionTemplate(this.options.terms))
			.val(this.options.term)
			.on("change", function() {
				that._loadData($terms.val());
			});
			
			this._loadData($terms.val());
		},
		
		_loadData: function(term) {
			var that = this;
			$.ajax({
				type: "GET",
				url: window.location.href + "?term=" + term,
				contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillData(term, data);
			});
		},
		
		_fillData: function(term, data) {
			data.teachers.sort(function(a, b) {
				return a.name.localeCompare(b.name);
			});
			var $listBody = this.$listBody.html(this.template(data.teachers));
			var $detail = this.$detail = $.ui.teachingMaterialDetail({
				categories:data.categories
			});
			
			$("tr", $listBody).click(function(e) {
				e.preventDefault();
				$("tr").removeClass("selected");
				$(this).addClass("selected");
				$detail.option({"activeRow": $(this), term:term});
				$detail.getDetail();	
			});
			
			this._hoverable($("tr", $listBody));
		}
	});
	
	$.widget("ui.teachingMaterialDetail", {
		defaultElement: "<tr>",
		
		_create: function() {
			var that = this
			  , cellbarTemplate = doT.template($("#cell-toolbar-template").html())
			  , $toolbar = $(cellbarTemplate())
			  ;
			
			this.$td = $("<td class='detail'/>").appendTo(this.element);
			this.detailTemplate = doT.template($("#detail-template").html());
			
			$("button", $toolbar).on("click", function() {
				that._onCellClick($(this));
			});
			
			this.$td.on("mouseenter", "td.cell", function() {
				var $cell = $(this)
				  , status = $cell.data("status");
				$cell.find("span").hide();
				$cell.append($toolbar);
				$("button", $toolbar).removeClass("active");
				$("#btn" + status).addClass("active");
			}).on("mouseleave", "td.cell", function() {
				var $cell = $(this);
				$cell.find("span").show();
				$toolbar.detach();
			});
		},
		
		_onCellClick: function($button) {
			var that = this
			  , newStatus = $button.data("status")
			  , $cell = $button.parents("td.cell")
			  , orgStatus = $cell.data("status") 
			  ;
			if(newStatus == orgStatus) {
				return;
			}
			
			var promise;
			if(orgStatus == 0) { 
				// insert
				promise = $.ajax({
    				type: "POST",
    				url: window.location.href,
    				data: {
    					tmid: $cell.data("tmid"),
    					target: $cell.data("target"),
    					teacherId: that.teacherId,
    					status: newStatus
        			},
        			dataType: 'json'
    			}).done(function(data) {
    				$cell.data("id", data.id);
    				that._changeCount(newStatus, true);
    			});
			} else if(newStatus == 0){ 
				// delete
				promise = $.ajax({
					type : "DELETE",
					url : window.location.href + "/" + $cell.data("id")
				}).done(function(data) {
					that._changeCount(orgStatus, false);
    			});
			} else { 
				// update
				promise = $.ajax({
					type: "POST",
					url: window.location.href + "/" + $cell.data("id"),
					data: {status:newStatus}
				}).done(function(data) {
					that._changeCount(newStatus, true);
					that._changeCount(orgStatus, false);
    			});
			}
			
			promise.done(function() {
				$cell.find("span")
			         .text(that.STATUS[newStatus].text)
			         .removeClass(that.STATUS[orgStatus].color)
			         .addClass(that.STATUS[newStatus].color);
				$cell.data("status", newStatus);
			});
		},
		
		_changeCount:function(status, inc) {
			var $row = this.$activeRow
			  , total = parseInt($row.find("td.total").text())
			  , $td = $row.find("td.s" + status)
			  ,	count = parseInt($td.text())
			  ;
			if(inc) {
				++count;
			} else {
				--count;
			}
			$td.text(count);
			$row.find(".ratio .s" + status).width((count / total * 100) + "%");
			
			var commit = parseInt($row.find("td.s1").text())
			var free = parseInt($row.find("td.s2").text())
			$row.find(".percent").text(Math.round(commit / (total - free) * 100) + "%");
		},
		
		_setOption:function(key, value) {
			var that = this;
			if(key == "activeRow") {
				this.$activeRow = value;
			} 			
			this._superApply(arguments);
		},
		
		attach: function() {
			this.element.insertAfter(this.$activeRow);
			this.element.slideDown("fast");
		},
		
		detach: function(animate) {
			var $el = this.element;
			if(animate) {
				$el.slideUp('fast',function() {
					$el.detach();
				});
			} else {
				$el.detach();
			}
		},

		getDetail: function() {
			var that = this
			  , teacherId = this.$activeRow.data("id");
			
			this.detach(true);
			
			$.ajax({
				type: "GET",
				url: window.location.href + "/" + teacherId + "?term=" + this.options.term,
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillDetail(teacherId, data);
			});
		},
		
		_fillDetail: function(teacherId, data) {
			var that = this;
			if(teacherId != this.$activeRow.data("id")) {
				return;
			}
			this.teacherId = teacherId;
			
			this.element.stop(true, true);				
			
			var colSpan = this.$activeRow.children().length
			this.$td.empty().prop("colspan", colSpan);
			
			var classCount = 0;
			for(var i = 0; i < data.courses.length; i++) {
				classCount += data.courses[i].classes.length;
			}
				
			this.$td.html(this.detailTemplate({
				categories: this.options.categories,
				courses: data.courses,
				commits: data.commits,
				classCount: classCount,
				termId: this.options.term,
				STATUS: that.STATUS,
				find: that._findCommit,
				thesis: this.$activeRow.data("thesis"),
				internship: this.$activeRow.data("internship")
			}));
			
			this.attach();
		},

		_findCommit: function(commits, tmid, target) {
			for(var i = 0; i < commits.length; i++) {
				var commit = commits[i]; 
				if(commit.tmid == tmid && commit.target == target) {
					return commit;
				}
			}
			return null;
		},
		
		STATUS: [
		    {text:"未交", color:"label-danger"},
		    {text:"已交", color:"label-success"},
		    {text:"免交", color:"label-default"}
		]
	});
}(jQuery, doT));

