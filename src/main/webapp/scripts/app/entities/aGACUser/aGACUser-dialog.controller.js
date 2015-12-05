'use strict';

angular.module('olisParamsAndScriptsApp').controller('AGACUserDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AGACUser', 'AuthorizationSetLink', 'AGACUserLink',
        function($scope, $stateParams, $modalInstance, entity, AGACUser, AuthorizationSetLink, AGACUserLink) {

        $scope.aGACUser = entity;
        $scope.authorizationsetlinks = AuthorizationSetLink.query();
        $scope.agacuserlinks = AGACUserLink.query();
        $scope.load = function(id) {
            AGACUser.get({id : id}, function(result) {
                $scope.aGACUser = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:aGACUserUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aGACUser.id != null) {
                AGACUser.update($scope.aGACUser, onSaveSuccess, onSaveError);
            } else {
                AGACUser.save($scope.aGACUser, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
