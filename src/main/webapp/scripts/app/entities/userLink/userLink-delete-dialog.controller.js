'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('UserLinkDeleteController', function($scope, $modalInstance, entity, UserLink) {

        $scope.userLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });