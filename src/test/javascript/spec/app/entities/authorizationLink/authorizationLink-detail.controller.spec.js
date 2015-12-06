'use strict';

describe('AuthorizationLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAuthorizationLink, MockAGACAuthorization, MockAuthorizationSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAuthorizationLink = jasmine.createSpy('MockAuthorizationLink');
        MockAGACAuthorization = jasmine.createSpy('MockAGACAuthorization');
        MockAuthorizationSet = jasmine.createSpy('MockAuthorizationSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AuthorizationLink': MockAuthorizationLink,
            'AGACAuthorization': MockAGACAuthorization,
            'AuthorizationSet': MockAuthorizationSet
        };
        createController = function() {
            $injector.get('$controller')("AuthorizationLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:authorizationLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
