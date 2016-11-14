package cn.edu.bnuz.tpt

class ErrorItem {
	def String id
	def args
	ErrorItem(String messId,List<String> args){
		id=messId
		this.args=args
	}
}
