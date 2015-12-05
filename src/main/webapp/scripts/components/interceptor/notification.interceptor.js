 'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-olisParamsAndScriptsApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-olisParamsAndScriptsApp-params')});
                }
                return response;
            }
        };
    });
