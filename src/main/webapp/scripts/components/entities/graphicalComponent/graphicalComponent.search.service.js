'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponentSearch', function ($resource) {
        return $resource('api/_search/graphicalComponents/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
