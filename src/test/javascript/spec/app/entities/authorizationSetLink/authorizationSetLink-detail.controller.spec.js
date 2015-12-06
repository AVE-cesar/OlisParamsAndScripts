'use strict';

describe('AuthorizationSetLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAuthorizationSetLink, MockAGACAuthorization, MockAuthorizationSet, MockAGACUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAuthorizationSetLink = jasmine.createSpy('MockAuthorizationSetLink');
        MockAGACAuthorization = jasmine.createSpy('MockAGACAuthorization');
        MockAuthorizationSet = jasmine.createSpy('MockAuthorizationSet');
        MockAGACUser = jasmine.createSpy('MockAGACUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AuthorizationSetLink': MockAuthorizationSetLink,
            'AGACAuthorization': MockAGACAuthorization,
            'AuthorizationSet': MockAuthorizationSet,
            'AGACUser': MockAGACUser
        };
        createController = function() {
            $injector.get('$controller')("AuthorizationSetLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:authorizationSetLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
