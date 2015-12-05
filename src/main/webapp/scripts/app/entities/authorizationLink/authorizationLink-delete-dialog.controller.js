'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AuthorizationLinkDeleteController', function($scope, $modalInstance, entity, AuthorizationLink) {

        $scope.authorizationLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AuthorizationLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });