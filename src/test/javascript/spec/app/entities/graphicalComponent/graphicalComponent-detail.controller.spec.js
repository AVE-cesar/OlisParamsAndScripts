'use strict';

describe('GraphicalComponent Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockGraphicalComponent, MockGraphicalComponentLink, MockGraphicalComponentParam;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockGraphicalComponent = jasmine.createSpy('MockGraphicalComponent');
        MockGraphicalComponentLink = jasmine.createSpy('MockGraphicalComponentLink');
        MockGraphicalComponentParam = jasmine.createSpy('MockGraphicalComponentParam');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'GraphicalComponent': MockGraphicalComponent,
            'GraphicalComponentLink': MockGraphicalComponentLink,
            'GraphicalComponentParam': MockGraphicalComponentParam
        };
        createController = function() {
            $injector.get('$controller')("GraphicalComponentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:graphicalComponentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
