describe('myApp.ExchangeRateInfo ExchangeRateController', function () {

    var scope, httpBackendMock, ctrl;
    var testResponse = {message: "Test Message Mock", serverTime: "10-23-2015 17:35:21"};

    var dailyRates = [
        {id: '1', dateField: 'nov 5, 2015', currency: {currencyCode: 'AUD', name: 'Australian dollars', dailyRates: []}, value: 489.67},
        {id: '2', dateField: 'nov 5, 2015', currency: {currencyCode: 'BGN', name: 'Bulgarian lev', dailyRates: []}, value: 381.39},
        {id: '3', dateField: 'nov 5, 2015', currency: {currencyCode: 'BRL', name: 'Brazilian real', dailyRates: []}, value: 180.33},
        {id: '4', dateField: 'nov 5, 2015', currency: {currencyCode: 'CAD', name: 'Canadian dollars', dailyRates: []}, value: 520.89}
    ];

    beforeEach(module('myApp.ExchangeRateInfo'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/currency/dailyrates').respond(dailyRates);
        scope = $rootScope.$new();
        ctrl = $controller('ExchangeRateController', {$scope: scope});
    }));

    it('Should fetch a test message', function () {
        expect(scope.info).toBeUndefined();
        ctrl.getDailyRates();
        httpBackendMock.flush();
        expect(ctrl.dailyRates[0].id).toEqual("1");
        expect(ctrl.dailyRates[0].dateField).toEqual("nov 5, 2015");
        expect(ctrl.dailyRates[0].currency.currencyCode).toEqual("AUD");
        expect(ctrl.dailyRates[0].currency.name).toEqual("Australian dollars");
        expect(ctrl.dailyRates[0].value).toEqual(489.67);

        expect(ctrl.dailyRates[3].id).toEqual("4");
        expect(ctrl.dailyRates[3].dateField).toEqual("nov 5, 2015");
        expect(ctrl.dailyRates[3].currency.currencyCode).toEqual("CAD");
        expect(ctrl.dailyRates[3].currency.name).toEqual("Canadian dollars");
        expect(ctrl.dailyRates[3].value).toEqual(520.89);
    });
});

