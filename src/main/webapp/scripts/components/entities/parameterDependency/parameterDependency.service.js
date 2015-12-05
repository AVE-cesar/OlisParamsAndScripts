'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('ParameterDependency', function ($resource, DateUtils) {
        return $resource('api/parameterDependencys/:id', {}, {
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
.factory('ParameterDependencyList', function ($resource, DateUtils) {
    return $resource('api/parameterDependencysByPromptId/:id', {}, {
        'queryParameterDependencysByPromptId': {
        	method: 'GET',
        	url: 'api/parameterDependencysByPromptId/:id',
        	isArray: true
        }
    });
});