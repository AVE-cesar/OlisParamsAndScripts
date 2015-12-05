'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('DatasourcePosition', function ($resource, DateUtils) {
        return $resource('api/datasourcePositions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });


/* ajout pour l'appel des filles */
angular.module('olisParamsAndScriptsApp')
.factory('DatasourcePositionList', function ($resource, DateUtils) {
    return $resource('api/datasourcePositionsByPromptId/:id', {}, {
        'queryDatasourcePositionsByPromptId': {
        	method: 'GET',
        	url: 'api/datasourcePositionsByPromptId/:id',
        	isArray: true
        }
    });
});

