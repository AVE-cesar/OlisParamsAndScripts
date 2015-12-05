'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('GlobalParametersDeleteController', function($scope, $modalInstance, entity, GlobalParameters) {

        $scope.globalParameters = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GlobalParameters.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });