'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('DatasourcePositionController', function ($scope, $state, $modal, DatasourcePosition, DatasourcePositionSearch) {
      
        $scope.datasourcePositions = [];
        $scope.loadAll = function() {
            DatasourcePosition.query(function(result) {
               $scope.datasourcePositions = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            DatasourcePositionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.datasourcePositions = result;
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
            $scope.datasourcePosition = {
                order: null,
                id: null
            };
        };
    });
