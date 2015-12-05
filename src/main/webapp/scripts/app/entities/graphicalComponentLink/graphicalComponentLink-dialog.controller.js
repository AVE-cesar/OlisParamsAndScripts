'use strict';

angular.module('olisParamsAndScriptsApp').controller('GraphicalComponentLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GraphicalComponentLink', 'Prompt', 'GraphicalComponent',
        function($scope, $stateParams, $modalInstance, entity, GraphicalComponentLink, Prompt, GraphicalComponent) {

        $scope.graphicalComponentLink = entity;
        $scope.prompts = Prompt.query();
        $scope.graphicalcomponents = GraphicalComponent.query();
        $scope.load = function(id) {
            GraphicalComponentLink.get({id : id}, function(result) {
                $scope.graphicalComponentLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:graphicalComponentLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.graphicalComponentLink.id != null) {
                GraphicalComponentLink.update($scope.graphicalComponentLink, onSaveSuccess, onSaveError);
            } else {
                GraphicalComponentLink.save($scope.graphicalComponentLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
