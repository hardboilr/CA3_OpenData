'use strict';

angular.module('myApp.Home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {
    templateUrl: 'views/home/home.html',
    controller: 'HomeCtrl',
    controllerAs : 'ctrl'
  });
}])

.controller('HomeCtrl', [function() {
}]);