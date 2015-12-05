'use strict';

describe('ParameterDependency Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockParameterDependency, MockPrompt;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockParameterDependency = jasmine.createSpy('MockParameterDependency');
        MockPrompt = jasmine.createSpy('MockPrompt');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ParameterDependency': MockParameterDependency,
            'Prompt': MockPrompt
        };
        createController = function() {
            $injector.get('$controller')("ParameterDependencyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:parameterDependencyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
