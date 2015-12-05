'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('PromptDeleteController', function($scope, $modalInstance, entity, Prompt) {

        $scope.prompt = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Prompt.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });