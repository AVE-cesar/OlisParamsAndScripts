'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponentLink', function ($resource, DateUtils) {
        return $resource('api/graphicalComponentLinks/:id', {}, {
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
.factory('GraphicalComponentLinkList', function ($resource, DateUtils) {
    return $resource('api/graphicalComponentLinksByPromptId/:id', {}, {
        'queryGraphicalComponentLinksByPromptId': {
        	method: 'GET',
        	url: 'api/graphicalComponentLinksByPromptId/:id',
        	isArray: true
        }
    });
});

