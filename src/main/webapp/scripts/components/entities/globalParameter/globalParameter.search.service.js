'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GlobalParameterSearch', function ($resource) {
        return $resource('api/_search/globalParameters/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
