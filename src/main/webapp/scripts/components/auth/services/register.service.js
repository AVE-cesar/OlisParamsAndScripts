'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


