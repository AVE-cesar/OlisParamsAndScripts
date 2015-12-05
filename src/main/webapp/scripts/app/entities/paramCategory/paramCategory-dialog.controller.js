'use strict';

angular.module('olisParamsAndScriptsApp').controller('ParamCategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ParamCategory', 'GlobalParameter',
        function($scope, $stateParams, $modalInstance, entity, ParamCategory, GlobalParameter) {

        $scope.paramCategory = entity;
        $scope.globalparameters = GlobalParameter.query();
        $scope.load = function(id) {
            ParamCategory.get({id : id}, function(result) {
                $scope.paramCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:paramCategoryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.paramCategory.id != null) {
                ParamCategory.update($scope.paramCategory, onSaveSuccess, onSaveError);
            } else {
                ParamCategory.save($scope.paramCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
