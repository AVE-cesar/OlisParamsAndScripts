'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACUserDetailController', function ($scope, $rootScope, $stateParams, entity, AGACUser, AuthorizationSetLink, AGACUserLink) {
        $scope.aGACUser = entity;
        $scope.load = function (id) {
            AGACUser.get({id: id}, function(result) {
                $scope.aGACUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:aGACUserUpdate', function(event, result) {
            $scope.aGACUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
