'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AGACUserDeleteController', function($scope, $modalInstance, entity, AGACUser) {

        $scope.aGACUser = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AGACUser.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });