'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('CheckScriptDeleteController', function($scope, $modalInstance, entity, CheckScript) {

        $scope.checkScript = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CheckScript.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });