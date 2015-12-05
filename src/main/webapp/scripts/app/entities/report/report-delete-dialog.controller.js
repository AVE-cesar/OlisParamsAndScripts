'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('ReportDeleteController', function($scope, $modalInstance, entity, Report) {

        $scope.report = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Report.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });