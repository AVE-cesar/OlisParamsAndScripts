'use strict';

angular.module('olisParamsAndScriptsApp').controller('ParameterDependencyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ParameterDependency', 'Prompt',
        function($scope, $stateParams, $modalInstance, entity, ParameterDependency, Prompt) {

        $scope.parameterDependency = entity;
        $scope.prompts = Prompt.query();
        $scope.load = function(id) {
            ParameterDependency.get({id : id}, function(result) {
                $scope.parameterDependency = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:parameterDependencyUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.parameterDependency.id != null) {
                ParameterDependency.update($scope.parameterDependency, onSaveSuccess, onSaveError);
            } else {
                ParameterDependency.save($scope.parameterDependency, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
