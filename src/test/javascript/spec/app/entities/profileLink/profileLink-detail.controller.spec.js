'use strict';

describe('ProfileLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProfileLink, MockAGACUser, MockProfile;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProfileLink = jasmine.createSpy('MockProfileLink');
        MockAGACUser = jasmine.createSpy('MockAGACUser');
        MockProfile = jasmine.createSpy('MockProfile');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProfileLink': MockProfileLink,
            'AGACUser': MockAGACUser,
            'Profile': MockProfile
        };
        createController = function() {
            $injector.get('$controller')("ProfileLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:profileLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
