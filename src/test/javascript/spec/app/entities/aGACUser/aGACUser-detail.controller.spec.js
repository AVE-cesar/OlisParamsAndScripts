'use strict';

describe('AGACUser Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAGACUser, MockAuthorizationSetLink, MockAGACUserLink;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAGACUser = jasmine.createSpy('MockAGACUser');
        MockAuthorizationSetLink = jasmine.createSpy('MockAuthorizationSetLink');
        MockAGACUserLink = jasmine.createSpy('MockAGACUserLink');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AGACUser': MockAGACUser,
            'AuthorizationSetLink': MockAuthorizationSetLink,
            'AGACUserLink': MockAGACUserLink
        };
        createController = function() {
            $injector.get('$controller')("AGACUserDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:aGACUserUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
