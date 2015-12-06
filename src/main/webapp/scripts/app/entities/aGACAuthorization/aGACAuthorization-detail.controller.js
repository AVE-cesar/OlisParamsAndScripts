'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACAuthorizationDetailController', function ($scope, $rootScope, $stateParams, entity, AGACAuthorization, AuthorizationLink) {
        $scope.aGACAuthorization = entity;
        $scope.load = function (id) {
            AGACAuthorization.get({id: id}, function(result) {
                $scope.aGACAuthorization = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:aGACAuthorizationUpdate', function(event, result) {
            $scope.aGACAuthorization = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
