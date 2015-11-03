'use strict';

angular.module('myApp.CreateUser', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/create_user', {
    templateUrl: 'views/create_user/create_user.html',
    controller: 'CreateUserCtrl'
  });
}])

.controller('CreateUserCtrl', function() {
 
});
