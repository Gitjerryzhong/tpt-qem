package cn.edu.bnuz.tpt

import java.util.Date;

class TptLog {
	/**
	 * 插入操作
	 */
	public static final int ACTION_INSERT = 0
	/**
	 * 修改操作
	 */
	public static final int ACTION_UPDATE = 10
	
	/**
	 * 删除操作
	 */
	public static final int ACTION_DELETE = 20
	String userId
	int action
	Date date
	String content
	long objectId
	String src
	static mapping = {
		table 		'tpt_log'
		userId		length: 10
	}
	
    static constraints = {
		content 	nullable: true
    }
}
