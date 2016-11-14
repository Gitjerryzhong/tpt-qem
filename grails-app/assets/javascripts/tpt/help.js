/**
 * 
 */
var app = angular.module('helpApp', []);
app.controller('HelpCtrl', [
                            '$scope',                            
                          function($scope) {
                            $scope.title = ["","第一步：阅读声明，点击继续>>","第二步：勾选“同意”，点击我要申请",
                                            "第三步：填写信息（全是必填项），点击“保存”",
                                            "第四步：上传照片（统一要求标准尺寸413*626，蓝底免冠，不可以将头像拉伸）",
                                            "第五步：上传证书",
                                            "第六步：上传成绩单",
                                            "第七步：点击“提交申请”",
                                            "第八步：等待材料审核结果",
                                            "第八步：材料审核通过后，根据实际情况点击论文成绩互认表申请",
                                            "第九步：填写论文成绩互认表，并上传论文",
                                            "第十步：等待终审结果……",
                                            "恭喜您！学位审核通过！"];
                            $scope.step=1;
                            $scope.goStep=function(nm){
                            	$scope.step+=nm;
                            	if($scope.step<1)$scope.step=1;
                            	if($scope.step>12)$scope.step=12;
                            }
                        }]);