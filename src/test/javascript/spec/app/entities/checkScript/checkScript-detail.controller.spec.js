'use strict';

describe('CheckScript Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCheckScript, MockPrompt;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCheckScript = jasmine.createSpy('MockCheckScript');
        MockPrompt = jasmine.createSpy('MockPrompt');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CheckScript': MockCheckScript,
            'Prompt': MockPrompt
        };
        createController = function() {
            $injector.get('$controller')("CheckScriptDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:checkScriptUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
