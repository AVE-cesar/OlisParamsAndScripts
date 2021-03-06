'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('DatasourcePositionDeleteController', function($scope, $modalInstance, entity, DatasourcePosition) {

        $scope.datasourcePosition = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DatasourcePosition.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });