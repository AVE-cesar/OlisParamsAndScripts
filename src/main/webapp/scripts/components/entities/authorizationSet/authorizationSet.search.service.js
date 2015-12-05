'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AuthorizationSetSearch', function ($resource) {
        return $resource('api/_search/authorizationSets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
