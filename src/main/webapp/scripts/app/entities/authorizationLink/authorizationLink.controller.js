'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AuthorizationLinkController', function ($scope, $state, $modal, AuthorizationLink, AuthorizationLinkSearch) {
      
        $scope.authorizationLinks = [];
        $scope.loadAll = function() {
            AuthorizationLink.query(function(result) {
               $scope.authorizationLinks = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AuthorizationLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.authorizationLinks = result;
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
            $scope.authorizationLink = {
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
