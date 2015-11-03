'use strict';

angular.module('myApp.createUser', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/createUser', {
                    templateUrl: 'createUser/createUser.html',
                    controller: 'CreateUserController'
                });
            }])

        .controller('CreateUserController', function ($http) {
            var self = this;
            self.createUser = function (user) {
                $http.post("api/create", user);
            };
        });