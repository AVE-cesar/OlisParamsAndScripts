'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('AGACUserController', function ($scope, $state, $modal, AGACUser, AGACUserSearch) {
      
        $scope.aGACUsers = [];
        $scope.loadAll = function() {
            AGACUser.query(function(result) {
               $scope.aGACUsers = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            AGACUserSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aGACUsers = result;
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
            $scope.aGACUser = {
                status: null,
                login: null,
                externalId: null,
                gender: null,
                firstName: null,
                lastName: null,
                language: null,
                email: null,
                phone: null,
                cellularPhone: null,
                fax: null,
                authenticationType: null,
                theme: null,
                creationDate: null,
                creatorUserId: null,
                modificationDate: null,
                updatorUserId: null,
                id: null
            };
        };
    });
