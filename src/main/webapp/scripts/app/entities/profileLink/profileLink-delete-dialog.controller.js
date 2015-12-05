'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('ProfileLinkDeleteController', function($scope, $modalInstance, entity, ProfileLink) {

        $scope.profileLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProfileLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });