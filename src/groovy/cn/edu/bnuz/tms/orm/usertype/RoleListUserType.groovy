package cn.edu.bnuz.tms.orm.usertype

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

import org.hibernate.HibernateException
import org.hibernate.usertype.UserType

import cn.edu.bnuz.tms.system.RoleList

class RoleListUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached)
	}

	@Override
	public Object deepCopy(Object o) throws HibernateException {
		return o
	}

	@Override
	public Serializable disassemble(Object o) throws HibernateException {
		return deepCopy(o)
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y
	}

	@Override
	public int hashCode(Object o) throws HibernateException {
		return o.hashCode()
	}

	@Override
	public boolean isMutable() {
		return true
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, org.hibernate.engine.spi.SessionImplementor si, Object o)
			throws HibernateException, SQLException {
		String val = resultSet.getString(names[0])
		if(val) {
			return new RoleList(val.split(",") as List)
		} else {
			return null
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object o, int i, org.hibernate.engine.spi.SessionImplementor si)
			throws HibernateException, SQLException {
		if(o) {
			statement.setString(i, o.toString())
		} else {
			statement.setNull(i, Types.VARCHAR)
		}
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original)
	}

	@Override
	public Class returnedClass() {
		RoleList
	}

	@Override
	public int[] sqlTypes() {
		[Types.VARCHAR] as int[]
	}

}
