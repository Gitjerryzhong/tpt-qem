angular.module('mine.filter', [])
  .filter('uniKey',function(){
	return function(items,key){
		var out = [];
//		console.info(items);
		angular.forEach(items, function (item) {
			var exists=false;
            for (var i = 0; i < out.length; i++) {
                if (item[key] == out[i]) { 
                	exists=true;
                    break;
                }
            }
            if(!exists) {
            	out.push(item[key]);            	
            }
        })
//        倒序排序
        out.sort( function(b,a){
				if(typeof(a)=="number" || typeof(a)=="boolean") 
					return a>b;
				return a.localeCompare(b);
				});
        return out;
	}
})
.filter('filter2_0',function(){
	return function(items,key,value){
		var out = [];
		angular.forEach(items, function (item) {
	        if (item[key] == value) { 
	        	out.push(item);
	        }
        })
        return out;
	}
})
.filter("sort", function () {                       
    return function (items, key, value) {
//    	console.log(items);
    	if(value>0){
    		items.sort(function (a, b) {
    			if(typeof(a[key])=="number" || typeof(a[key])=="boolean") 
					return a[key]>b[key];
    			return a[key].localeCompare(b[key]);
    	    });
    	}
        else{
        	items.sort(function (b, a) {
    			if(typeof(a[key])=="number" || typeof(a[key])=="boolean") 
					return a[key]>b[key];
    			return a[key].localeCompare(b[key]);
    	    });
        }
    return items;
    }
})
.filter("groupForCollege", function () {                       
    return function (items) {
//    	console.log(items);
    	var groups={"10":"未审","20":"未审","30":"未审","16":"未审","26":"未审","36":"未审",
    				"1101":"已审","1102":"已审","2101":"已审","2102":"已审","3101":"已审","3102":"已审",
    				"11":"已审","12":"已审","13":"已审","14":"已审","15":"已审",
    				"21":"已审","22":"已审","23":"已审","24":"已审","25":"已审",
    				"31":"已审","32":"已审","33":"已审","34":"已审","35":"已审"}
    	angular.forEach(items, function (item) {
    		if(groups[item.runStatus]==undefined)item.groups="未提交或其他";
    		else item.groups=groups[item.runStatus];
    	})
    return items;    
    }
})
.filter("groupForUniversity", function () {                       
    return function (items) {
//    	console.log(items);
    	var index1=1;
    	var index2=1;
    	var index3=1;
    	var index4=1;
    	var groups={"1101":"未安排评审","1102":"未安排评审","2101":"未安排评审","2102":"未安排评审","3101":"未安排评审","3102":"未安排评审",
    				"11":"需评审","12":"已安排评审","13":"已安排评审","14":"已审","15":"已审","16":"已审",
    				"21":"需评审","22":"已安排评审","23":"已安排评审","24":"已审","25":"已审","26":"已审","27":"已审",
    				"31":"需评审","32":"已安排评审","33":"已安排评审","34":"已审","35":"已审","36":"已审","37":"已审"}
    	angular.forEach(items, function (item) {
    		if(groups[item.runStatus]==undefined)item.groups="未提交或其他";
    		else item.groups=groups[item.runStatus];
    		switch(item.groups){
    		case "未安排评审": item.index=index1++; break;
    		case "需评审": item.index=index2++; break;
    		case "已审": item.index=index3++; break;
    		case "未提交或其他": item.index=index4++; break;
    		}
    	})
    return items;    
    }
})
.filter("auditActionText", function () {                       
    return function (id) {
    	var actionText ={"20":"审核通过","21":"审核不通过","26":"退回"}
    	return actionText[id.toString()];    
    }
});
