'use strict';

describe('myApp.CompanyInfo CompanyInfoCtrl', function () {

    var scope, httpBackendMock, ctrl;
//    var testResponse = {"vat":"31678021","name":"EA Copenhagen Business","address":"Landem\u00e6rket 11, 5","zipcode":"1119","city":"K\u00f8benhavn K","protected":false,"phone":"36154500","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"01\/10 - 2008","enddate":null,"employees":"200 - 499","addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau","companycode":280,"companydesc":"\u00d8vrige virksomhedsformer","creditstartdate":null,"creditbankrupt":false,"owners":null,"productionunits":[{"pno":1015222189,"main":false,"name":"Cphbusiness Lyngby","address":"N\u00f8rgaardsvej 30","zipcode":"2800","city":"Kgs. Lyngby","protected":false,"phone":"36154504","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"27\/04 - 2009","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1015222235,"main":false,"name":"Cphbusiness N\u00f8rrebro","address":"Bl\u00e5g\u00e5rdsgade 23B","zipcode":"2200","city":"K\u00f8benhavn N","protected":false,"phone":"36154500","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"27\/04 - 2009","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1015222227,"main":false,"name":"Cphbusiness City","address":"Landem\u00e6rket 11","zipcode":"1119","city":"K\u00f8benhavn K","protected":false,"phone":"36154512","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"27\/04 - 2009","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1014765472,"main":true,"name":"Copenhagen Business Academy","address":"Landem\u00e6rket 11, 5","zipcode":"1119","city":"K\u00f8benhavn K","protected":false,"phone":"36154500","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"01\/10 - 2008","enddate":null,"employees":"200 - 499","addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1015222197,"main":false,"name":"Cphbusiness Laboratorie og Milj\u00f8","address":"Peder Oxes Alle 4","zipcode":"3400","city":"Hiller\u00f8d","protected":false,"phone":"36154516","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"27\/04 - 2009","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1015222200,"main":false,"name":"Cphbusiness S\u00f8erne","address":"Nansensgade 19","zipcode":"1366","city":"K\u00f8benhavn K","protected":false,"phone":"36154513","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"27\/04 - 2009","enddate":null,"employees":"0","addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1018494805,"main":false,"name":"Smart Learning","address":"Nansensgade 19","zipcode":"1366","city":"K\u00f8benhavn K","protected":false,"phone":"36154509","email":"info@smartlearning.dk","fax":null,"startdate":"17\/05 - 2013","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1018679740,"main":false,"name":"Cphbusiness Lyngby (Lundtoftevej)","address":"Lundtoftevej 93","zipcode":"2800","city":"Kgs. Lyngby","protected":false,"phone":"36154505","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"12\/08 - 2013","enddate":"06\/08 - 2014","employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"},{"pno":1019598469,"main":false,"name":"Cphbusiness Partner","address":"Nansensgade 19","zipcode":"1366","city":"K\u00f8benhavn K","protected":false,"phone":"36154500","email":"kontakt@cphbusiness.dk","fax":null,"startdate":"01\/08 - 2014","enddate":null,"employees":null,"addressco":null,"industrycode":854100,"industrydesc":"Videreg\u00e5ende uddannelser ikke p\u00e5 universitetsniveau"}],"t":100,"version":6};
    var testResponse = {vat: "31678021", zipcode: "1119"};
//    var testResponse = {message: "Test Message Mock", serverTime: "10-23-2015 17:35:21"};
//    var testResponse = {error: "INVALID_VAT", message: "The VAT number is not valid.", t: "1", version: "6"};

    beforeEach(module('myApp.CompanyInfo'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/search/vat/31678021/dk').respond(testResponse);
        scope = $rootScope.$new();
        ctrl = $controller('CompanyInfoCtrl', {$scope: scope});
    }));

    it('Should fetch a company based on a given vat nr', function () {
        expect(scope.info).toBeUndefined();
        ctrl.search();
        httpBackendMock.flush();
//        console.log(scope);
        expect(scope.vat).toEqual("31678021");
        expect(scope.zipcode).toEqual("1119");
//        expect(scope.data.error).toEqual("INVALID_VAT");
    });
});