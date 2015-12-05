'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('CheckScript', function ($resource, DateUtils) {
        return $resource('api/checkScripts/:id', {}, {
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
