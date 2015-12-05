'use strict';

angular.module('olisParamsAndScriptsApp')
	.controller('AuthorizationSetDeleteController', function($scope, $modalInstance, entity, AuthorizationSet) {

        $scope.authorizationSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AuthorizationSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });