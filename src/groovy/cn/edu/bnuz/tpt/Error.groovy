package cn.edu.bnuz.tpt

class Error {
	def errors
	Error(){
		errors=new ArrayList<ErrorItem>()	
	}
	def push(ErrorItem item){		
		errors.add(item)
	}
	def hasError(){
		return errors.size()>0
	}
}
