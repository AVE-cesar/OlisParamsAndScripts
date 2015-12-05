'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('ParameterDependencySearch', function ($resource) {
        return $resource('api/_search/parameterDependencys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
