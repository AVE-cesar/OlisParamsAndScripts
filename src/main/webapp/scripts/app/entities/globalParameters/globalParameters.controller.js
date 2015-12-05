'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GlobalParametersController', function ($scope, $state, $modal, GlobalParameters, GlobalParametersSearch) {
      
        $scope.globalParameterss = [];
        $scope.loadAll = function() {
            GlobalParameters.query(function(result) {
               $scope.globalParameterss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            GlobalParametersSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.globalParameterss = result;
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
            $scope.globalParameters = {
                name: null,
                technicalName: null,
                type: null,
                script: null,
                ttl: null,
                defaultValue: null,
                order: null,
                id: null
            };
        };
    });
