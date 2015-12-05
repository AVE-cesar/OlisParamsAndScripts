'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AGACUserSearch', function ($resource) {
        return $resource('api/_search/aGACUsers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
