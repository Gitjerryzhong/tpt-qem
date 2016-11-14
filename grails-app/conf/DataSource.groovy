import cn.edu.bnuz.tms.orm.MysqlGrailsAnnotationConfiguration

dataSource {
	pooled = true
	jmxExport = true
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}
// environment specific settings
environments {
	development {
			
		dataSource {
			configClass = MysqlGrailsAnnotationConfiguration
			dbCreate = "update"
			url = "jdbc:mysql://localhost/tms?characterEncoding=gbk"
			driverClassName = 'com.mysql.jdbc.Driver'
			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
			username = ""
			password = ""

		    properties {
				// See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = "SELECT 1"
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				ignoreExceptionOnPreLoad = true
				// http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#JDBC_interceptors
				jdbcInterceptors = "ConnectionState;StatementCache(max=200)"
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED // safe default
				// controls for leaked connections
				abandonWhenPercentageFull = 100 // settings are active only when pool is full
				removeAbandonedTimeout = 120
				removeAbandoned = true
				// use JMX console to change this setting at runtime
				logAbandoned = false // causes stacktrace recording overhead, use only for debugging
				/*
				dbProperties {
					// Mysql specific driver properties
					// http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html
					// let Tomcat JDBC Pool handle reconnecting
					autoReconnect=false
					// truncation behaviour
					jdbcCompliantTruncation=false
					// mysql 0-date conversion
					zeroDateTimeBehavior='convertToNull'
					// Tomcat JDBC Pool's StatementCache is used instead, so disable mysql driver's cache
					cachePrepStmts=false
					cacheCallableStmts=false
					// Tomcat JDBC Pool's StatementFinalizer keeps track
					dontTrackOpenResources=true
					// performance optimization: reduce number of SQLExceptions thrown in mysql driver code
					holdResultsOpenOverStatementClose=true
					// enable MySQL query cache - using server prep stmts will disable query caching
					useServerPrepStmts=false
					// metadata caching
					cacheServerConfiguration=true
					cacheResultSetMetadata=true
					metadataCacheSize=100
					// timeouts for TCP/IP
					connectTimeout=15000
					socketTimeout=120000
					// timer tuning (disable)
					maintainTimeStats=false
					enableQueryTimeouts=false
					// misc tuning
					noDatetimeStringSync=true
				}
				*/
			}
			
			logSql = true
			//formatSql = true
		}
		
		dataSource_es {
			dbCreate = "none"
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			url = ""
			dialect = org.hibernate.dialect.Oracle10gDialect
			username = ""
			password = ""
			properties {
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = "SELECT 1 from dual"
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				ignoreExceptionOnPreLoad = true
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED // safe default
				abandonWhenPercentageFull = 100 // settings are active only when pool is full
				removeAbandonedTimeout = 120
				removeAbandoned = true
				logAbandoned = false // causes stacktrace recording overhead, use only for debugging
			}
		}
	}
	test {
		dataSource {
			driverClassName = "org.h2.Driver"
			dbCreate = "update"
			url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			username = "sa"
			password = ""
			logSql = true
			formatSql = true
		}
	}
	production {
		
		dataSource {
			configClass = MysqlGrailsAnnotationConfiguration
			dbCreate = "none"
			url = "jdbc:mysql://localhost/tms?characterEncoding=gbk"
			driverClassName = 'com.mysql.jdbc.Driver'
			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
			username = ""
			password = ""

		    properties {
				// See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = "SELECT 1"
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				ignoreExceptionOnPreLoad = true
				// http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#JDBC_interceptors
				jdbcInterceptors = "ConnectionState;StatementCache(max=200)"
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED // safe default
				// controls for leaked connections
				abandonWhenPercentageFull = 100 // settings are active only when pool is full
				removeAbandonedTimeout = 120
				removeAbandoned = true
				// use JMX console to change this setting at runtime
				logAbandoned = false // causes stacktrace recording overhead, use only for debugging
				/*
				dbProperties {
					// Mysql specific driver properties
					// http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html
					// let Tomcat JDBC Pool handle reconnecting
					autoReconnect=false
					// truncation behaviour
					jdbcCompliantTruncation=false
					// mysql 0-date conversion
					zeroDateTimeBehavior='convertToNull'
					// Tomcat JDBC Pool's StatementCache is used instead, so disable mysql driver's cache
					cachePrepStmts=false
					cacheCallableStmts=false
					// Tomcat JDBC Pool's StatementFinalizer keeps track
					dontTrackOpenResources=true
					// performance optimization: reduce number of SQLExceptions thrown in mysql driver code
					holdResultsOpenOverStatementClose=true
					// enable MySQL query cache - using server prep stmts will disable query caching
					useServerPrepStmts=false
					// metadata caching
					cacheServerConfiguration=true
					cacheResultSetMetadata=true
					metadataCacheSize=100
					// timeouts for TCP/IP
					connectTimeout=15000
					socketTimeout=120000
					// timer tuning (disable)
					maintainTimeStats=false
					enableQueryTimeouts=false
					// misc tuning
					noDatetimeStringSync=true
				}
				*/
			}
		}
		
		dataSource_es {
			dbCreate = "none"
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			url = ""
			dialect = org.hibernate.dialect.Oracle10gDialect
			username = ""
			password = ""
			properties {
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = "SELECT 1 from dual"
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				ignoreExceptionOnPreLoad = true
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED // safe default
				abandonWhenPercentageFull = 100 // settings are active only when pool is full
				removeAbandonedTimeout = 120
				removeAbandoned = true
				logAbandoned = false // causes stacktrace recording overhead, use only for debugging
			}
		}
	}
}
