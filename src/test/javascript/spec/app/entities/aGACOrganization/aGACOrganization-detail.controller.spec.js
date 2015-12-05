'use strict';

describe('AGACOrganization Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAGACOrganization, MockAGACUserLink;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAGACOrganization = jasmine.createSpy('MockAGACOrganization');
        MockAGACUserLink = jasmine.createSpy('MockAGACUserLink');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AGACOrganization': MockAGACOrganization,
            'AGACUserLink': MockAGACUserLink
        };
        createController = function() {
            $injector.get('$controller')("AGACOrganizationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:aGACOrganizationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
