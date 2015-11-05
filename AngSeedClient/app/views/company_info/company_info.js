'use strict';

angular.module('myApp.CompanyInfo', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/company_info', {
                    templateUrl: 'views/company_info/company_info.html',
                    controller: 'CompanyInfoCtrl'
                });
            }])

        .controller('CompanyInfoCtrl', function ($http, $rootScope) {
            var self = this;
            self.companyFound = false;
            self.searchQuery = {};
            self.companyInfo = {};

//            Use this url for karma tests
            var url = "http://cvrapi.dk/api?vat=31678021&country=dk";

//            Use this url for live testing
//            var url = "http://cvrapi.dk/api?" + self.searchQuery.option + "=" + self.searchQuery.query + "&country=" + self.searchQuery.country;

            self.search = function () {
                $http.get(url)
                        .success(function (data) {
                            if (data.error == undefined) {
                                self.companyInfo = data;
                                self.companyFound = true;
                            } else {
                                self.companyFound = false;
                                $rootScope.error = data.error + " : " + data.message;
                            }
                        }).error(function (data) {
                    self.companyFound = false;
                    $rootScope.error = data.error + " : " + data.message;
                });
            };

        })

        .filter('bankruptFilter', function () {
            return function (input) {
                var yesNo;
                if (input) {
                    yesNo = "yes";
                } else {
                    yesNo = "No";
                }
                return yesNo;
            };
        })

        .filter('nullFilter', function () {
            return function (input) {
                var Na;
                if (input == null) {
                    Na = "Not avaible";
                } else {
                    Na = input;
                }
                return Na;
            };
        });