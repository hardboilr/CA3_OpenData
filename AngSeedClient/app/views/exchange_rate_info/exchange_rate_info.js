'use strict';

angular.module('myApp.ExhangeRateInfo', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/exchange_rate_info', {
    templateUrl: 'views/exchange_rate_info/exchange_rate_info.html'
  });
}]);