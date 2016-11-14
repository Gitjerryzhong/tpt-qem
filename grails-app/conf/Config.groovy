// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

grails.config.locations = [ "classpath:${appName}-config.properties",
                            "classpath:${appName}-config.groovy",
                            "file:${userHome}/.grails/${appName}-config.properties",
                            "file:${userHome}/.grails/${appName}-config.groovy"]
if (System.properties["${appName}.config.location"]) {
	grails.config.locations << "file:" + System.properties["${appName}.config.location"]
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml'],
	excel:         'application/vnd.ms-excel'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']
grails.resources.adhoc.excludes = ['**/WEB-INF/**','**/META-INF/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "none"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'none' // escapes values inside ${}
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
	def logDirectory = grails.util.Environment.warDeployed ? System.getProperty('catalina.base') + '/logs' : 'target'
	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
		rollingFile name:'tracking',
				 	file:logDirectory + '/tms-user-tracking.log', 
					layout:  pattern(conversionPattern: "%d{yyyy-MM-dd HH:mm:ss.SSS}  %m%n")
	}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
	debug  'grails.app.controllers',
		   'grails.app.services',
		   'grails.app.domain',
		   'grails.app.utils'
		   //'grails.app.taglib.cn',
		   //'org.springframework',
		   //'org.hibernate',
		   //'org.hibernate.SQL',
		   //'org.hibernate.transaction',
		   //'org.hibernate.tool.hbm2ddl'
		   //'cn.edu.bnuz.tms'
	info  additivity: false,
		  tracking: 'user-tracking'
}

grails {
	plugin {
		springsecurity {
			successHandler.defaultTargetUrl = '/'
			securityConfigType = "InterceptUrlMap"
			interceptUrlMap = InterceptUrlMap.mappings
			printStatusMessages = false
		}
	}
}

grails.gorm.default.mapping = {
	version false
	
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateMidnight,         class: org.joda.time.DateMidnight
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime,             class: org.joda.time.DateTime
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTimeZoneAsString, class: org.joda.time.DateTimeZone
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDurationAsString,     class: org.joda.time.Duration
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInstantAsMillisLong,  class: org.joda.time.Instant
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInterval,             class: org.joda.time.Interval
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate,            class: org.joda.time.LocalDate
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime,        class: org.joda.time.LocalDateTime
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalTime,            class: org.joda.time.LocalTime
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentPeriodAsString,       class: org.joda.time.Period
	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentYears,                class: org.joda.time.Years
	
	// java 8 java.time.*
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentDurationAsMillisLong ,class: java.time.Duration
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentInstantAsMillisLong,  class: java.time.Instant
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentLocalDate,            class: java.time.LocalDate
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentLocalTime,            class: java.time.LocalTime
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime,        class: java.time.LocalDateTime
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentPeriodAsString,       class: java.time.Period
//	"user-type" type: org.jadira.usertype.dateandtime.threeten.PersistentYearAsInteger ,       class: java.time.Year

}

grails.reload.enabled = true

environments {
	development {
		tms.student.picturePath = "d:\\temp\\student"
		tms.user.picturePath="d:\\temp\\profile"
		tms.tpt.uploadPath = "d:\\temp\\tpt"
		tms.qem.uploadPath = "d:\\temp\\qem"
	}
	production {
		tms.student.picturePath = "/var/tms/pictures"
		tms.user.picturePath="/var/tms/profile"
		tms.tpt.uploadPath = "/var/tms/tpt"
		tms.qem.uploadPath = "/var/tms/qem"
	}
}

//grails.assets.bundle = true
//grails.assets.minifyJs = false

grails.assets.excludes = ["lib/*.js"]