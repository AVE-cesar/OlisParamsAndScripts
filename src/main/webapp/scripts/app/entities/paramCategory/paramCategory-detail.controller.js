'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ParamCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, ParamCategory, GlobalParameter) {
        $scope.paramCategory = entity;
        $scope.load = function (id) {
            ParamCategory.get({id: id}, function(result) {
                $scope.paramCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:paramCategoryUpdate', function(event, result) {
            $scope.paramCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
