'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('DatasourcePositionDetailController', function ($scope, $rootScope, $stateParams, entity, DatasourcePosition, Prompt, Datasource) {
        $scope.datasourcePosition = entity;
        $scope.load = function (id) {
            DatasourcePosition.get({id: id}, function(result) {
                $scope.datasourcePosition = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:datasourcePositionUpdate', function(event, result) {
            $scope.datasourcePosition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
