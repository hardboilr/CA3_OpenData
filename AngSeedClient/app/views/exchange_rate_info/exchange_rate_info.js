'use strict';

angular.module('myApp.ExchangeRateInfo', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/exchange_rate_info', {
                    templateUrl: 'views/exchange_rate_info/exchange_rate_info.html'
                });
            }])
        .controller('ExchangeRateController', ['CurrencyFactory', function (CurrencyFactory, $rootScope) {
                console.log("in controller");
                var self = this;
                self.dailyRates = {};
                self.getDailyRates = function () {
                    CurrencyFactory.getDailyRates().success(function (dailyRates) {
                        self.dailyRates = dailyRates;
                    }).error(function (data) {
                        $rootScope.error = data.error + " : " + data.message;
                    });
                };
            }])
        .factory('CurrencyFactory', ['$http', function ($http) {
                var urlBase = 'api/currency';
                var getDailyRates = function () {
                    console.log(urlBase + '/' + 'dailyrates');
                    return $http.get(urlBase + '/' + 'dailyrates');
                };
                return {
                    getDailyRates: getDailyRates
                };
            }]);