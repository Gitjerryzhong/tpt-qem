//= require core/format
//= require_self

(function(tms, undefined) {
	"use strict";
	var SECTION_TYPES = {
		"1": "1-2节",
		"3": "3-4节",
		"5": "5-6节",
		"7": "7-8节",
		"9": "第9节",
		"10": "10-11节",
		"12": "12-13节",
		"0": "中午（12-13点）",
		"-1": "白天（1-9节）",
		"-2": "上午（1-4节）",
		"-3": "下午（5-9节）",
		"-4": "晚上（10-13节）",
		"-5": "全天"
	}
	
	var DAYS = " 一二三四五六日";
	
	var ACTIONS = {
		"10": "提交申请",
		"11": "撤销申请",
		"12": "修改申请",
		"20": "审核通过",
		"21": "审核不通过",
		"30": "审批通过",
		"31": "审批不通过",
		"40": "关闭申请",
		"41": "回收申请"
	};
	
	var STATUS = [
		{name: "未提交", statusClass:"default", checkState: "不批准", checkClass:"text-danger"},
		{name: "待申请单位审核", statusClass:"info", checkState: "不批准", checkClass:"text-danger"},
		{name: "待教务处审批", statusClass:"info", checkState: "审核通过", checkClass:"text-success"},
		{name: "不批准", statusClass:"danger", checkState: "不批准", checkClass:"text-danger"},
		{name: "已批准", statusClass:"success", checkState: "审批通过", checkClass:"text-success"},
		{name: "已关闭", statusClass:"warning", checkState: "已关闭", checkClass:"text-warning"},
		{name: "已回收", statusClass:"warning", checkState: "已回收", checkClass:"text-warning"}
	]
	
	tms.createModual("tms.place.booking.format", {
		weeks: function(start, end) {
			if(start == end) {
				return "第" + start + "周";
			} else {
				return "第" + start + "-" + end + "周";
			}
		},
		
		week: function(week) {
			return tms.format.week(week); 
		},

		dayOfWeek: function(day) {
			return "星期" + DAYS.charAt(day);
		},
		
		sectionType: function(type) {
			return SECTION_TYPES[type];
		},
		
		getSectionTypes: function() {
			var ids = [1,3,5,7,9,10,12,-1,-2,0,-3,-4,-5];
			var types = [];
			for(var i = 0; i < ids.length; i++) {
				types.push({id:ids[i], name:SECTION_TYPES[ids[i]]})
			}
			return types;
		},
		
		actionText: function(action) {
			return ACTIONS[action]
		},
		
		statusText: function(status) {
			return STATUS[status].name
		},
		
		statusClass: function(status) {
			return STATUS[status].statusClass
		},
		
		checkStatusText: function(status) {
			return STATUS[status].checkState
		},
		
		checkStatusClass: function(status) {
			return STATUS[status].checkClass
		}
	});
}(window.tms));