'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AGACOrganizationSearch', function ($resource) {
        return $resource('api/_search/aGACOrganizations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
