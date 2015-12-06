'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationSetDetailController', function ($scope, $rootScope, $stateParams, entity, AuthorizationSet, AuthorizationSetLink, AuthorizationLink) {
        $scope.authorizationSet = entity;
        $scope.load = function (id) {
            AuthorizationSet.get({id: id}, function(result) {
                $scope.authorizationSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:authorizationSetUpdate', function(event, result) {
            $scope.authorizationSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
