'use strict';

describe('GraphicalComponentParam Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGraphicalComponentParam, MockPrompt, MockGraphicalComponent;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGraphicalComponentParam = jasmine.createSpy('MockGraphicalComponentParam');
        MockPrompt = jasmine.createSpy('MockPrompt');
        MockGraphicalComponent = jasmine.createSpy('MockGraphicalComponent');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GraphicalComponentParam': MockGraphicalComponentParam,
            'Prompt': MockPrompt,
            'GraphicalComponent': MockGraphicalComponent
        };
        createController = function() {
            $injector.get('$controller')("GraphicalComponentParamDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:graphicalComponentParamUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
