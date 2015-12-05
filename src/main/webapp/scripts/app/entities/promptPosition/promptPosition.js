'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('promptPosition', {
                parent: 'entity',
                url: '/promptPositions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.promptPosition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promptPosition/promptPositions.html',
                        controller: 'PromptPositionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promptPosition');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('promptPosition.detail', {
                parent: 'entity',
                url: '/promptPosition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.promptPosition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-detail.html',
                        controller: 'PromptPositionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promptPosition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PromptPosition', function($stateParams, PromptPosition) {
                        return PromptPosition.get({id : $stateParams.id});
                    }]
                }
            })
             /* état pour dupliquer un enregistrement (sans ses enfants ?); comment avoir l'id sans l'afficher dans l'url ?*/
            .state('promptPosition.duplicate', {
                parent: 'promptPosition',
                url: '/{id}/duplicate',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-dialog.html',
                        controller: 'PromptPositionDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['PromptPosition', function(PromptPosition) {
                                var temp = PromptPosition.get({id : $stateParams.id}, function() {
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
                        $state.go('promptPosition', null, { reload: true });
                    }, function() {
                    	//console.log("step: clique sur cancel, on retourne sur la liste des datasources sans recharger");
                        $state.go('promptPosition');
                    })
                }] // fin du onEnter
            })
            .state('promptPosition.new', {
                parent: 'promptPosition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-dialog.html',
                        controller: 'PromptPositionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    order: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('promptPosition', null, { reload: true });
                    }, function() {
                        $state.go('promptPosition');
                    })
                }]
            })
            .state('promptPosition.edit', {
                parent: 'promptPosition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-dialog.html',
                        controller: 'PromptPositionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PromptPosition', function(PromptPosition) {
                                return PromptPosition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('promptPosition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('promptPosition.delete', {
                parent: 'promptPosition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-delete-dialog.html',
                        controller: 'PromptPositionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PromptPosition', function(PromptPosition) {
                                return PromptPosition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('promptPosition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
