'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationSetController', function ($scope, $state, $modal, AuthorizationSet, AuthorizationSetSearch) {
      
        $scope.authorizationSets = [];
        $scope.loadAll = function() {
            AuthorizationSet.query(function(result) {
               $scope.authorizationSets = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AuthorizationSetSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.authorizationSets = result;
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
            $scope.authorizationSet = {
                code: null,
                name: null,
                id: null
            };
        };
    });
