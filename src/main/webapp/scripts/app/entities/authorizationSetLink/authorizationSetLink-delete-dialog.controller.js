'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AuthorizationSetLinkDeleteController', function($scope, $modalInstance, entity, AuthorizationSetLink) {

        $scope.authorizationSetLink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AuthorizationSetLink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });