'use strict';

describe('DatasourcePosition Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDatasourcePosition, MockPrompt, MockDatasource;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDatasourcePosition = jasmine.createSpy('MockDatasourcePosition');
        MockPrompt = jasmine.createSpy('MockPrompt');
        MockDatasource = jasmine.createSpy('MockDatasource');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DatasourcePosition': MockDatasourcePosition,
            'Prompt': MockPrompt,
            'Datasource': MockDatasource
        };
        createController = function() {
            $injector.get('$controller')("DatasourcePositionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:datasourcePositionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
