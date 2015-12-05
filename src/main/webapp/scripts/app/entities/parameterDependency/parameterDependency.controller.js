'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ParameterDependencyController', function ($scope, $state, $modal, ParameterDependency, ParameterDependencySearch) {
      
        $scope.parameterDependencys = [];
        $scope.loadAll = function() {
            ParameterDependency.query(function(result) {
               $scope.parameterDependencys = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            ParameterDependencySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.parameterDependencys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.parameterDependency = {
                type: null,
                checkOperation: null,
                script: null,
                id: null
            };
        };
    });
