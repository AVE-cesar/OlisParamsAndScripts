'use strict';

describe('Prompt Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPrompt, MockPromptPosition, MockDatasourcePosition, MockGraphicalComponentLink, MockGraphicalComponentParam, MockCheckScript, MockParameterDependency;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPrompt = jasmine.createSpy('MockPrompt');
        MockPromptPosition = jasmine.createSpy('MockPromptPosition');
        MockDatasourcePosition = jasmine.createSpy('MockDatasourcePosition');
        MockGraphicalComponentLink = jasmine.createSpy('MockGraphicalComponentLink');
        MockGraphicalComponentParam = jasmine.createSpy('MockGraphicalComponentParam');
        MockCheckScript = jasmine.createSpy('MockCheckScript');
        MockParameterDependency = jasmine.createSpy('MockParameterDependency');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Prompt': MockPrompt,
            'PromptPosition': MockPromptPosition,
            'DatasourcePosition': MockDatasourcePosition,
            'GraphicalComponentLink': MockGraphicalComponentLink,
            'GraphicalComponentParam': MockGraphicalComponentParam,
            'CheckScript': MockCheckScript,
            'ParameterDependency': MockParameterDependency
        };
        createController = function() {
            $injector.get('$controller')("PromptDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:promptUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
