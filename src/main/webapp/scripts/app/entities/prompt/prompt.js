'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prompt', {
                parent: 'entity',
                url: '/prompts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.prompt.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prompt/prompts.html',
                        controller: 'PromptController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prompt');
                        $translatePartialLoader.addPart('promptType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prompt.detail', {
                parent: 'entity',
                url: '/prompt/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.prompt.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prompt/prompt-detail.html',
                        controller: 'PromptDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prompt');
                        $translatePartialLoader.addPart('promptType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Prompt', function($stateParams, Prompt) {
                        return Prompt.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour remplir les onglets des filles */
            .state('GraphicalComponentLink.detail', {
                parent: 'entity',
                url: '/graphicalComponentLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GraphicalComponentLink'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-detail.html',
                        controller: 'GraphicalComponentLinkDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GraphicalComponentLink', function($stateParams, PromptPosition) {
                        return GraphicalComponentLink.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour remplir les onglets des filles */
            .state('GraphicalComponentParam.detail', {
                parent: 'entity',
                url: '/graphicalComponentParam/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'GraphicalComponentParam'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponenParam-detail.html',
                        controller: 'GraphicalComponentParamDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GraphicalComponentParam', function($stateParams, PromptPosition) {
                        return GraphicalComponentParam.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour remplir les onglets des filles */
            .state('DatasourcePosition.detail', {
                parent: 'entity',
                url: '/datasourcePosition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DatasourcePosition'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePosition-detail.html',
                        controller: 'DatasourcePositionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DatasourcePosition', function($stateParams, PromptPosition) {
                        return DatasourcePosition.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour remplir les onglets des filles */
            .state('ParameterDependency.detail', {
                parent: 'entity',
                url: '/parameterDependency/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ParameterDependency'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-detail.html',
                        controller: 'ParameterDependencyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ParameterDependency', function($stateParams, PromptPosition) {
                        return ParameterDependency.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour dupliquer un enregistrement (sans ses enfants ?); comment avoir l'id sans l'afficher dans l'url ?*/
            .state('prompt.duplicate', {
                parent: 'prompt',
                url: '/{id}/duplicate',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prompt/prompt-dialog.html',
                        controller: 'PromptDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['Prompt', function(Prompt) {
                                var temp = Prompt.get({id : $stateParams.id}, function() {
                                	//cette méthode sera lancée quand le GET finira
                                	temp.id = null;
                                	});
                                
                                //console.log("dans OnEnter, id: " + temp.id); 
                                return temp;
                            }]                         
                        }
                    })// fin de la méthode $modal.open
                    .result.then(function(result) {
                    	
                    	//console.log("step: clique sur OK, on retourne sur la liste des datasources et on reload");
                        $state.go('prompt', null, { reload: true });
                    }, function() {
                    	//console.log("step: clique sur cancel, on retourne sur la liste des datasources sans recharger");
                        $state.go('prompt');
                    })
                }] // fin du onEnter
            })
            .state('prompt.new', {
                parent: 'prompt',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prompt/prompt-dialog.html',
                        controller: 'PromptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    systemName: null,
                                    type: null,
                                    transformationScript: null,
                                    visibleName: null,
                                    defaultValueScript: null,
                                    order: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('prompt', null, { reload: true });
                    }, function() {
                        $state.go('prompt');
                    })
                }]
            })
            .state('prompt.edit', {
                parent: 'prompt',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prompt/prompt-dialog.html',
                        controller: 'PromptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Prompt', function(Prompt) {
                                return Prompt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prompt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('prompt.delete', {
                parent: 'prompt',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prompt/prompt-delete-dialog.html',
                        controller: 'PromptDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Prompt', function(Prompt) {
                                return Prompt.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prompt', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
