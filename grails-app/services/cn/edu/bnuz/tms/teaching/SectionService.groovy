package cn.edu.bnuz.tms.teaching

class SectionService {
	/**
	 * Section数据
	 * start: tms中记录的开始节
	 * total: tms中记录的总节数
	 * subs: 包含的时间段
	 */
	private static sections = [
		(1) : [start: 1,  total: 2,  subs:[1]],
		(3) : [start: 3,  total: 2,  subs:[3]],
		(5) : [start: 5,  total: 2,  subs:[5]],
		(7) : [start: 7,  total: 2,  subs:[7]],
		(9) : [start: 9,  total: 1,  subs:[9]],
		(10): [start: 10, total: 2,  subs:[10]],
		(12): [start: 12, total: 2,  subs:[12]],
		(0):  [start: 0,  total: 1,  subs:[0]],             // 中午
		(-1): [start: 1,  total: 9,  subs:[1, 3, 5, 7, 9]], // 白天（1-9节）
		(-2): [start: 1,  total: 4,  subs:[1, 3]],          // 上午（1-4节）
		(-3): [start: 5,  total: 5,  subs:[5, 7, 9]],       // 下午（5-9节）
		(-4): [start: 10, total: 4,  subs:[10, 12]],        // 晚上（10-13节）
		(-5): [start: 1,  total: 13, subs:[0, 1, 3, 5, 7, 9, 10, 12]], // 全天
	]
	
	private static confilcts = null
	
	/**
	 * 获取时间段
	 * @param id 时间段ID
	 * @return 时间段
	 */
	def getSection(int id) {
		return sections[id]
	}
	
	/**
	 * 获取指定ID的时间段包含的子时间段
	 * @param id 时间段ID
	 * @return 子时间段
	 */
	def getSubSections(int id) {
		sections[id].subs.collect { start ->
			sections[start]
		}
	}
	
	/**
	 * 获取冲突时间段
	 */
	def getConfilcts() {
		if(!confilcts) {
			confilcts = sections.collectEntries {k1, v1 ->
				def keys = []
				sections.each { k2, v2 ->
					if(v1.subs.intersect(v2.subs)) {
						keys << k2
					}
				}
				return [k1, keys]
			}
		}
		return confilcts
	}
	
	/**
	 * 判断两个时间段是否冲突
	 * @param a 时间段A
	 * @param b 时间段B
	 * @return 是否冲突
	 */
	def isConfilict(int a, int b) {
		return sections[a].subs.intersect(sections[b].subs).size() != 0
	}
}
