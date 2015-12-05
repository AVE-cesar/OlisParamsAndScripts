'use strict';

angular.module('olisParamsAndScriptsApp').controller('GlobalParameterDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GlobalParameter', 'ParamCategory',
        function($scope, $stateParams, $modalInstance, entity, GlobalParameter, ParamCategory) {

        $scope.globalParameter = entity;
        $scope.paramcategorys = ParamCategory.query();
        $scope.load = function(id) {
            GlobalParameter.get({id : id}, function(result) {
                $scope.globalParameter = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:globalParameterUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.globalParameter.id != null) {
                GlobalParameter.update($scope.globalParameter, onSaveSuccess, onSaveError);
            } else {
                GlobalParameter.save($scope.globalParameter, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
