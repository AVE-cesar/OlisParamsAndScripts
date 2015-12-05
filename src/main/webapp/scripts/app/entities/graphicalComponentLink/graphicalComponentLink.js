'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('graphicalComponentLink', {
                parent: 'entity',
                url: '/graphicalComponentLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponentLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLinks.html',
                        controller: 'GraphicalComponentLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponentLink');
                        $translatePartialLoader.addPart('mode');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('graphicalComponentLink.detail', {
                parent: 'entity',
                url: '/graphicalComponentLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponentLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-detail.html',
                        controller: 'GraphicalComponentLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponentLink');
                        $translatePartialLoader.addPart('mode');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GraphicalComponentLink', function($stateParams, GraphicalComponentLink) {
                        return GraphicalComponentLink.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour dupliquer un enregistrement (sans ses enfants ?); comment avoir l'id sans l'afficher dans l'url ?*/
            .state('graphicalComponentLink.duplicate', {
                parent: 'graphicalComponentLink',
                url: '/{id}/duplicate',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-dialog.html',
                        controller: 'GraphicalComponentLinkDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['GraphicalComponentLink', function(GraphicalComponentLink) {
                                var temp = GraphicalComponentLink.get({id : $stateParams.id}, function() {
                                	//cette méthode sera lancée quand le GET finira
                                	temp.id = null;
                                	temp.value = 'duplicated';
                                	});
                                
                                //console.log("dans OnEnter, id: " + temp.id); 
                                return temp;
                            }]                         
                        }
                    })// fin de la méthode $modal.open
                    .result.then(function(result) {
                    	
                    	//console.log("step: clique sur OK, on retourne sur la liste des datasources et on reload");
                        $state.go('graphicalComponentLink', null, { reload: true });
                    }, function() {
                    	//console.log("step: clique sur cancel, on retourne sur la liste des datasources sans recharger");
                        $state.go('graphicalComponentLink');
                    })
                }] // fin du onEnter
            })
            .state('graphicalComponentLink.new', {
                parent: 'graphicalComponentLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-dialog.html',
                        controller: 'GraphicalComponentLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    mode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentLink', null, { reload: true });
                    }, function() {
                        $state.go('graphicalComponentLink');
                    })
                }]
            })
            .state('graphicalComponentLink.edit', {
                parent: 'graphicalComponentLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-dialog.html',
                        controller: 'GraphicalComponentLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GraphicalComponentLink', function(GraphicalComponentLink) {
                                return GraphicalComponentLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('graphicalComponentLink.delete', {
                parent: 'graphicalComponentLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentLink/graphicalComponentLink-delete-dialog.html',
                        controller: 'GraphicalComponentLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GraphicalComponentLink', function(GraphicalComponentLink) {
                                return GraphicalComponentLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
