'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentController', function ($scope, $state, $modal, GraphicalComponent, GraphicalComponentSearch) {
      
        $scope.graphicalComponents = [];
        $scope.loadAll = function() {
            GraphicalComponent.query(function(result) {
               $scope.graphicalComponents = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            GraphicalComponentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.graphicalComponents = result;
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
            $scope.graphicalComponent = {
                script: null,
                description: null,
                id: null
            };
        };
    });
