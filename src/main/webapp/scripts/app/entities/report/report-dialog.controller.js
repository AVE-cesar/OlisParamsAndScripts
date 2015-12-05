'use strict';

angular.module('olisParamsAndScriptsApp').controller('ReportDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Report', 'PromptPosition',
        function($scope, $stateParams, $modalInstance, entity, Report, PromptPosition) {

        $scope.report = entity;
        $scope.promptpositions = PromptPosition.query();
        $scope.load = function(id) {
            Report.get({id : id}, function(result) {
                $scope.report = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:reportUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.report.id != null) {
                Report.update($scope.report, onSaveSuccess, onSaveError);
            } else {
                Report.save($scope.report, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
