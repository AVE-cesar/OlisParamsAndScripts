'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('ProfileLinkSearch', function ($resource) {
        return $resource('api/_search/profileLinks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
