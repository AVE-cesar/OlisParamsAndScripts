'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('PromptDetailController', function ($scope, $rootScope, $stateParams, entity, Prompt, PromptPosition, 
    		DatasourcePosition, GraphicalComponentLink, GraphicalComponentParam, CheckScript, ParameterDependency
    		,GraphicalComponentLinkList, GraphicalComponentParamList, DatasourcePositionList, ParameterDependencyList) {
        $scope.prompt = entity;
        
        // appel des enfants de cette entity
        $scope.graphCompLinks = GraphicalComponentLinkList.queryGraphicalComponentLinksByPromptId({id: $stateParams.id}, function(data) {
            console.log("fin appel WS des graphCompLinks: " + data);                
        });
        $scope.graphCompParams = GraphicalComponentParamList.queryGraphicalComponentParamsByPromptId({id: $stateParams.id}, function(data) {
            console.log("fin appel WS des graphCompParams: " + data);                
        });
        $scope.datasourcePositions = DatasourcePositionList.queryDatasourcePositionsByPromptId({id: $stateParams.id}, function(data) {
            console.log("fin appel WS des datasourcePositions: " + data);                
        });
        $scope.parameterDependencys = ParameterDependencyList.queryParameterDependencysByPromptId({id: $stateParams.id}, function(data) {
            console.log("fin appel WS des parameterDependencys: " + data);                
        });
        
        $scope.load = function (id) {
            Prompt.get({id: id}, function(result) {
                $scope.prompt = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:promptUpdate', function(event, result) {
            $scope.prompt = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
