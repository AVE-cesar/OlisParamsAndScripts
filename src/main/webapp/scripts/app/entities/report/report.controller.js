'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ReportController', function ($scope, $state, $modal, Report, ReportSearch) {
      
        $scope.reports = [];
        $scope.loadAll = function() {
            Report.query(function(result) {
               $scope.reports = result;
            });
        };
        //$scope.loadAll();


        $scope.search = function () {
            ReportSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.reports = result;
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
            $scope.report = {
                name: null,
                triggerCode: null,
                type: null,
                domain: null,
                generationType: null,
                specific: null,
                raw: null,
                fatRawReport: null,
                userAccess: null,
                id: null
            };
        };
    });
