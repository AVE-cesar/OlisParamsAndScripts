'use strict';

angular.module('olisParamsAndScriptsApp').controller('DatasourceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Datasource', 'DatasourcePosition',
        function($scope, $stateParams, $modalInstance, entity, Datasource, DatasourcePosition) {

        $scope.datasource = entity;
        
        // on va cherche la liste des datasourcePosition ?
        //$scope.datasourcepositions = DatasourcePosition.query();
        
        $scope.load = function(id) {
            Datasource.get({id : id}, function(result) {
            	//console.log("dans le CTRL, reload id: " + id); 
                $scope.datasource = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:datasourceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.datasource.id != null) {
            	console.log("on doit la faire la maj car il y a un id");
                Datasource.update($scope.datasource, onSaveSuccess, onSaveError);
            } else {
            	console.log("on doit faire la création car il n'y a pas d'id.");
                Datasource.save($scope.datasource, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
