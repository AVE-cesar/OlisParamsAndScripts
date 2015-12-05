'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ParamCategoryController', function ($scope, $state, $modal, ParamCategory, ParamCategorySearch) {
      
        $scope.paramCategorys = [];
        $scope.loadAll = function() {
            ParamCategory.query(function(result) {
               $scope.paramCategorys = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ParamCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.paramCategorys = result;
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
            $scope.paramCategory = {
                description: null,
                id: null
            };
        };
    });
