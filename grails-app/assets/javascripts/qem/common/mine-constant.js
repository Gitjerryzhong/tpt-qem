angular.module('mine.constant', [])
  .constant('bnudisciplines',
		  [{"title":"哲学","majors":["哲学"]},
           {"title":"经济学","majors":["理论经济学", "应用经济学", "金融（专业学位）", "应用统计（专业学位）", "税务（专业学位）", "国际商务（专业学位）", "保险（专业学位）", "资产评估（专业学位）", "审计（专业学位）"]},
           {"title":"法学","majors":["法学", "政治学", "社会学", "民族学", "马克思主义理论", "公安学", "法律（专业学位）", "社会工作（专业学位）", "警务（专业学位）"]},
           {"title":"教育学","majors":["教育学", "心理学（可授教育学、理学学位）", "体育学", "教育（专业学位）", "体育（专业学位）", "汉语国际教育（专业学位）", "应用心理（专业学位）"]},
           {"title":"文学","majors":["中国语言文学", "外国语言文学", "新闻传播学", "翻译（专业学位）", "新闻与传播（专业学位）", "出版（专业学位）"]},
           {"title":"历史学","majors":["考古学", "中国史", "世界史", "文物与博物馆（专业学位）"]},
           {"title":"理学","majors":["数学", "物理学", "化学", "天文学", "地理学", "大气科学", "海洋科学", "地球物理学", "地质学", "生物学", "系统科学", "科学技术史", "生态学", "统计学"]},
           {"title":"工学","majors":["力学", "机械工程", "光学工程", "仪器科学与技术", "材料科学与工程", "冶金工程", "动力工程及工程热物理", "电气工程", "电子科学与技术", "信息与通信工程", "控制科学与工程", "计算机科学与技术", "建筑学", "土木工程", "水利工程", "测绘科学与技术", "化学工程与技术", "地质资源与地质工程", "矿业工程", "石油与天然气工程", "纺织科学与工程", "轻工技术与工程", "交通运输工程", "船舶与海洋工程", "航空宇航科学与技术", "兵器科学与技术", "核科学与技术", "农业工程", "林业工程", "环境科学与工程", "生物医学工程", "食品科学与工程", "城乡规划学", "风景园林学", "软件工程", "生物工程", "安全科学与工程", "公安技术", "建筑学（专业学位）", "工程（专业学位）", "城市规划（专业学位）"]},
           {"title":"农学","majors":["作物学", "园艺学", "农业资源与环境", "植物保护", "畜牧学", "兽医学", "林学", "水产", "草学", "农业推广（专业学位）", "兽医（专业学位）", "风景园林（专业学位）", "林业（专业学位）"]},
           {"title":"医学","majors":["基础医学", "临床医学", "口腔医学", "公共卫生与预防医学", "中医学", "中西医结合", "药学", "中药学", "特种医学", "医学技术", "护理学", "临床医学（专业学位）", "口腔医学（专业学位）", "公共卫生（专业学位）", "护理（专业学位）", "药学（专业学位）", "中药学（专业学位）"]},
           {"title":"军事学","majors":["军事思想及军事历史", "战略学", "战役学", "战术学", "军队指挥学", "军制学", "军队政治工作学", "军事后勤学", "军事装备学", "军事训练学", "军事（专业学位）"]},
           {"title":"管理学","majors":["管理科学与工程", "工商管理", "农林经济管理", "公共管理", "图书情报与档案管理", "工商管理（专业学位）", "公共管理（专业学位）", "会计（专业学位）", "旅游管理（专业学位）", "图书情报（专业学位）", "工程管理（专业学位）"]},
           {"title":"艺术学","majors":["艺术学理论", "音乐与舞蹈学", "戏剧与影视学", "美术学", "设计学", "艺术（专业学位）"]}])
  .constant('runStatusText', 
		  {"0": "未提交合同",
			"1": "已提交合同",
			"201": "学院确认合同",
			"202": "学院不同意",
			"203": "学院退回",
			"2" : "学校确认合同",
			"3" : "项目终止",
			"4" : "学校退回合同",
			"5" : "项目中止",
			"9" : "年检开始",
			"10" : "年检报告提交",
			"11" : "等待安排专家",
			"1101" : "学院通过年检",
			"1102" : "学院不通过年检",
			"1103" : "学院退回年检",
			"12" : "正安排专家",
			"13" : "专家评审中",
			"14" : "年检评审通过",
			"15" : "年检评审不通过",
			"16" : "年检退回学院",
			"19" : "中检开始",
			"20" : "中检报告提交",
			"21" : "等待安排专家",
			"2101" : "学院通过中报",
			"2102" : "学院不通过中报",
			"2103" : "学院退回中报",
			"22" : "正安排专家",
			"23" : "专家评审中",
			"24" : "中报评审通过",
			"25" : "中报评审不通过",
			"26" : "中报退回学院",
			"27" : "限期整改",
			"29" : "结题开始",
			"30" : "结题报告提交",
			"31" : "等待安排专家",
			"3101" : "学院通过结题",
			"3102" : "学院不通过结题",
			"3103" : "学院退回结题",
			"32" : "正安排专家",
			"33" : "专家评审中",
			"34" : "结题评审通过",
			"35" : "结题评审不通过",
			"36" : "结题退回学院",
			"37" : "暂缓通过"})
  .constant('aboutLeaders',{
	  'projectLevels':[{"id":1,"name":"校级"},{"id":2,"name":"省级"},{"id":3,"name":"国家级"}],
	  'titles':['教授', '副教授', '讲师', '助教', '其他正高级', '其他副高级', '其他中级', '其他初级', '未评级'],
	  'positions':['校长', '副校长', '处长', '副处长', '院长/部长/主任', '副院长/副部长/副主任', '系主任/专业负责人', '院长助理/部长助理', '实验室负责人','其他','无'],
	  'degrees':['大专','本科','硕士','博士']
  }) 
  .constant('aboutUpdate',{
	  'updateStatus':{
		  '0':['未提交','未知','未知','学院退回'],
		  '1':['学院未审','未知','学院不通过','学校退回'],
		  '2':['学院通过','学校通过','学校不通过','未知'] 
	  },
	  'updateTypes':[{"id":1,"name":"变更项目负责人"},{"id":2,"name":"延期"},{"id":3,"name":"改变项目名称"},{"id":4,"name":"研究内容重大调整"},{"id":5,"name":"自行中止项目"},{"id":6,"name":"改变成果形式"},{"id":7,"name":"变更参与人"},{"id":8,"name":"其他"}],
	  'updateTypeText':{
		  "1":"变更项目负责人",
		  "2":"延期",
		  "3":"改变项目名称",
		  "4":"研究内容重大调整",
		  "5":"自行中止项目",
		  "6":"改变成果形式",
		  "7":"变更参与人",
		  "8":"其他"
	  }
  })
 .constant('aboutProject',{
	  'projectLevels':[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}],
	  'projectStatus':[{"id":"10","name":"在研"},{"id":"20","name":"结题"},{"id":"32","name":"终止"},{"id":"33","name":"中止"}]
  })
