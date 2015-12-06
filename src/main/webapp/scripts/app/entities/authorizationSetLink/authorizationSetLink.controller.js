'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationSetLinkController', function ($scope, $state, $modal, AuthorizationSetLink, AuthorizationSetLinkSearch) {
      
        $scope.authorizationSetLinks = [];
        $scope.loadAll = function() {
            AuthorizationSetLink.query(function(result) {
               $scope.authorizationSetLinks = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            AuthorizationSetLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.authorizationSetLinks = result;
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
            $scope.authorizationSetLink = {
                status: null,
                validityStartDate: null,
                validityEndDate: null,
                creationDate: null,
                creatorUserId: null,
                modificationDate: null,
                updatorUserId: null,
                id: null
            };
        };
    });
