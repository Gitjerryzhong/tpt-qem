//= require ui/week-tabs
//= require_self

(function($, tms, undefined) {
$.widget("ui.leaveSchedule", {
	options: {
    	// callback
    	change: null
	},
	
	_create: function () {
    	var that = this
      	  , $element = this.element
      	  , options = this.options
      	  ;
    	// 创建周次TAB
    	this.$tabs = $element.find(".nav-tabs").weekTabs({
    		start   : options.term.startWeek,
    		end     : options.term.endWeek,
    		active  : options.term.currentWeek,
        	stretch : true,
        	change  : function(e, week){
        		that._resetTable();
        		that._updateTable(week);
            }
        });
        
    	this.$table = $element.find("table")
    	
        this._createTable(options.term.currentWeek);
        this._updateTable(options.term.currentWeek);
    },
    
    _createTable: function (week) {
    	var that = this
      	  , $element = this.element
      	  , $table = this.$table
      	  , $days = this.$days = [] // 天
      	  , $arrs = this.$arrs = [] // 安排
    	  , $week
          ;
        
    	// whole week
    	$week = this.$week = $("<th colSpan='2' data-type='week'/>"); // 整周
        
        // table header
        var $tr = $("<tr/>").append($week).appendTo($table);
        for (var i = 1; i <= 7; i++) {
        	$days[i] = $("<th data-type='day'/>").appendTo($tr);
        }
        
        // table body
        var spans = [];
        spans[1]  = { span: "4", text: '上午' };
        spans[5]  = { span: "5", text: '下午' };
        spans[10] = { span: "4", text: '晚上' };
        
        for (var i = 1; i <= 13; i++) {
            $tr = $("<tr/>").appendTo($table);
            // 上午、下午、晚上
            if (spans[i]) {
                $("<td/>")
                	.prop('rowspan', spans[i].span)
                    .text(spans[i].text)
                    .addClass("c1")
                    .appendTo($tr);
            }
            // 第几节
            $("<td/>").text(i).addClass("c2").appendTo($tr);
            // 课表项
            $arrs[i] = []
            for (var j = 1; j <= 7; j++) {
            	$arrs[i][j] = $("<td data-type='arr' class='cn'/>").data("day", j).appendTo($tr);
            }
        }
    },
    
    _updateTable: function (week) {
    	var that = this
    	  , arrangements = this.options.arrangements
    	  ;
    	
    	this._setCell({
    		cell:  this.$week,
    		item:  {week: week},
    		text:  "第" + week + "周", 
    		title: "请假一周" 
        });

    	for(var i = 1; i <= 7; i++) {
            this._setCell({
        		cell:  this.$days[i],
        		item:  {week:week, day: i},
        		text:  tms.format.dayOfWeek(i), 
        		title: "请假一天"           	
            });
    	};
    	
        $.each(arrangements, function (index, val) {
        	if(that._isVisible(val, week)) {
                var $arrs = that.$arrs
                  , row = val.startSection
                  , col = val.dayOfWeek
                  , rowSpan = val.totalSection
                  , cell = $arrs[row][col]
                  ;
                // hide by total section
                for (var i = 1; i < rowSpan; i++) {
                    $arrs[row + i][col].hide();
                }

                cell.prop('rowspan', rowSpan);
                if(!that._hasMultiple(val, week)) {
                    that._setCell({
    	        		cell:  cell,
    	        		item:  {week: week, arr: val.id, arrangement:val},
    	        		text:  val.courseClass, 
    	        		title: "请假一次"           	
    	            });                	
                } else {
                	if(cell.children().length != 0) {
                		cell.append($("<hr>"));
                	}
                	cell.append($("<a/>").text(val.courseClass));
                	cell.prop("title", "课程冲突，请申请免听");
                	cell.addClass("multiple");
                }
        	}
        });
        
    	this._updateTabs();
    },
    
    _isVisible: function(arr, week) {
    	return week >= arr.startWeek 
    		&& week <= arr.endWeek
			&& (arr.oddEven == 0 || arr.oddEven == (week % 2 ? 1 : 2))
			&& !this._isFreeArrangement(arr);
    },
    
    _isFreeArrangement: function(arr) {
    	var exists = false;
    	for(var i = 0; i < this.options.freeArrangements.length; i++) {
    		if(this.options.freeArrangements[i] == arr.id) {
    			exists = true;
    			break;
    		}
    	}
    	return exists;
    },
    
    _hasMultiple: function(arr, week) {
    	var exists = false;
    	for(var i = 0; i < this.options.arrangements.length; i++) {
    		var curr = this.options.arrangements[i];
    		if(curr.id != arr.id 
    				&& this._isVisible(curr, week)
            		&& curr.dayOfWeek == arr.dayOfWeek 
            		&& curr.startSection == arr.startSection) {
    			exists = true;
    			break;
    		}
    	}
    	return exists;
    },

    _setCell: function(data) {
    	var that = this
    	  , $element = this.element
    	  , $cell = data.cell
    	  , leaveModel = this.options.leaveModel
    	  , conflict = leaveModel.conflictTest(data.item)
    	  ;
    	
    	if(conflict == null) {
    		this._hoverable($cell);
        	
        	$cell.append($("<a/>").text(data.text))
        	     .data("item", data.item)
        	     .prop("title", data.title)
        	     .addClass("enabled");
        	
        	this._on($cell, {
        		click: function() {
        			this._toggleCell($cell);
        			this._updateCell($cell);
        			this._updateTabs();
        		}
        	});
        	
        	if(leaveModel.hasItem(data.item)) {
        		this._selectCell($cell);
        	}
    	} else {
    		$cell.append($("<a/>").text(data.text))
		   	     .data("item", data.item)
		   	     .prop("title", conflict)
		   	     .addClass("disabled");
    		$("<span class='glyphicon glyphicon-ban-circle'/>").insertBefore($("a",$cell));
    	}
    },
    
    _selectCell: function($cell) {
    	$("<span class='glyphicon glyphicon-check'/>").insertBefore($("a",$cell));
    },
    
    _unselectCell: function($cell) {
    	$(".glyphicon-check", $cell).remove();
    },
    
    _cellSelected: function($cell) {
    	return $(".glyphicon-check", $cell).length > 0;
    },
    
    _toggleCell: function($cell) {
    	if(this._cellSelected($cell)) {
    		this._unselectCell($cell);
    	} else {
    		this._selectCell($cell);
    	}
    },
    
    _updateTabs: function() {
      	var leaveModel = this.options.leaveModel
    	  , $tabs = this.$tabs
    	  ;
      	
    	$tabs.children().removeClass("has-item");
    	// add * to week if have leave items
   	 	leaveModel.weeks().each(function() {
        	$tabs.find(":nth-child("+ this +")").addClass("has-item");        		
    	});
    },
    
    _updateCell: function($cell) {
    	var that = this
    	  , leaveModel = this.options.leaveModel
    	  , $element = this.element
    	  , $table = this.$table
	      , $week = this.$week
	      , type = $cell.data("type")
	      , item = $cell.data("item")
	      , selected = this._cellSelected($cell)
	      , $removedCell
	      ;
    	

    	if (selected) {
			switch (type) {
			case "week": // 选择整周，取得所有其它
				$removedCell = unselectDay(0).add(unselectArr(0));
				break;
			case "day":	// 选择整天，取消整周和当天的安排
				$removedCell = unselectWeek().add(unselectArr(item.day));
				break;
			case "arr": // 选择安排，取消整周和整天
				$removedCell = unselectWeek().add(unselectDay($cell.data("day")));
				break;
			}

			$removedCell.each(function() {
				that._unselectCell($(this));
				leaveModel.removeItem($(this).data("item"));
			});

			leaveModel.addItem(item);
		} else {
			leaveModel.removeItem(item);
		}
    	
    	function unselectWeek() {
    		return $("th[data-type='week']:has(.glyphicon-check)", $table);
    	}
    	
    	function unselectDay(day) {
    		return $("th:has(.glyphicon-check)", $table).filter(function(){
				var data = $(this).data("item").day
				  , type = $(this).data("type");
				return day == data || (day == 0 && type == "day");
			})
    	}
    	
    	function unselectArr(day) {
			return $("td:has(.glyphicon-check)", $table).filter(function(){
				var data = $(this).data("day");
				return day == data || day == 0;
			});
    	}
    },
    
    _resetTable : function() {
    	var $table = this.$table
    	  , $ths = $("th", $table)
    	  , $tds = $("td[data-type='arr']", $table)
    	  ;
    	
    	$ths.removeClass("disabled")
    		.removeData("item")
        	.empty();
        
        this._off($ths);
    	
    	$tds.prop("rowspan", 1)
            .removeClass("enabled disabled multiple")
            .removeData("item")
            .empty()
            .show();
            
        this._off($tds);
    	
    	$(".glyphicon-check", $table).remove();
    }
});
}(jQuery, tms));