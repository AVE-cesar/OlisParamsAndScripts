'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('GlobalParameterController', function ($scope, $state, $modal, GlobalParameter, GlobalParameterSearch) {
      
        $scope.globalParameters = [];
        $scope.loadAll = function() {
            GlobalParameter.query(function(result) {
               $scope.globalParameters = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            GlobalParameterSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.globalParameters = result;
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
            $scope.globalParameter = {
                key: null,
                value: null,
                description: null,
                id: null
            };
        };
    });
