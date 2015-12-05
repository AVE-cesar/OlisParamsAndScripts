'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('CheckScriptController', function ($scope, $state, $modal, CheckScript, CheckScriptSearch) {
      
        $scope.checkScripts = [];
        $scope.loadAll = function() {
            CheckScript.query(function(result) {
               $scope.checkScripts = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            CheckScriptSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.checkScripts = result;
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
            $scope.checkScript = {
                script: null,
                id: null
            };
        };
    });
