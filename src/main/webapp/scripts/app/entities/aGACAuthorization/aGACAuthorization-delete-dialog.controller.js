'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AGACAuthorizationDeleteController', function($scope, $modalInstance, entity, AGACAuthorization) {

        $scope.aGACAuthorization = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AGACAuthorization.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });