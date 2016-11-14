/**
 * 
 */
var app = angular.module('pdfApp', ['pdf']);
app.controller('AppCtrl', [
                            '$scope',
                            'pdfDelegate',
                            '$timeout',
                          function($scope, pdfDelegate, $timeout) {
//                            $scope.pdfUrl = '/tms/tptUpload/2015/0803010018/trans_1_0803010018_88.pdf';

                            $scope.loadNewFile = function(url) {
                              pdfDelegate
                                .$getByHandle('my-pdf-container')
                                .load(url);
                            };
                        }]);