'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('Datasource', function ($resource, DateUtils) {
        return $resource('api/datasources/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                	//console.log("retour du get");
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
