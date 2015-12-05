'use strict';

angular.module('olisParamsAndScriptsApp').controller('GraphicalComponentParamDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GraphicalComponentParam', 'Prompt', 'GraphicalComponent',
        function($scope, $stateParams, $modalInstance, entity, GraphicalComponentParam, Prompt, GraphicalComponent) {

        $scope.graphicalComponentParam = entity;
        $scope.prompts = Prompt.query();
        $scope.graphicalcomponents = GraphicalComponent.query();
        $scope.load = function(id) {
            GraphicalComponentParam.get({id : id}, function(result) {
                $scope.graphicalComponentParam = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:graphicalComponentParamUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.graphicalComponentParam.id != null) {
                GraphicalComponentParam.update($scope.graphicalComponentParam, onSaveSuccess, onSaveError);
            } else {
                GraphicalComponentParam.save($scope.graphicalComponentParam, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
