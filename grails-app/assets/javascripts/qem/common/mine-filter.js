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
    	console.log(items);
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
});
//.filter("sort", function () {                       
//            return function (items, key, value) {
//                angular.forEach(items,function(item){
//                    
//                })
//                
//                if(typeof(items[0][key])=="number")
//                {return items;
//                }
//                else{
//            items.sort(function (a, b) {
//                return a[key].localeCompare(b[key]);
//            });
//            return items;}
//            }
//        });