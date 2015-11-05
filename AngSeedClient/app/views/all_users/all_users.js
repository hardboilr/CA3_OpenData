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
            self.users = [];
            $http.get("http://localhost:8080/AngSeedServer/api/admin/users")
                    .success(function (data) {
                        self.users = data;
                    }).error(function (data) {
                $rootScope.error = data.error + " : " + data.message;
            });
            
            self.deleteUser = function(data) {
                alert("Delete");
                $http({
                    method: 'PUT',
                    url: "http://localhost:8080/AngSeedServer/api/admin/user",
                    data: {userName: data}
                });
            };
        });