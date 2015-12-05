'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('PromptController', function ($scope, $state, $modal, Prompt, PromptSearch) {
      
        $scope.prompts = [];
        $scope.loadAll = function() {
            Prompt.query(function(result) {
               $scope.prompts = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            PromptSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prompts = result;
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
            $scope.prompt = {
                name: null,
                systemName: null,
                type: null,
                transformationScript: null,
                visibleName: null,
                defaultValueScript: null,
                order: null,
                id: null
            };
        };
    });
