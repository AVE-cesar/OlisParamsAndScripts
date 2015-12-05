'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('ParamCategoryDeleteController', function($scope, $modalInstance, entity, ParamCategory) {

        $scope.paramCategory = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ParamCategory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });