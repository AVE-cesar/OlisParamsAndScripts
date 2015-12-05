'use strict';

describe('GraphicalComponentLink Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGraphicalComponentLink, MockPrompt, MockGraphicalComponent;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGraphicalComponentLink = jasmine.createSpy('MockGraphicalComponentLink');
        MockPrompt = jasmine.createSpy('MockPrompt');
        MockGraphicalComponent = jasmine.createSpy('MockGraphicalComponent');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GraphicalComponentLink': MockGraphicalComponentLink,
            'Prompt': MockPrompt,
            'GraphicalComponent': MockGraphicalComponent
        };
        createController = function() {
            $injector.get('$controller')("GraphicalComponentLinkDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:graphicalComponentLinkUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
