'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('CheckScriptDetailController', function ($scope, $rootScope, $stateParams, entity, CheckScript, Prompt) {
        $scope.checkScript = entity;
        $scope.load = function (id) {
            CheckScript.get({id: id}, function(result) {
                $scope.checkScript = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:checkScriptUpdate', function(event, result) {
            $scope.checkScript = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
