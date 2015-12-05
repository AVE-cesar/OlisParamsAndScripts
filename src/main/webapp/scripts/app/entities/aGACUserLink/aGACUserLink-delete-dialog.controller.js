'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AGACUserLinkDeleteController', function($scope, $modalInstance, entity, AGACUserLink) {

        $scope.aGACUserLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AGACUserLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });