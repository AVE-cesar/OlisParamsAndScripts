'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('PromptController', function ($scope, $state, $modal, Prompt, PromptSearch, ParseLinks) {
      
        $scope.prompts = [];
        /*$scope.loadAll = function() {
            Prompt.query(function(result) {
               $scope.prompts = result;
            });
        };*/
        //$scope.loadAll();
        $scope.page = 0;
        $scope.loadAll = function() {
            Prompt.query({page: $scope.page, size: 20}, function(result, headers) {
            
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.prompts.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.prompts = [];
            $scope.loadAll();
        };
		$scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

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
