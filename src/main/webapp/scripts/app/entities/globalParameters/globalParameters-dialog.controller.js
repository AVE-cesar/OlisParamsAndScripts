'use strict';

angular.module('olisParamsAndScriptsApp').controller('GlobalParametersDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GlobalParameters',
        function($scope, $stateParams, $modalInstance, entity, GlobalParameters) {

        $scope.globalParameters = entity;
        $scope.load = function(id) {
            GlobalParameters.get({id : id}, function(result) {
                $scope.globalParameters = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:globalParametersUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.globalParameters.id != null) {
                GlobalParameters.update($scope.globalParameters, onSaveSuccess, onSaveError);
            } else {
                GlobalParameters.save($scope.globalParameters, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
