'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GlobalParametersSearch', function ($resource) {
        return $resource('api/_search/globalParameterss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
