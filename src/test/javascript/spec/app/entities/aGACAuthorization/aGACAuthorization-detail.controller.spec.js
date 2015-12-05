'use strict';

describe('AGACAuthorization Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAGACAuthorization, MockAuthorizationSetLink;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAGACAuthorization = jasmine.createSpy('MockAGACAuthorization');
        MockAuthorizationSetLink = jasmine.createSpy('MockAuthorizationSetLink');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AGACAuthorization': MockAGACAuthorization,
            'AuthorizationSetLink': MockAuthorizationSetLink
        };
        createController = function() {
            $injector.get('$controller')("AGACAuthorizationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:aGACAuthorizationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
