'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('PromptPositionDetailController', function ($scope, $rootScope, $stateParams, entity, PromptPosition, Report, Prompt) {
        $scope.promptPosition = entity;
        $scope.load = function (id) {
            PromptPosition.get({id: id}, function(result) {
                $scope.promptPosition = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:promptPositionUpdate', function(event, result) {
            $scope.promptPosition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
