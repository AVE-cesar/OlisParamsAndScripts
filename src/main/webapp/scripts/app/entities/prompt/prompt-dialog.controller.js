'use strict';

angular.module('olisParamsAndScriptsApp').controller('PromptDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Prompt', 'PromptPosition', 'DatasourcePosition', 'GraphicalComponentLink', 'GraphicalComponentParam', 'CheckScript', 'ParameterDependency',
        function($scope, $stateParams, $modalInstance, entity, Prompt, PromptPosition, DatasourcePosition, GraphicalComponentLink, GraphicalComponentParam, CheckScript, ParameterDependency) {

        $scope.prompt = entity;
        $scope.promptpositions = PromptPosition.query();
        $scope.datasourcepositions = DatasourcePosition.query();
        $scope.graphicalcomponentlinks = GraphicalComponentLink.query();
        $scope.graphicalcomponentparams = GraphicalComponentParam.query();
        $scope.checkscripts = CheckScript.query();
        $scope.parameterdependencys = ParameterDependency.query();
        $scope.load = function(id) {
            Prompt.get({id : id}, function(result) {
                $scope.prompt = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:promptUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.prompt.id != null) {
                Prompt.update($scope.prompt, onSaveSuccess, onSaveError);
            } else {
                Prompt.save($scope.prompt, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
