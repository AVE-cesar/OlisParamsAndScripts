'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentParamController', function ($scope, $state, $modal, GraphicalComponentParam, GraphicalComponentParamSearch) {
      
        $scope.graphicalComponentParams = [];
        $scope.loadAll = function() {
            GraphicalComponentParam.query(function(result) {
               $scope.graphicalComponentParams = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            GraphicalComponentParamSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.graphicalComponentParams = result;
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
            $scope.graphicalComponentParam = {
                name: null,
                value: null,
                mode: null,
                id: null
            };
        };
    });
