'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('DatasourceDeleteController', function($scope, $modalInstance, entity, Datasource) {

        $scope.datasource = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Datasource.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });