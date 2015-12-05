'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentDetailController', function ($scope, $rootScope, $stateParams, entity, GraphicalComponent, GraphicalComponentLink, GraphicalComponentParam) {
        $scope.graphicalComponent = entity;
        $scope.load = function (id) {
            GraphicalComponent.get({id: id}, function(result) {
                $scope.graphicalComponent = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:graphicalComponentUpdate', function(event, result) {
            $scope.graphicalComponent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
