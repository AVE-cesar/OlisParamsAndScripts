'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GraphicalComponentLinkController', function ($scope, $state, $modal, GraphicalComponentLink, GraphicalComponentLinkSearch) {
      
        $scope.graphicalComponentLinks = [];
        $scope.loadAll = function() {
            GraphicalComponentLink.query(function(result) {
               $scope.graphicalComponentLinks = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            GraphicalComponentLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.graphicalComponentLinks = result;
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
            $scope.graphicalComponentLink = {
                mode: null,
                id: null
            };
        };
    });
