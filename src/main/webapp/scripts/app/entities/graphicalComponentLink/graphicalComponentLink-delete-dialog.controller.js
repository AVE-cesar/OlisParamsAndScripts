'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('GraphicalComponentLinkDeleteController', function($scope, $modalInstance, entity, GraphicalComponentLink) {

        $scope.graphicalComponentLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GraphicalComponentLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });