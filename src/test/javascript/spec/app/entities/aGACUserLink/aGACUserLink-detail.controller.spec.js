'use strict';

describe('AGACUserLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAGACUserLink, MockAGACOrganization, MockAGACUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAGACUserLink = jasmine.createSpy('MockAGACUserLink');
        MockAGACOrganization = jasmine.createSpy('MockAGACOrganization');
        MockAGACUser = jasmine.createSpy('MockAGACUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AGACUserLink': MockAGACUserLink,
            'AGACOrganization': MockAGACOrganization,
            'AGACUser': MockAGACUser
        };
        createController = function() {
            $injector.get('$controller')("AGACUserLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:aGACUserLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
