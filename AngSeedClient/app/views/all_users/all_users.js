'use strict';

angular.module('myApp.AllUsers', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/all_users', {
                    templateUrl: 'views/all_users/all_users.html',
                    controller: 'AllUsersCtrl'
                });
            }])

        .controller('AllUsersCtrl', function ($http) {
            var self = this;
            self.usersFound = false;
            self.users = [];
            $http.get("api/admin/users")
                    .success(function (data) {
                        self.users = data;
                self.usersFound = true;
                    }).error(function (data) {
                $rootScope.error = data.error + " : " + data.message;
            });

            self.deleteUser = function (data) {
                alert("Delete");
                $http({
                    method: 'PUT',
                    url: "api/admin/user",
                    data: {userName: data}
                });
            };
        });