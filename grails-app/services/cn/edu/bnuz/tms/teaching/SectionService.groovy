package cn.edu.bnuz.tms.teaching

class SectionService {
	/**
	 * Section����
	 * start: tms�м�¼�Ŀ�ʼ��
	 * total: tms�м�¼���ܽ���
	 * subs: ������ʱ���
	 */
	private static sections = [
		(1) : [start: 1,  total: 2,  subs:[1]],
		(3) : [start: 3,  total: 2,  subs:[3]],
		(5) : [start: 5,  total: 2,  subs:[5]],
		(7) : [start: 7,  total: 2,  subs:[7]],
		(9) : [start: 9,  total: 1,  subs:[9]],
		(10): [start: 10, total: 2,  subs:[10]],
		(12): [start: 12, total: 2,  subs:[12]],
		(0):  [start: 0,  total: 1,  subs:[0]],             // ����
		(-1): [start: 1,  total: 9,  subs:[1, 3, 5, 7, 9]], // ���죨1-9�ڣ�
		(-2): [start: 1,  total: 4,  subs:[1, 3]],          // ���磨1-4�ڣ�
		(-3): [start: 5,  total: 5,  subs:[5, 7, 9]],       // ���磨5-9�ڣ�
		(-4): [start: 10, total: 4,  subs:[10, 12]],        // ���ϣ�10-13�ڣ�
		(-5): [start: 1,  total: 13, subs:[0, 1, 3, 5, 7, 9, 10, 12]], // ȫ��
	]
	
	private static confilcts = null
	
	/**
	 * ��ȡʱ���
	 * @param id ʱ���ID
	 * @return ʱ���
	 */
	def getSection(int id) {
		return sections[id]
	}
	
	/**
	 * ��ȡָ��ID��ʱ��ΰ�������ʱ���
	 * @param id ʱ���ID
	 * @return ��ʱ���
	 */
	def getSubSections(int id) {
		sections[id].subs.collect { start ->
			sections[start]
		}
	}
	
	/**
	 * ��ȡ��ͻʱ���
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
	 * �ж�����ʱ����Ƿ��ͻ
	 * @param a ʱ���A
	 * @param b ʱ���B
	 * @return �Ƿ��ͻ
	 */
	def isConfilict(int a, int b) {
		return sections[a].subs.intersect(sections[b].subs).size() != 0
	}
}
