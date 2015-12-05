'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponentParam', function ($resource, DateUtils) {
        return $resource('api/graphicalComponentParams/:id', {}, {
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
.factory('GraphicalComponentParamList', function ($resource, DateUtils) {
    return $resource('api/graphicalComponentParamsByPromptId/:id', {}, {
        'queryGraphicalComponentParamsByPromptId': {
        	method: 'GET',
        	url: 'api/graphicalComponentParamsByPromptId/:id',
        	isArray: true
        }
    });
});

