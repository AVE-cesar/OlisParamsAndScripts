'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AGACAuthorizationSearch', function ($resource) {
        return $resource('api/_search/aGACAuthorizations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
