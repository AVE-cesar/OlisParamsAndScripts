'use strict';

angular.module('olisParamsAndScriptsApp').controller('DatasourcePositionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DatasourcePosition', 'Prompt', 'Datasource',
        function($scope, $stateParams, $modalInstance, entity, DatasourcePosition, Prompt, Datasource) {

        $scope.datasourcePosition = entity;
        $scope.prompts = Prompt.query();
        $scope.datasources = Datasource.query();
        $scope.load = function(id) {
            DatasourcePosition.get({id : id}, function(result) {
                $scope.datasourcePosition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:datasourcePositionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.datasourcePosition.id != null) {
                DatasourcePosition.update($scope.datasourcePosition, onSaveSuccess, onSaveError);
            } else {
                DatasourcePosition.save($scope.datasourcePosition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
