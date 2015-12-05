'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACUserLinkController', function ($scope, $state, $modal, AGACUserLink, AGACUserLinkSearch) {
      
        $scope.aGACUserLinks = [];
        $scope.loadAll = function() {
            AGACUserLink.query(function(result) {
               $scope.aGACUserLinks = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AGACUserLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aGACUserLinks = result;
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
            $scope.aGACUserLink = {
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
