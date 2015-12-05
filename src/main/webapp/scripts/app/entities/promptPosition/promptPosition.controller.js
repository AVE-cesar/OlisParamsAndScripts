'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('PromptPositionController', function ($scope, $state, $modal, PromptPosition, PromptPositionSearch) {
      
        $scope.promptPositions = [];
        $scope.loadAll = function() {
            PromptPosition.query(function(result) {
               $scope.promptPositions = result;
            });
        };
        // chargée par défaur
        // $scope.loadAll();


        $scope.search = function () {
            PromptPositionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.promptPositions = result;
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
            $scope.promptPosition = {
                order: null,
                id: null
            };
        };
    });
