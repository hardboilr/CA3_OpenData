'use strict';

angular.module('myApp.AllUsers', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/all_users', {
    templateUrl: 'views/all_users/all_users.html',
    controller: 'AllUsersCtrl'
  });
}])

.controller('AllUsersCtrl', function() {
 
});