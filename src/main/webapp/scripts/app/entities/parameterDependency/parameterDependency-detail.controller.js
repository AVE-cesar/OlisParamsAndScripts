'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ParameterDependencyDetailController', function ($scope, $rootScope, $stateParams, entity, ParameterDependency, Prompt) {
        $scope.parameterDependency = entity;
        $scope.load = function (id) {
            ParameterDependency.get({id: id}, function(result) {
                $scope.parameterDependency = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:parameterDependencyUpdate', function(event, result) {
            $scope.parameterDependency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
