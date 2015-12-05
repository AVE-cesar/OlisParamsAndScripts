'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponentParamSearch', function ($resource) {
        return $resource('api/_search/graphicalComponentParams/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
