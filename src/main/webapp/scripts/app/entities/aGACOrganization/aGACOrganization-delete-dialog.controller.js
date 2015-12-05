'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AGACOrganizationDeleteController', function($scope, $modalInstance, entity, AGACOrganization) {

        $scope.aGACOrganization = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AGACOrganization.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });