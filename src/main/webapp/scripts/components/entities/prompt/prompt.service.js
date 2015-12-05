'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('Prompt', function ($resource, DateUtils) {
        return $resource('api/prompts/:id', {}, {
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
