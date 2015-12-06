'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationSetLinkDetailController', function ($scope, $rootScope, $stateParams, entity, AuthorizationSetLink, AGACAuthorization, AuthorizationSet, AGACUser) {
        $scope.authorizationSetLink = entity;
        $scope.load = function (id) {
            AuthorizationSetLink.get({id: id}, function(result) {
                $scope.authorizationSetLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:authorizationSetLinkUpdate', function(event, result) {
            $scope.authorizationSetLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
