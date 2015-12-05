'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AuthorizationLinkSearch', function ($resource) {
        return $resource('api/_search/authorizationLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
