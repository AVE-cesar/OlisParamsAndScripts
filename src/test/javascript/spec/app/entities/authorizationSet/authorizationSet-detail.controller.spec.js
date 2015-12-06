'use strict';

describe('AuthorizationSet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAuthorizationSet, MockAuthorizationSetLink, MockAuthorizationLink;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAuthorizationSet = jasmine.createSpy('MockAuthorizationSet');
        MockAuthorizationSetLink = jasmine.createSpy('MockAuthorizationSetLink');
        MockAuthorizationLink = jasmine.createSpy('MockAuthorizationLink');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AuthorizationSet': MockAuthorizationSet,
            'AuthorizationSetLink': MockAuthorizationSetLink,
            'AuthorizationLink': MockAuthorizationLink
        };
        createController = function() {
            $injector.get('$controller')("AuthorizationSetDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:authorizationSetUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
