'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('DatasourceController', function ($scope, $state, $modal, Datasource, DatasourceSearch) {
      
        $scope.datasources = [];
        $scope.loadAll = function() {
        	console.log("dans loadAll, query"); 
            Datasource.query(function(result) {
               $scope.datasources = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            DatasourceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.datasources = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
        	console.log("dans controller, refresh"); 
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.datasource = {
                condition: null,
                type: null,
                value: null,
                code: null,
                datasourceLink: null,
                sql: null,
                script: null,
                description: null,
                url: null,
                request: null,
                response: null,
                id: null
            };
        };
    });
