//= require core/tms
//= require lib/json3.min
//= require teach/model/arrangement
//= require_self

(function($, tms, Arrangement, undefined) {
	
var Model = tms.createModual('tms.rollcall.model');

$.extend(Model, {
    // CONSTANTS
    // rollcallTypes
	ABSENT : 1,
	LATE : 2,
	EARLY : 3,
	LEAVE : 4,
	LATE_EARLY : 5,
	ATTEND : 6,
	
	// student status masks
	FREE_LISTEN : 15,
	CANCEL_EXAM : 1
});

$.extend(Model, {
	states : {
		absent: {text:'旷课', value:Model.ABSENT},
		late  : {text:'迟到', value:Model.LATE, comb:Model.EARLY},
		early : {text:'早退', value:Model.EARLY,comb:Model.LATE},
		attend: {text:'调课', value:Model.ATTEND}
	}
});

Model.buttonClasses = $.map(Model.states, function(state, key){
	return 'btn-' + key;}
).join(' ');

/* VIEW MODEL
 * =============== */
var ViewModel = Model.ViewModel = function(options) {
	this.options = options;
	this.formId = options.form ? options.form.id : 0;
	this.remoteServer = new RemoteServer(options);
	this.settings = $.extend({}, this.defaultSettings, options.settings)
	this._init();
};

ViewModel.prototype = {
	constructor: ViewModel,

	defaultSettings : {
		hideFree : false,
		hideLeave : false,
		hideCancel : false,
		random: 100
	},
	
	changedHanders : [],
	arrangements: [],
    
	_init: function() {
		var that = this;
		this.students = [];
		this.studentsFree = [];
		this.studentsLeave= [];
		this.studentsCancel = [];
		this.studentsRandom = [];
		this.studentsMap = {};
		$.each(this.options.students, function() {
			var student = new Student(that, this);
			that.students.push(student);
			that.studentsMap[student.id] = student;
			if(student.isFreeListen()) {
				that.studentsFree.push(student);
			} else if(student.isCanceledExam()) {
				that.studentsCancel.push(student);
			} else if(student.isLeave()) {
				that.studentsLeave.push(student);
			} else {
				that.studentsRandom.push(student);
			}
		});
		this.hideStudentsFree();
		this.hideStudentsLeave();
		this.hideStudentsCancel();
		this.hideStudentsRandom();
		
		$.each(this.options.arrangements, function() {
			var arr = new Arrangement(this);
			that.arrangements.push(arr);
			if(arr.id == that.options.arrangementId) {
				that.currentArrangement = arr;
			}
		});
    },
    
    first: function() {
    	if(this.students.length) {
        	return this.students[0];        		
    	} else {
    		return null;
    	}
    },
    
    firstVisible: function() {
    	if(this.students.length) {
    		for(var i = 0; i < this.students.length; i++) {
    			var student = this.students[i];
    			if(student.visible) {
    				return student;
    			}
    		}
    		return null;
    	} else {
    		return null;
    	}
	},
    
    size: function() {
    	return this.students.length;
    },
    
    getSummary: function() {
    	var types = ['normal', 'absent', 'late', 'early', 'leave', 'lateEarly', 'attend']
      	  , counter = {}
      	  , visible = 0;
      	$.each(types, function() {
      		counter[this] = 0;
      	});

      	$.each(this.studentsRandom, function(index, student) {
         	counter[types[student.rollcall]]++;
         	if(student.visible) {
         		visible++;
         	}
      	});
      	
      	counter.total = this.students.length;
      	counter.leave = this.studentsLeave.length;
      	counter.free = this.studentsFree.length;
      	counter.cancel = this.studentsCancel.length;
      	counter.should = counter.total - counter.leave - counter.free - counter.cancel;
      	counter.visible= visible;
      	
      	return counter;
    },
    
    isLocked: function() {
    	return this.options.form && this.options.form.status == 1;
    },
    _getCounts: function() {

    }, 
    
    submitted: function() {
    	return this.formId != 0;
    },
    
    perfect: function() {
    	var that = this;
    	this.remoteServer.perfect(function(formId){
    		that.formId = formId;
    		$(that).triggerHandler("perfectSaved");
    	});
    },
    
    hide: function(free, leave, cancel, random) {
    	var settings = this.settings
    	  , changed = false;
		if(settings.hideFree != free) {
			settings.hideFree = free;
			changed = true;
			this.hideStudentsFree();
		}
		if(settings.hideLeave != leave) {
			settings.hideLeave = leave;
			changed = true;
			this.hideStudentsLeave();
		}
		if(settings.hideCancel != cancel) {
			settings.hideCancel = cancel;
			changed = true;
			this.hideStudentsCancel();
		}

		if(settings.random != random) {
			settings.random = random
			changed = true;
			this.hideStudentsRandom();
		} 
		
		if(changed) {
			this.saveSettings("rollcall.settings", JSON.stringify({
				hideFree : free,
	    		hideLeave : leave,
	    		hideCancel : cancel,
	    		random: random
			}));
		}
    },
    
    hideStudentsFree: function() {
    	var settings = this.settings;
    	$.each(this.studentsFree, function(index, student) {
			student.setVisible(!settings.hideFree);
		});
    },
    
    hideStudentsLeave: function() {
    	var settings = this.settings;
    	$.each(this.studentsLeave, function(index, student) {
			student.setVisible(!settings.hideLeave);
		});
    },
    
    hideStudentsCancel: function() {
    	var settings = this.settings;
    	$.each(this.studentsCancel, function(index, student) {
			student.setVisible(!settings.hideCancel);
		});
    },
    
    hideStudentsRandom: function() {
    	var random = this.settings.random;
    	
    	if(random < 10 || random > 90) {
    		$.each(this.studentsRandom, function(index, student) {
        		student.setVisible(true);
        	});
    		return;
    	}
    	var studentsRandom = this.studentsRandom
    	  , total = studentsRandom.length
    	  , numberToHide = (100 - random) / 100 * total
    	  , statis = [];
    	// 统计迟到旷课早退次数,清除之前的随机
    	$.each(this.studentsRandom, function(index, student) {
    		statis[index] = student.statis[0] + student.statis[1] + student.statis[2];
    		student.setVisible(true);
    	});
    	// 随机选择，统计减1，达到-1则隐藏
    	var count = 0;
    	while(numberToHide > 0) {
    		var index = Math.floor(Math.random() * total);
    		if(statis[index] > -1) {
        		statis[index]--;
        		if(statis[index] == -1) {
        			studentsRandom[index].setVisible(false);
        			numberToHide--;
        		}
    		}
    	} 
    },
    
    saveSettings: function(key, value) {
    	this.remoteServer.saveSettings(key, value);
    },
    
    getStudentById: function(studentId) {
    	return this.studentsMap[studentId];
    },
    
    toggleById: function(studentId, stateName) {
    	this.toggle(this.studentsMap[studentId], stateName);
    },
    
    toggle : function(student, stateName) {
		var prevValue = student.rollcall
		  , prevState
		  , currValue
		  , currState = Model.states[stateName]
		  , remoteServer = this.remoteServer
		  ;
		if(prevValue == 0) { // 插入
			currValue = currState.value;
			if(this.formId == 0) {
    			remoteServer.insertForm(student, currValue);
			} else {
    			remoteServer.insert(this.formId, student, currValue);
			}
		} else {
			$.each(Model.states, function(key, state){
				if(state.value == prevValue) {
					prevState = state;
					return false;
				}
			});
			
			if(prevState) { // 直接存在
				// 前后相同则删除；否则，如果有组合值则取组合；否则取当前值
				currValue = (prevState == currState) ? 0 : ((prevState.value == currState.comb) ? Model.LATE_EARLY : currState.value);
			} else { // 不存在则之前是组合值
				currValue = currState.comb ? currState.comb : currState.value; 
			}
			
			if(currValue == 0) { // 删除
				remoteServer.remove(student);
			} else {// 更新
				remoteServer.update(student, currValue);
			}
		}
	}
} 

var Student = function(viewModel, student) {
	this.viewModel = viewModel;
	$.extend(this, student);
	var that = this;
	if(viewModel.options.form) {
		$.each(viewModel.options.form.items, function() {
			if(this.studentId == that.id) {
				that.rollcall = this.type;
				that.itemId = this.id;
				return false; // break each;
			}
		});
	}
	$.each(viewModel.options.leaveRequests, function() {
		if(this.studentId == that.id) {
			that.rollcall = Model.LEAVE;
			that.leaveRequest = this;
			return false;
		}
	});
	
	if(viewModel.options.statis[this.id]) {
		this.statis = viewModel.options.statis[this.id];
	} else {
		this.statis = [0,0,0,0]
	};
};

Student.prototype = {
	constructor: Student,
	
	rollcall: 0,
	
	itemId: 0,
	
	visible: true,
	
	isFreeListen : function() {
		return this.status & (1 << Model.FREE_LISTEN);
	},
			
	isCanceledExam : function() {
		return this.status & (1 << Model.CANCEL_EXAM);
	},
		
	isLeave : function() {
		return this.rollcall == Model.LEAVE;
	},
	
	leaveLabel: function() {
		if(this.isLeave()) {
    		return tms.rollcall.format.leaveType(this.leaveRequest.type)
		} else {
			return "";
		}
	},
	
	hasState: function(key) {
		var state = Model.states[key];
		return this.rollcall == state.value || this.rollcall == state.value + state.comb;
	},
	
	toggle : function(currentState) {
		this.viewModel.toggle(this, currentState)
	},
	
	setVisible: function(value) {
		if(this.visible != value) {
    		this.visible = value;
    		$(this).triggerHandler("visible");    
    		$(this.viewModel).triggerHandler("visible", this);
		}
	},
	
	formInserted: function(formId, itemId, rollcall, statis) {
		this.viewModel.formId = formId;
		this.rollcall = rollcall;
		this.itemId = itemId;
		this.statis = statis;
		this._triggerChanged();
	},
	
	inserted: function(itemId, rollcall, statis) {
		this.rollcall = rollcall;
		this.itemId = itemId;
		this.statis = statis;
		this._triggerChanged();
	},
	
	updated: function(rollcall, statis) {
		this.rollcall = rollcall;
		this.statis = statis;
		this._triggerChanged();
	},
	
	deleted: function(statis) {
		this.rollcall = 0;
		this.itemId = 0;
		this.statis = statis;
		this._triggerChanged();
	},
	
	removeError: function() {
		$(this.viewModel).triggerHandler("studentSaveError", this);
	},
	
	_triggerChanged: function() {
		$(this.viewModel).triggerHandler("studentSaved", this);
	}
}

/* REMOTE SERVER
 * =============== */
var RemoteServer = function(options) {
	this.arrangementId = options.arrangementId;
	this.week = options.week;
};

RemoteServer.prototype = {
	insertForm : function(student, type) {
		$.ajax({
			type: "POST",
			url: "../form",
			data: {
    			arrangementId: this.arrangementId,
				week: this.week,
				studentId: student.id,
				type: type
			},
			success: function(data) {
				student.formInserted(data.formId, data.itemId, type, data.statis);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				student.removeError();
				alert("textStatus:" + textStatus + ", error:" + errorThrown);
			}
		});
	},
	
	insert: function(formId, student, type) {
		$.ajax({
			type: "POST",
			url: "../item",
			data: {
				formId: formId, 
				studentId: student.id,
				type: type
			},
			success: function(data) {
				student.inserted(data.itemId, type, data.statis);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				student.removeError();
				alert("textStatus:" + textStatus + ", error:" + errorThrown);
			}
		});
	},
	
	update: function(student, type) {
		var that = this;
		$.ajax({
			type: "PUT",
			url: "../item/" + student.itemId,
			data: JSON.stringify({type: type }),
			contentType: 'application/json',
			success: function(data) {
				student.updated(type, data.statis);						
			},
			error: function(jqXHR, textStatus, errorThrown) {
				student.removeError();
				alert("textStatus:" + textStatus + ", error:" + errorThrown);
			}
		});
	},
	
	remove: function(student) {
		$.ajax({
			type: "DELETE",
			url: "../item/" + student.itemId,
			success: function(data) {
				student.deleted(data.statis);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				student.removeError();
				alert("textStatus:" + textStatus + ", error:" + errorThrown);
			}
		});
	},
	
	perfect: function(onSuccess) {
		var that = this;
		
		$.ajax({
			type: "POST",
			url: "../form",
			data: {
    			arrangementId: this.arrangementId,
				week: this.week
			},
			success: function(data) {
				onSuccess(data.formId);					
			},
			error: function(jqXHR, textStatus, errorThrown) {
				student.removeError();
				alert("textStatus:" + textStatus + ", error:" + errorThrown);
			}
		});
	},
	
	saveSettings: function(key, value) {
		$.post("../../teacher/setting", {name: key, value: value});
	}
};
} (jQuery, tms, tms.teach.model.Arrangement));