package cn.edu.bnuz.tpt

class TptPaperMtlRgn {
	/**
	 * ����=1
	 */
	public static final String TYPE_UNDERGRADUATE = "1"
	/**
	 * ˶ʿ=2
	 */
	public static final String TYPE_MASTER = "2"
	/**
	 * �γ�����=3
	 */
	public static final String TYPE_COURSE = "3"
	/**
	 * ѧ��ѧ��
	 */
	String studentID
	/**
	 * ѧ������
	 */
	String name
	/**
	 * ���ͣ����ƻ���˶ʿ���ǿγ�����
	 */
	String type
	/**
	 * ����רҵ
	 */
	String major
	/**
	 * ����ԺУ���ơ����ƺ���ԺУ����
	 */
	String foreignCollege
	/**
	 * ����ԺУ�Ͷ�רҵ�����ƺ���ԺУ�Ͷ�רҵ
	 */
	String f_c_major
	/**
	 * ����ԺУ����(�γ�)���ơ�����˶ʿ��ҵԺУ
	 */
	String masterCollege
	/**
	 * ����(�γ�)�ɼ�
	 */
	String master_result
	/**
	 * ����������Ŀ
	 */
	String paperName
	/**
	 * Ӣ��������Ŀ
	 */
	String paperName_en
	/**
	 * ��������ժҪ
	 */
	String abstrct
	/**
	 * Ӣ������ժҪ
	 */
	String abstrct_en
	String bn
	static mapping = {
		table 			'tpt_paper_mtl_rgn'
		studentID		length: 10
		name			length: 50
		major 			length: 50
		foreignCollege	length: 255
		f_c_major		length: 50
		masterCollege	length: 255
		master_result	length: 10
		paperName		length: 100
		paperName_en	length: 500
		abstrct			length: 500
		bn				length: 10
		type			length: 10
	}
    static constraints = {
		abstrct_en(maxSize:3000)
		masterCollege	nullable:true
		abstrct			nullable:true
		abstrct_en		nullable:true
    }
}
