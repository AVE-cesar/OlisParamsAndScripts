'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GlobalParameter', function ($resource, DateUtils) {
        return $resource('api/globalParameters/:id', {}, {
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
