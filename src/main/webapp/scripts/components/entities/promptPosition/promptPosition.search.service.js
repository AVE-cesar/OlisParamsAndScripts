'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('PromptPositionSearch', function ($resource) {
        return $resource('api/_search/promptPositions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
