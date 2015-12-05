'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('GlobalParameterDeleteController', function($scope, $modalInstance, entity, GlobalParameter) {

        $scope.globalParameter = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GlobalParameter.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });