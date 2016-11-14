package cn.edu.bnuz.tpt.teaching
import cn.edu.bnuz.tms.organization.Department

/**
 * 年级专业
 * @author Yang Lin
 */

class GradeMajor {

    /**
     * 年级专业ID
     * <pre>
     * 20140101
     * ----==-=
     *  |  | ||
     *  |  | | `-- 主专业
     *  |  | `---- 派生专业
     *  |  `------ 学院代码
     *   `-------- 年级
     * </pre>
     */
    Integer id

    /**
     * 年级
     */
    Integer grade

    /**
     * 考生类别
     */
    Integer candidateType

    /**
     * 校内专业
     */
    Subject subject


    /**
     * 所在学院
     */
    Department department


    static belongsTo = [
        department : Department // 所属学院
    ]


    static mapping = {
        table        'tpt_grade_major'
        id            generator: 'assigned', comment: '年级专业ID'
        grade         comment: '年级'
        candidateType comment: '允许考生类别'
        subject       comment: '校内专业'
        department    comment: '所属学院'
    }

}
