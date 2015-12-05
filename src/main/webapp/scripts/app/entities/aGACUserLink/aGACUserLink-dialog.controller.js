'use strict';

angular.module('olisParamsAndScriptsApp').controller('AGACUserLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AGACUserLink', 'AGACOrganization', 'AGACUser',
        function($scope, $stateParams, $modalInstance, entity, AGACUserLink, AGACOrganization, AGACUser) {

        $scope.aGACUserLink = entity;
        $scope.agacorganizations = AGACOrganization.query();
        $scope.agacusers = AGACUser.query();
        $scope.load = function(id) {
            AGACUserLink.get({id : id}, function(result) {
                $scope.aGACUserLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:aGACUserLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aGACUserLink.id != null) {
                AGACUserLink.update($scope.aGACUserLink, onSaveSuccess, onSaveError);
            } else {
                AGACUserLink.save($scope.aGACUserLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
