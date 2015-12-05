'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('CheckScriptSearch', function ($resource) {
        return $resource('api/_search/checkScripts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
