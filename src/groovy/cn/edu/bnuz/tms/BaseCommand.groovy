package cn.edu.bnuz.tms

class BaseCommand {
	def copyTo(target) {
		def (sProps, tProps) = [this, target]*.properties*.keySet()
		def commonProps = sProps.intersect(tProps) - ['class', 'metaClass']
		commonProps.each { target[it] = this[it] }
	}
}
