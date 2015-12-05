'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('UserLinkSearch', function ($resource) {
        return $resource('api/_search/userLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
