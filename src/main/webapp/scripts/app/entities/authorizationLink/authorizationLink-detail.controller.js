'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationLinkDetailController', function ($scope, $rootScope, $stateParams, entity, AuthorizationLink, AuthorizationSet) {
        $scope.authorizationLink = entity;
        $scope.load = function (id) {
            AuthorizationLink.get({id: id}, function(result) {
                $scope.authorizationLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:authorizationLinkUpdate', function(event, result) {
            $scope.authorizationLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
