'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('GraphicalComponentParamDeleteController', function($scope, $modalInstance, entity, GraphicalComponentParam) {

        $scope.graphicalComponentParam = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GraphicalComponentParam.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });