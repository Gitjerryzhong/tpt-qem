package cn.edu.bnuz.tpt.teaching
import cn.edu.bnuz.tms.organization.Department

/**
 * �꼶רҵ
 * @author Yang Lin
 */

class GradeMajor {

    /**
     * �꼶רҵID
     * <pre>
     * 20140101
     * ----==-=
     *  |  | ||
     *  |  | | `-- ��רҵ
     *  |  | `---- ����רҵ
     *  |  `------ ѧԺ����
     *   `-------- �꼶
     * </pre>
     */
    Integer id

    /**
     * �꼶
     */
    Integer grade

    /**
     * �������
     */
    Integer candidateType

    /**
     * У��רҵ
     */
    Subject subject


    /**
     * ����ѧԺ
     */
    Department department


    static belongsTo = [
        department : Department // ����ѧԺ
    ]


    static mapping = {
        table        'tpt_grade_major'
        id            generator: 'assigned', comment: '�꼶רҵID'
        grade         comment: '�꼶'
        candidateType comment: '���������'
        subject       comment: 'У��רҵ'
        department    comment: '����ѧԺ'
    }

}
