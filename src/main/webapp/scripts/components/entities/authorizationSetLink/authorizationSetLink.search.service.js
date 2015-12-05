'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AuthorizationSetLinkSearch', function ($resource) {
        return $resource('api/_search/authorizationSetLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
