'use strict';

angular.module('olisParamsAndScriptsApp').controller('AuthorizationLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AuthorizationLink', 'AuthorizationSet',
        function($scope, $stateParams, $modalInstance, entity, AuthorizationLink, AuthorizationSet) {

        $scope.authorizationLink = entity;
        $scope.authorizationsets = AuthorizationSet.query();
        $scope.load = function(id) {
            AuthorizationLink.get({id : id}, function(result) {
                $scope.authorizationLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:authorizationLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.authorizationLink.id != null) {
                AuthorizationLink.update($scope.authorizationLink, onSaveSuccess, onSaveError);
            } else {
                AuthorizationLink.save($scope.authorizationLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
