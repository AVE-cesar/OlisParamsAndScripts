'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AGACUserLinkSearch', function ($resource) {
        return $resource('api/_search/aGACUserLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
