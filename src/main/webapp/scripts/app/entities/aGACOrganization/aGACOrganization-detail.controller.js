'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACOrganizationDetailController', function ($scope, $rootScope, $stateParams, entity, AGACOrganization, AGACUserLink) {
        $scope.aGACOrganization = entity;
        $scope.load = function (id) {
            AGACOrganization.get({id: id}, function(result) {
                $scope.aGACOrganization = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:aGACOrganizationUpdate', function(event, result) {
            $scope.aGACOrganization = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
