'use strict';

describe('PromptPosition Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPromptPosition, MockReport, MockPrompt;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPromptPosition = jasmine.createSpy('MockPromptPosition');
        MockReport = jasmine.createSpy('MockReport');
        MockPrompt = jasmine.createSpy('MockPrompt');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PromptPosition': MockPromptPosition,
            'Report': MockReport,
            'Prompt': MockPrompt
        };
        createController = function() {
            $injector.get('$controller')("PromptPositionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:promptPositionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
