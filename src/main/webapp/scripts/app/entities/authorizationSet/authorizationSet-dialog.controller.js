'use strict';

angular.module('olisParamsAndScriptsApp').controller('AuthorizationSetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AuthorizationSet', 'AuthorizationLink', 'AuthorizationSetLink',
        function($scope, $stateParams, $modalInstance, entity, AuthorizationSet, AuthorizationLink, AuthorizationSetLink) {

        $scope.authorizationSet = entity;
        $scope.authorizationlinks = AuthorizationLink.query();
        $scope.authorizationsetlinks = AuthorizationSetLink.query();
        $scope.load = function(id) {
            AuthorizationSet.get({id : id}, function(result) {
                $scope.authorizationSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:authorizationSetUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.authorizationSet.id != null) {
                AuthorizationSet.update($scope.authorizationSet, onSaveSuccess, onSaveError);
            } else {
                AuthorizationSet.save($scope.authorizationSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
