'use strict';

angular.module('myApp.Documentation', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/documenation', {
              templateUrl: 'views/documentation/documentation.html',
              controller: 'DocumentationCtrl'
            });
          }])

        .controller('DocumentationCtrl', function () {

        });