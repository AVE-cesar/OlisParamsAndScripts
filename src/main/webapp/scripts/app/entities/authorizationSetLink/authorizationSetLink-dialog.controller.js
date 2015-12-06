'use strict';

angular.module('olisParamsAndScriptsApp').controller('AuthorizationSetLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AuthorizationSetLink', 'AuthorizationSet', 'AGACUser',
        function($scope, $stateParams, $modalInstance, entity, AuthorizationSetLink, AuthorizationSet, AGACUser) {

        $scope.authorizationSetLink = entity;
        $scope.authorizationsets = AuthorizationSet.query();
        $scope.agacusers = AGACUser.query();
        $scope.load = function(id) {
            AuthorizationSetLink.get({id : id}, function(result) {
                $scope.authorizationSetLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:authorizationSetLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.authorizationSetLink.id != null) {
                AuthorizationSetLink.update($scope.authorizationSetLink, onSaveSuccess, onSaveError);
            } else {
                AuthorizationSetLink.save($scope.authorizationSetLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
