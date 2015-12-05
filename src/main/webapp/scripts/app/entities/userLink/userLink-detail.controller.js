'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('UserLinkDetailController', function ($scope, $rootScope, $stateParams, entity, UserLink, Organization, AGACUser) {
        $scope.userLink = entity;
        $scope.load = function (id) {
            UserLink.get({id: id}, function(result) {
                $scope.userLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:userLinkUpdate', function(event, result) {
            $scope.userLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
