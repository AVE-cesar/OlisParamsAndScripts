'use strict';

angular.module('olisParamsAndScriptsApp').controller('CheckScriptDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CheckScript', 'Prompt',
        function($scope, $stateParams, $modalInstance, entity, CheckScript, Prompt) {

        $scope.checkScript = entity;
        $scope.prompts = Prompt.query();
        $scope.load = function(id) {
            CheckScript.get({id : id}, function(result) {
                $scope.checkScript = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:checkScriptUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.checkScript.id != null) {
                CheckScript.update($scope.checkScript, onSaveSuccess, onSaveError);
            } else {
                CheckScript.save($scope.checkScript, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
