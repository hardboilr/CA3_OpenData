'use strict'; 

describe('myApp.CreateUser CreateUserCtrl', function () {

    var scope, httpBackendMock, ctrl;
    var user = {userName: "Ole", password: "test"};
    var response = {userName: "Ole", password: "test", roles: ["User"]};
    beforeEach(module('myApp.CreateUser'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.when('POST', 'api/create',user).respond(201, response);
        scope = $rootScope.$new();
        ctrl = $controller('CreateUserCtrl', {$scope: scope});
    }));
    
    it('Should fetch a status code 201', function () {
     httpBackendMock.expectPOST('api/create',user);
    
    });
});