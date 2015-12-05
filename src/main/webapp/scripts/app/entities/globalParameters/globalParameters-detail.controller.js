'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GlobalParametersDetailController', function ($scope, $rootScope, $stateParams, entity, GlobalParameters) {
        $scope.globalParameters = entity;
        $scope.load = function (id) {
            GlobalParameters.get({id: id}, function(result) {
                $scope.globalParameters = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:globalParametersUpdate', function(event, result) {
            $scope.globalParameters = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
