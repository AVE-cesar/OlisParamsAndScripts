'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentParamDetailController', function ($scope, $rootScope, $stateParams, entity, GraphicalComponentParam, Prompt, GraphicalComponent) {
        $scope.graphicalComponentParam = entity;
        $scope.load = function (id) {
            GraphicalComponentParam.get({id: id}, function(result) {
                $scope.graphicalComponentParam = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:graphicalComponentParamUpdate', function(event, result) {
            $scope.graphicalComponentParam = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
