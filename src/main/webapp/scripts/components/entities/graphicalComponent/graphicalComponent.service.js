'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('GraphicalComponent', function ($resource, DateUtils) {
        return $resource('api/graphicalComponents/:id', {}, {
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
