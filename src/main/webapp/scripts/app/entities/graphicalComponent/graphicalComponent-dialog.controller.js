'use strict';

angular.module('olisParamsAndScriptsApp').controller('GraphicalComponentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GraphicalComponent', 'GraphicalComponentLink', 'GraphicalComponentParam',
        function($scope, $stateParams, $modalInstance, entity, GraphicalComponent, GraphicalComponentLink, GraphicalComponentParam) {

        $scope.graphicalComponent = entity;
        $scope.graphicalcomponentlinks = GraphicalComponentLink.query();
        $scope.graphicalcomponentparams = GraphicalComponentParam.query();
        $scope.load = function(id) {
            GraphicalComponent.get({id : id}, function(result) {
                $scope.graphicalComponent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:graphicalComponentUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.graphicalComponent.id != null) {
                GraphicalComponent.update($scope.graphicalComponent, onSaveSuccess, onSaveError);
            } else {
                GraphicalComponent.save($scope.graphicalComponent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
