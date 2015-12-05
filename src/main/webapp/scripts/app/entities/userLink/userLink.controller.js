'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('UserLinkController', function ($scope, $state, $modal, UserLink, UserLinkSearch) {
      
        $scope.userLinks = [];
        $scope.loadAll = function() {
            UserLink.query(function(result) {
               $scope.userLinks = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            UserLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.userLinks = result;
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
            $scope.userLink = {
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
