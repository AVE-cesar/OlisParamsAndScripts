'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('ParamCategorySearch', function ($resource) {
        return $resource('api/_search/paramCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
