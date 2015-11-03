'use strict';

angular.module('myApp.CompanyInfo', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/company_info', {
    templateUrl: 'views/company_info/company_info.html',
    controller: 'CompanyInfoCtrl'
  });
}])

.controller('CompanyInfoCtrl', function() {
 
});