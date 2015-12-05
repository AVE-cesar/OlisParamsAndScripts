'use strict';

describe('ParamCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockParamCategory, MockGlobalParameter;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockParamCategory = jasmine.createSpy('MockParamCategory');
        MockGlobalParameter = jasmine.createSpy('MockGlobalParameter');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ParamCategory': MockParamCategory,
            'GlobalParameter': MockGlobalParameter
        };
        createController = function() {
            $injector.get('$controller')("ParamCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:paramCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
