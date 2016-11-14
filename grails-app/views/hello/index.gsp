<!DOCTYPE html>

<head>
<meta name="layout" content="main" />
  <meta charset="GBK">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>北师大珠海分校质量工程项目管理系统</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="bower_components/html5-boilerplate/dist/css/normalize.css">
  <link rel="stylesheet" href="bower_components/html5-boilerplate/dist/css/main.css">
  <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="app.css">
  <script src="bower_components/html5-boilerplate/dist/js/vendor/modernizr-2.8.3.min.js"></script>
  <style>
    body{font-family:"ff-tisa-web-pro-1","ff-tisa-web-pro-2","Lucida Grande","Helvetica Neue",Helvetica,Arial,"Hiragino Sans GB","Hiragino Sans GB W3","WenQuanYi Micro Hei",sans-serif;}
    h1, .h1, h2, .h2, h3, .h3, h4, .h4, .lead {font-family:"ff-tisa-web-pro-1","ff-tisa-web-pro-2","Lucida Grande","Helvetica Neue",Helvetica,Arial,"Hiragino Sans GB","Hiragino Sans GB W3","Microsoft YaHei UI","Microsoft YaHei","WenQuanYi Micro Hei",sans-serif;}
    pre code { background: transparent; }
    @media (min-width: 768px) {
      .bs-docs-home .bs-social,
      .bs-docs-home .bs-masthead-links {
        margin-left: 0;
      }
    }

    .bs-docs-section p {
      line-height: 2;
    }

    .bs-docs-section p.lead {
      line-height: 1.4;
    }
    .bs-header h1{
      padding-left: 10px;
      line-height: 4;
      color: #cdbfe3;
      background-color: #563d7c;
    }
  #notice{
    height: 500px;
    padding-left: 10px;
    padding-right: 10px;
  }
    #notice h3{
      color: #b81900;
    }
    #notice h4{
      color: #d58512;
    }
    #notice pre{
      font-size: 14px;
      line-height: 1.5;
    }

  </style>
</head>
<body>
<div class="bs-header" id="content">
  <div class="container">
    <h1>北京师范大学珠海分校质量工程项目管理系统</h1>
  </div>
</div>
<div class="container">
  <div class="row">
    <div class="col-md-3">
      <h3>登录系统</h3>
      <hr/>
      <form role="form">
        <div class="form-group">
          <label for="loginName">用户名：</label>
          <input id="loginName" type="text" class="form-control"  placeholder="请输入用户名" required/>
        </div>
        <div class="form-group">
          <label for="password">密 码：</label>
          <input type="password" class="form-control" id="password" placeholder="请输入密码" required/>
        </div>
        <button type="submit" class="btn btn-primary">登 录</button>
      </form>
      <h3>文件下载</h3>
      <hr/>
      <a href="#"></p>申请书下载</p></a>

      <a href="#"></p>个人申请流程</p></a>
      <a href="#"></p>软件使用说明</p></a>
      <h3>常见问题</h3>
      <hr/>
      <ul>
        <li>密码错误</li>
        <li>用户名忘记了</li>
        <li>文件无法上传</li>
        <li>无权限</li>
        <li>提交失败</li>
      </ul>
    </div>
    <div class="col-md-9">
      <div class="table table-bordered" id="notice">
        <h3>申请通知</h3>
        <h4>个人填写申请书流程</h4>
    <pre>
      1.点击页面左侧“申请书下载”链接进入申请书下载页面。
      2.点击页面左侧“申请书下载”链接进入页面。
      3.点击页面左侧“申请书下载”链接进入申请书下载页面。
      4.点击页面左侧“申请书下载”链接进入下载页面。
      5.点击页面左侧“申请书下载”链接进入申请书下载页面。
      6.点击页面左侧“申请书下载”链接进入申请书下载页面，申请书下申请书下。
    </pre>
        <h4>个人上传申请书文件流程</h4>
    <pre>
      1.点击页面左侧“申请书下载”链接进入申请书下载页面。
      2.点击页面左侧“申请书下载”链接进入页面。
      3.点击页面左侧“申请书下载”链接进入申请书下载页面。
      4.点击页面左侧“申请书下载”链接进入下载页面。
      5.点击页面左侧“申请书下载”链接进入申请书下载页面。
      6.点击页面左侧“申请书下载”链接进入申请书下载页面，申请书下申请书下。
    </pre>
      </div>
    </div>
  </div>
</div>

  <!-- In production use:
  <script src="//ajax.googleapis.com/ajax/libs/angularjs/x.x.x/angular.min.js"></script>
  -->
  <script src="bower_components/angular/angular.js"></script>
  <script src="bower_components/angular-route/angular-route.js"></script>
  <script src="app.js"></script>
  <script src="view1/view1.js"></script>
  <script src="view2/view2.js"></script>
  <script src="components/version/version.js"></script>
  <script src="components/version/version-directive.js"></script>
  <script src="components/version/interpolate-filter.js"></script>
</body>
</html>
