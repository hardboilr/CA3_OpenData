'use strict';

angular.module('myApp.CreateUser', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/create_user', {
                    templateUrl: 'views/create_user/create_user.html',
                    controller: 'CreateUserCtrl'
                });
            }])

        .controller('CreateUserCtrl', function ($http, $rootScope) {
            var self = this;
            self.user = {};
            self.createUser = function () {
                $http.post("api/create", self.user)
                        .success(function (data) {
                            $rootScope.success = "User " + data.userName + " succesfullly created!";
                        })
                        .error(function () {
                            $rootScope.error = "A user with that name already exist";
                        });
            };
        });
