'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GlobalParameterDetailController', function ($scope, $rootScope, $stateParams, entity, GlobalParameter, ParamCategory) {
        $scope.globalParameter = entity;
        $scope.load = function (id) {
            GlobalParameter.get({id: id}, function(result) {
                $scope.globalParameter = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:globalParameterUpdate', function(event, result) {
            $scope.globalParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
