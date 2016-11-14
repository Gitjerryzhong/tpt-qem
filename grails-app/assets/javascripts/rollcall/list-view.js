//= require lib/doT.min
//= require model
//= require_self

(function($, doT, Model, undefined) {
/* 列表视图
 * ======= */
$.widget("ui.listView", {
	_create: function() {
		this.viewModel = this.options.viewModel
		this.$detail = $(".detail-pane", this.element);
		this.$list = $(".list-pane", this.element);
		if( $("html").hasClass("ie8") ) {
			this._delay(this.init, 0);
		} else {
			this.init();
		}
	},
	
	init: function() {
		var that = this;
		this.$detail.detailPane();
		this.$list.listPane({
		    viewModel: this.viewModel, 
		    activeChanged: function(e, id) {
		    	var student = that.viewModel.getStudentById(id);
		    	that.$detail.detailPane({student: student});
		    }
	    });
      	this.element.on("focus",function(event) {
  			that.$list.focus();	      			
      	});
      	this.$list.focus();
	}
});

$.widget("ui.listPane", {
	options: {
		activeChanged: null
	},
	
	_create: function() {
		var that = this
		  , template = doT.template($("#list-view-template").html());
		
		this.viewModel = this.options.viewModel;
		
		// 使用模板创建视图
		this.$list = $(template({
			students: this.viewModel.students
		})); 
		this.element.append(this.$list);
		
		// 注册监听器
		this.element.on("click", function(event){
			var $li = $(event.target).closest("li");
			if($li.length) {
				that.activate($li, true);
			}
		});
		this.element.on("keydown", function(event) {
			$.each(that.OPERATIONS, function(key, value) {
      			if(value == event.keyCode) {
	      			event.preventDefault();
	      			that[key]();
	      		}
      		});
      	});
		this.element.on("focus", function() {
      		if(that.active && that.active.length) {
      			that.activate(that.active, false);
      		}
      	});
		
		// 当学生状态变化时调用
		$(this.viewModel).on({
			studentSaved: function(e, student) {
				that.stateChanged(student);
			},
			
			studentSaveError: function(e, student) {
				that.findItem(student).removeClass("wait");
			},
			
			visible: function(e, student) {
				that.visibleChanged(student);
			}
		});
		// 激活第一个
      	this.activate(this.$list.find("li:not(.hide):first"), false);
	},
			
	stateChanged: function(student) {
		var $li = this.findItem(student);
		$li.removeClass("wait");
		
		// 更新统计
		$li.find(".statis").text(student.statis.join(" "));
		
		// 更新标签 
      	$.each(Model.states, function(key, state) {
      		var $label = $li.find(".label-" + key);
  			if(student.hasState(key)) {
  				if($label.length == 0) {
  					$li.append($("<label class='label'/>").addClass('label-' + key).text(state.text));
  				}
  			} else {
  				$label.remove();
  			}
  		});
	},
	
	visibleChanged: function(student) {
		var $li = this.findItem(student);
		if(student.visible) {
			$li.show();
   		} else {
   			$li.hide();
   		}
	},
	
	findItem: function(student) {
		return this.element.find("li[data-id='" + student.id + "']")
	},
    
    toggle: function(type) {
    	if(this.active && !this.active.hasClass("wait")) {
        	var student = this.viewModel.getStudentById(this.active.data('id'));
        	
        	if(student.isFreeListen() || student.isCanceledExam()) {
            	// doing nothing
        	} else if(student.isLeave()) { 
        		if(!type) { // only for enter key
        			window.open("../../leaves/" + student.leaveRequest.id, "_blank")
        		}
        	} else {
        		this.active.addClass("wait");
        		if(type) {
        			student.toggle(type);
        		} else {
        			if(student.rollcall){
        				$.each(Model.states, function(key, state) {
                			if(student.rollcall == state.value ||
                			   student.rollcall == Model.LATE_EARLY && state.value == Model.EARLY) {
                				student.toggle(key);
                				return false
                			}
                		})
            		} else {
            			student.toggle('absent');
            		}
        		}
        	}
    	}
    },
    
	toggleAbsend: function() {
		this.toggle("absent");
	},
	
	toggleLate: function() {
		this.toggle("late");
	},
	
	toggleEarly: function() {
		this.toggle("early");
	},
	
	toggleAttend: function() {
		this.toggle("attend");
	},
    
    activeStudent : function() {
    	return this.active && this.active.data('student'); 
    },

	activate : function(item, click) {
		var $list = this.$list;
		this.deactivate();				
		this.active = item.addClass("active");
		
		if (this.hasScroll()) {
			var itemCount = $list.children(":visible").length
			  , itemIndex = $list.children(":visible").index(item)
			  , listHeight = $list.height()
			  , itemHeight = item.height()
			  , favorPosition = Math.floor(listHeight/ itemHeight / 2)
			  , favorHeight = favorPosition * itemHeight
			  , borderWidth = ($list.outerHeight() - $list.innerHeight()) / 2
			  , offset = item.offset().top - $list.offset().top - borderWidth
			  , scroll = $list.scrollTop()
			   ;
			
			if (!click && itemIndex > favorPosition && offset < favorHeight) {
				$list.scrollTop(scroll + offset - favorHeight);
			} else if (offset < 0) {
				$list.scrollTop(scroll + offset);
			} else if (!click && itemIndex + favorPosition < itemCount
					&& offset > favorHeight) {
				$list.scrollTop(scroll + offset - favorHeight);
			} else if (offset + itemHeight >= listHeight) {
				$list.scrollTop(scroll + offset - listHeight + itemHeight);
			}
		}
		
		this._trigger("activeChanged", null, this.active.data('id'));
	},

	deactivate : function() {
		if (!this.active) {
			return;
		}

		this.active.removeClass("active");
		this.active = null;
	},

	next : function() {
		this.move("next", ":first");
	},

	previous : function() {
		this.move("prev", ":last");
	},

	first : function() {
		return this.active && !this.active.prevAll().filter(":visible").length;
	},

	last : function() {
		return this.active && !this.active.nextAll().filter(":visible").length;
	},
	
	_child : function(selector) {
		return this.$list.children(":visible").filter(selector).eq(0);
	},

	move : function(direction, edge) {
		if (!this.active) {
			this.activate(this._child(edge));
			return;
		}
		var next = this.active[direction + "All"]().filter(":visible").eq(0);
		if (next.length) {
			this.activate(next);
		} else {
			this.activate(this._child(edge));
		}
	},

	nextPage : function() {
		if (this.hasScroll()) {
			if (!this.active || this.last()) {
				this.activate(this._child(":first"));
				return;
			}
			var base = this.active.offset().top
			  , height = this.$list.height()
			  , result = this.$list.children(":visible").filter(function() {
					var close = $(this).offset().top - base - height;
					return Math.abs(close) <= $(this).height() / 2;
				}).eq(0);

			if (!result.length) {
				result = this._child(":last");
			}
			this.activate(result);
		} else {
			this.activate(this._child(!this.active || this.last() ? ":first" : ":last"));
		}
	},

	previousPage : function() {
		if (this.hasScroll()) {
			if (!this.active || this.first()) {
				this.activate(this._child(":last"));
				return;
			}

			var base = this.active.offset().top
			  , height = this.$list.height()
			  , result = this.$list.children(":visible").filter(function() {
					var close = $(this).offset().top - base + height - $(this).height();
					return Math.abs(close) <= $(this).height() / 2;
				});

			if (!result.length) {
				result = this._child(":first");
			}
			this.activate(result);
		} else {
			this.activate(this._child(!this.active || this.first() ? ":last" : ":first"));
		}
	},

	hasScroll : function() {
		return this.$list.height() < this.$list[$.fn.prop ? "prop" : "attr"]("scrollHeight");
	},
	
	OPERATIONS: {
  		previousPage : $.ui.keyCode.PAGE_UP,
  		nextPage     : $.ui.keyCode.PAGE_DOWN,
  		previous     : $.ui.keyCode.UP, 
  		next         : $.ui.keyCode.DOWN,
  		toggle       : $.ui.keyCode.ENTER, 
  		toggleAbsend : 49, // num 1
  		toggleLate   : 50, // num 2
  		toggleEarly  : 51, // num 3
  		toggleAttend : 52  // num 4
  	}
});

$.widget("ui.detailPane", {
	_create: function() {
		this.template = doT.template($("#list-view-detail-template").html())
	},
	
	_setOption: function(key, value) {
		if(key == "student") {
			this.student = value;
			this.refresh();
		}
	},
    
    refresh: function() {
    	this.element.html(this.template(this.student));
    }
});
}(jQuery, doT, tms.rollcall.model));