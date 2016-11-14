package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.teaching.Term;

class TermService {
	Term getCurrentTerm() {
		def term = Term.findByActive(true)
		return term
	}
	
	Term getTerm(Long id) {
		return Term.get(id);
	}
	
	List<Long> getAllTermIds() {
		Term.executeQuery 'select id from Term'
	}
	
	List<Long> getLastTermIds(Integer last) {
		Term.executeQuery 'select id from Term order by id desc',[:] , [max: last]
	}
}
