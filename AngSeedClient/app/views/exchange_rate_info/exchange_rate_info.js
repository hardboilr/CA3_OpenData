'use strict';

angular.module('myApp.ExhangeRateInfo', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/exchange_rate_info', {
    templateUrl: 'views/exchange_rate_info/exchange_rate_info.html'
  });
}]).controller('ExchangeRateController', ['CurrencyFactory', function(CurrencyFactory) {
    var self = this;
    self.getDailyRates = function () {
            CurrencyFactory.getDailyRates().success(function(dailyRates) {
                console.log("got stuff");
                self.dailyRates = dailyRates;
            }).error(function(error) {
                self.errorMessage = 'Unable to load dailyRates: ' + error.message;
            });
        };
 
}]).factory('CurrencyFactory', ['$http', function($http) {

    var urlBase = '/api/currency';
    var dataFactory = {};

    dataFactory.getDailyRates = function () {
        return $http.get(urlBase + '/' + 'dailyrates');
    };
    return dataFactory;
}]);