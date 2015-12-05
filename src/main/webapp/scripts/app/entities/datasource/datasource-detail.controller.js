'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('DatasourceDetailController', function ($scope, $rootScope, $stateParams, entity, Datasource, DatasourcePosition) {
        $scope.datasource = entity;
        $scope.load = function (id) {
            Datasource.get({id: id}, function(result) {
                $scope.datasource = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:datasourceUpdate', function(event, result) {
            $scope.datasource = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
