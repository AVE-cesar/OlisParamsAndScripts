'use strict';

describe('UserLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockUserLink, MockOrganization, MockAGACUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockUserLink = jasmine.createSpy('MockUserLink');
        MockOrganization = jasmine.createSpy('MockOrganization');
        MockAGACUser = jasmine.createSpy('MockAGACUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'UserLink': MockUserLink,
            'Organization': MockOrganization,
            'AGACUser': MockAGACUser
        };
        createController = function() {
            $injector.get('$controller')("UserLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:userLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
