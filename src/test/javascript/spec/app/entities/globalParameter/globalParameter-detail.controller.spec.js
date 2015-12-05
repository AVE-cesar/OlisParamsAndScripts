'use strict';

describe('GlobalParameter Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGlobalParameter, MockParamCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGlobalParameter = jasmine.createSpy('MockGlobalParameter');
        MockParamCategory = jasmine.createSpy('MockParamCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GlobalParameter': MockGlobalParameter,
            'ParamCategory': MockParamCategory
        };
        createController = function() {
            $injector.get('$controller')("GlobalParameterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:globalParameterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
