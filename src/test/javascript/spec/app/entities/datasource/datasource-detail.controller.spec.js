'use strict';

describe('Datasource Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDatasource, MockDatasourcePosition;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDatasource = jasmine.createSpy('MockDatasource');
        MockDatasourcePosition = jasmine.createSpy('MockDatasourcePosition');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Datasource': MockDatasource,
            'DatasourcePosition': MockDatasourcePosition
        };
        createController = function() {
            $injector.get('$controller')("DatasourceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'olisParamsAndScriptsApp:datasourceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
