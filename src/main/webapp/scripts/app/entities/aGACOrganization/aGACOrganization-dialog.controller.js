'use strict';

angular.module('olisParamsAndScriptsApp').controller('AGACOrganizationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AGACOrganization', 'AGACUserLink',
        function($scope, $stateParams, $modalInstance, entity, AGACOrganization, AGACUserLink) {

        $scope.aGACOrganization = entity;
        $scope.agacuserlinks = AGACUserLink.query();
        $scope.load = function(id) {
            AGACOrganization.get({id : id}, function(result) {
                $scope.aGACOrganization = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:aGACOrganizationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aGACOrganization.id != null) {
                AGACOrganization.update($scope.aGACOrganization, onSaveSuccess, onSaveError);
            } else {
                AGACOrganization.save($scope.aGACOrganization, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
