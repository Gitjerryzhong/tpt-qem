package cn.edu.bnuz.tms.orm

import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration
import org.hibernate.Hibernate
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.IntegerType
import org.hibernate.type.StringType

class MysqlGrailsAnnotationConfiguration extends GrailsAnnotationConfiguration {
	MysqlGrailsAnnotationConfiguration() {
		this.addSqlFunction("group_concat", new StandardSQLFunction("group_concat", new StringType()))
		this.addSqlFunction("group_concat_distinct",new SQLFunctionTemplate(new StringType(), "group_concat(distinct ?1)"))
		this.addSqlFunction("op_not", new SQLFunctionTemplate(new IntegerType(), "(~?1)"));
		this.addSqlFunction("op_and", new SQLFunctionTemplate(new IntegerType(), "(?1 & ?2)"));
		this.addSqlFunction("op_or", new SQLFunctionTemplate(new IntegerType(), "(?1 | ?2)"));
		this.addSqlFunction("op_xor", new SQLFunctionTemplate(new IntegerType(), "(?1 ^ ?2)"));
	}
}
