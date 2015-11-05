'use strict'; 

describe('myApp.AllUsers AllUsersCtrl', function () {

    var scope, httpBackendMock, ctrl;
    var users = [
        {userName: "user", password: "test"},
        {userName: "admin", password: "test"},
        {userName: "auser_admin", password: "test"}
    ];
    var user = {userName: "user", password: "test"};

    beforeEach(module('myApp.AllUsers'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/admin/users').respond(users);
        scope = $rootScope.$new();
        ctrl = $controller('AllUsersCtrl', {$scope: scope});
    }));

    it('Should fetch a list of users', function () {
        expect(scope.info).toBeUndefined();
        httpBackendMock.flush();
//        console.info(scope);  
        expect(scope.data).toEqual([
            {userName: "user", password: "test"},
            {userName: "admin", password: "test"},
            {userName: "auser_admin", password: "test"}
        ]);
//        expect(scope.data.userName).toEqual("user");
    });
});