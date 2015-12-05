'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('PromptPositionDeleteController', function($scope, $modalInstance, entity, PromptPosition) {

        $scope.promptPosition = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PromptPosition.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });