'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'myApp.security',
  'myApp.Home',
  'myApp.Documentation',
  'myApp.CompanyInfo',
  'myApp.ExhangeRateInfo',
  'myApp.AllUsers',
  'myApp.CreateUser',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/home'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('authInterceptor');
});


