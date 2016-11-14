package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.Notice
class NoticeService {

    def activeNotice() {
        def activeN=Notice.getAll()//.findAll("from Notice as n where n.start<:theDay and n.end>:theDay",[theDay:new Date()])
        return activeN
    }
}
