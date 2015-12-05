'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ReportDetailController', function ($scope, $rootScope, $stateParams, entity, Report, 
    		PromptPositionList) {
        $scope.report = entity;
        
        // appel des enfants de cette entity
        $scope.promptPositions = PromptPositionList.queryPromptPositionsByReportId({id: $stateParams.id}, function(data) {
            console.log("fin appel WS des promptPosition: " + data);                
        });
        
        $scope.load = function (id) {
            Report.get({id: id}, function(result) {
                $scope.report = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:reportUpdate', function(event, result) {
            $scope.report = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
