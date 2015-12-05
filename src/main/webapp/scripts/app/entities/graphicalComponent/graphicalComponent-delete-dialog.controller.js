'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('GraphicalComponentDeleteController', function($scope, $modalInstance, entity, GraphicalComponent) {

        $scope.graphicalComponent = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GraphicalComponent.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });