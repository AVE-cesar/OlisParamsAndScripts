'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('DatasourcePositionSearch', function ($resource) {
        return $resource('api/_search/datasourcePositions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
