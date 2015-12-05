'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('ParameterDependencyDeleteController', function($scope, $modalInstance, entity, ParameterDependency) {

        $scope.parameterDependency = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ParameterDependency.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });