'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentLinkDetailController', function ($scope, $rootScope, $stateParams, entity, GraphicalComponentLink, Prompt, GraphicalComponent) {
        $scope.graphicalComponentLink = entity;
        $scope.load = function (id) {
            GraphicalComponentLink.get({id: id}, function(result) {
                $scope.graphicalComponentLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:graphicalComponentLinkUpdate', function(event, result) {
            $scope.graphicalComponentLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
