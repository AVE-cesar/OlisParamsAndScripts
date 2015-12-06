'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACAuthorizationController', function ($scope, $state, $modal, AGACAuthorization, AGACAuthorizationSearch) {
      
        $scope.aGACAuthorizations = [];
        $scope.loadAll = function() {
            AGACAuthorization.query(function(result) {
               $scope.aGACAuthorizations = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            AGACAuthorizationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aGACAuthorizations = result;
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
            $scope.aGACAuthorization = {
                code: null,
                name: null,
                id: null
            };
        };
    });
