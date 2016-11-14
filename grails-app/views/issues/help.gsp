<html>
<head>
<meta name="layout" content="main" />
<title>使用帮助</title>
<asset:stylesheet src="issues/help"/>
<asset:javascript src="issues/help"/>
<asset:script>
$(function() {
    $("#issue-help").issueHelp();
});
</asset:script>
</head>
<body>
	<div class="row">
		<div id="sidebar" class="col-sm-3">
			<g:render template="sidebar" bean="${statis}"/>
		</div>
		<div id="issue-help" class="col-sm-9 ">
			<p>在新建问题或进行评论时，系统采用<a href="http://daringfireball.net/projects/markdown/" target="_blank">Markdown</a>语法。
			 Markdown的语法简洁明了，使用普通文本即可编写网页。关于Markdown的中文完整说明见<a href="http://wowubuntu.com/markdown/index.html" target="_blank">这里</a>。</p>
			<p>下表是Markdown语法的简明手册：</p>
			<table class="table">
				<thead>
					<tr>
						<th>Markdown标记</th>
						<th>实际显示</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
<p>一个段落：一段文字由连续的多行文字组成。</p>
<pre>第1句话。
第2句话。</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
					</tr>
					<tr>
						<td>
<p>多个段落：在段落之间加上<code>空行</code>进行分段。</p>
<pre>第1段第1句。
第1段第2句。

第2段第1句。
第2段第2句。</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
					</tr>
					<tr>
						<td>
<p>多级标题：文字前添加1到6个<code>#</code>产生一至六级标题。</p>
<pre>#一级标题
##二级标题
###三级标题
####四级标题
#####五级标题
######六级标题
</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
					</tr>
					<tr>
						<td>
<p>无序列表：文字前添加<code>+</code>、<code>-</code>或<code>*</code>和<code>1个空格</code>产生列表项。</p>
<pre>- 项目1
- 项目2
+ 项目1
+ 项目2
* 项目1
* 项目2
</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
					</tr>
					<tr>
						<td>
<p>多级无序列表：<code>+</code>、<code>-</code>或<code>*</code>前添加<code>4个空格</code>产生多级列表项。</p>
<pre>* 一级项目1
    - 二级项目1
        + 三级项目1
        + 三级项目2
    - 二级项目2
* 一级项目2
    - 二级项目1
    - 二级项目2</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
						<td>
						
						</td>
					</tr>
					<tr>
						<td>
<p>有序列表：文字前添加<code>数字</code>加<code>.</code>和<code>空格</code>产生有序列表项。</p>
<pre>1. 项目1
2. 项目2
3. 项目3
4. 项目4</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
						<td>
						
						</td>
					</tr>
					<tr>
						<td>
<p>超级链接：自动识别超级链接。或者<code>方框号</code>中写链接的文字，<code>小括号</code>中写链接的地址。</p>	
<pre>http://www.google.com.hk
或者
[google](http://www.google.com.hk)</pre>
						</td>
						<td>
							<div class="markdown"></div>
						</td>
						<td>
											
						</td>
					</tr>
					<tr>
						<td>
<p>图片：<code>惊叹号</code>加<code>方框号</code>中写替代文字(可省略)，<code>小括号</code>中写图片的地址。	</p>
<pre>![百度](http://www.baidu.com/img/bdlogo.gif)</pre>
注：由于空间有限，系统目前不提供图片上传功能，请自行在其它网站存储图片。
						</td>
						<td>
							<div class="markdown"></div>
						</td>
						<td>
											
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>