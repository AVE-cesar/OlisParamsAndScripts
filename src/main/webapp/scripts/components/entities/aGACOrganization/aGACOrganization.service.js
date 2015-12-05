'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AGACOrganization', function ($resource, DateUtils) {
        return $resource('api/aGACOrganizations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertLocaleDateFromServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateFromServer(data.modificationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateToServer(data.modificationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateToServer(data.modificationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
