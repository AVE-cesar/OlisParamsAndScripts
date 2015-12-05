'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACOrganizationController', function ($scope, $state, $modal, AGACOrganization, AGACOrganizationSearch) {
      
        $scope.aGACOrganizations = [];
        $scope.loadAll = function() {
            AGACOrganization.query(function(result) {
               $scope.aGACOrganizations = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AGACOrganizationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aGACOrganizations = result;
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
            $scope.aGACOrganization = {
                code: null,
                name: null,
                type: null,
                status: null,
                rootOrganization: null,
                fatherOrganization: null,
                theme: null,
                email: null,
                level: null,
                internal: null,
                creationDate: null,
                creatorUserId: null,
                modificationDate: null,
                updatorUserId: null,
                id: null
            };
        };
    });
