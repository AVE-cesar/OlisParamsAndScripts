'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('ParamCategory', function ($resource, DateUtils) {
        return $resource('api/paramCategorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
