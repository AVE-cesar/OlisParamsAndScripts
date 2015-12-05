'use strict';

describe('GlobalParameters Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGlobalParameters;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGlobalParameters = jasmine.createSpy('MockGlobalParameters');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GlobalParameters': MockGlobalParameters
        };
        createController = function() {
            $injector.get('$controller')("GlobalParametersDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:globalParametersUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
