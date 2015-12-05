'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('DatasourceSearch', function ($resource) {
        return $resource('api/_search/datasources/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
