'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACUserLinkDetailController', function ($scope, $rootScope, $stateParams, entity, AGACUserLink, AGACOrganization, AGACUser) {
        $scope.aGACUserLink = entity;
        $scope.load = function (id) {
            AGACUserLink.get({id: id}, function(result) {
                $scope.aGACUserLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:aGACUserLinkUpdate', function(event, result) {
            $scope.aGACUserLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
