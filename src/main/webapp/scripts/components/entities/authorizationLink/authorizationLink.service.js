'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('AuthorizationLink', function ($resource, DateUtils) {
        return $resource('api/authorizationLinks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.validityStartDate = DateUtils.convertLocaleDateFromServer(data.validityStartDate);
                    data.validityEndDate = DateUtils.convertLocaleDateFromServer(data.validityEndDate);
                    data.creationDate = DateUtils.convertLocaleDateFromServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateFromServer(data.modificationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.validityStartDate = DateUtils.convertLocaleDateToServer(data.validityStartDate);
                    data.validityEndDate = DateUtils.convertLocaleDateToServer(data.validityEndDate);
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateToServer(data.modificationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.validityStartDate = DateUtils.convertLocaleDateToServer(data.validityStartDate);
                    data.validityEndDate = DateUtils.convertLocaleDateToServer(data.validityEndDate);
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.modificationDate = DateUtils.convertLocaleDateToServer(data.modificationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
