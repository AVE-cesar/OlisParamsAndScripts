'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('PromptPosition', function ($resource, DateUtils) {
        return $resource('api/promptPositions/:id', {}, {
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
.factory('PromptPositionList', function ($resource, DateUtils) {
    return $resource('api/promptPositionsByReportId/:id', {}, {
        /*'query': { method: 'GET', isArray: true},
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },*/
        'queryPromptPositionsByReportId': {
        	method: 'GET',
        	url: 'api/promptPositionsByReportId/:id',
        	isArray: true
        }
    });
});
