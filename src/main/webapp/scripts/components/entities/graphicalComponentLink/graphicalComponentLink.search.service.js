'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponentLinkSearch', function ($resource) {
        return $resource('api/_search/graphicalComponentLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
