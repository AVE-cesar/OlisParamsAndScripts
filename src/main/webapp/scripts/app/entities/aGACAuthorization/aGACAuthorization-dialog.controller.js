'use strict';

angular.module('olisParamsAndScriptsApp').controller('AGACAuthorizationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AGACAuthorization', 'AuthorizationLink',
        function($scope, $stateParams, $modalInstance, entity, AGACAuthorization, AuthorizationLink) {

        $scope.aGACAuthorization = entity;
        $scope.authorizationlinks = AuthorizationLink.query();
        $scope.load = function(id) {
            AGACAuthorization.get({id : id}, function(result) {
                $scope.aGACAuthorization = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:aGACAuthorizationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aGACAuthorization.id != null) {
                AGACAuthorization.update($scope.aGACAuthorization, onSaveSuccess, onSaveError);
            } else {
                AGACAuthorization.save($scope.aGACAuthorization, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
