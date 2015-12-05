'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ProfileLinkController', function ($scope, $state, $modal, ProfileLink, ProfileLinkSearch) {
      
        $scope.profileLinks = [];
        $scope.loadAll = function() {
            ProfileLink.query(function(result) {
               $scope.profileLinks = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ProfileLinkSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.profileLinks = result;
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
            $scope.profileLink = {
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
