'use strict';

angular.module('olisParamsAndScriptsApp')
    .factory('PromptSearch', function ($resource) {
        return $resource('api/_search/prompts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
