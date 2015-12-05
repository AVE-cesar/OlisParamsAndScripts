'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('parameterDependency', {
                parent: 'entity',
                url: '/parameterDependencys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.parameterDependency.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependencys.html',
                        controller: 'ParameterDependencyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parameterDependency');
                        $translatePartialLoader.addPart('dependencyType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('parameterDependency.detail', {
                parent: 'entity',
                url: '/parameterDependency/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.parameterDependency.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-detail.html',
                        controller: 'ParameterDependencyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parameterDependency');
                        $translatePartialLoader.addPart('dependencyType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ParameterDependency', function($stateParams, ParameterDependency) {
                        return ParameterDependency.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour dupliquer un enregistrement (sans ses enfants ?); comment avoir l'id sans l'afficher dans l'url ?*/
            .state('parameterDependency.duplicate', {
                parent: 'parameterDependency',
                url: '/{id}/parameterDependency',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-dialog.html',
                        controller: 'ParameterDependencyDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['ParameterDependency', function(ParameterDependency) {
                                var temp = ParameterDependency.get({id : $stateParams.id}, function() {
                                	//cette méthode sera lancée quand le GET finira
                                	temp.id = null;
                                	});
                                
                                return temp;
                            }]                         
                        }
                    })// fin de la méthode $modal.open
                    .result.then(function(result) {
                    	
                    	//console.log("step: clique sur OK, on retourne sur la liste des datasources et on reload");
                        $state.go('parameterDependency', null, { reload: true });
                    }, function() {
                    	//console.log("step: clique sur cancel, on retourne sur la liste des datasources sans recharger");
                        $state.go('parameterDependency');
                    })
                }] // fin du onEnter
            })
            .state('parameterDependency.new', {
                parent: 'parameterDependency',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-dialog.html',
                        controller: 'ParameterDependencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    checkOperation: null,
                                    script: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('parameterDependency', null, { reload: true });
                    }, function() {
                        $state.go('parameterDependency');
                    })
                }]
            })
            .state('parameterDependency.edit', {
                parent: 'parameterDependency',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-dialog.html',
                        controller: 'ParameterDependencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ParameterDependency', function(ParameterDependency) {
                                return ParameterDependency.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('parameterDependency', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('parameterDependency.delete', {
                parent: 'parameterDependency',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/parameterDependency/parameterDependency-delete-dialog.html',
                        controller: 'ParameterDependencyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ParameterDependency', function(ParameterDependency) {
                                return ParameterDependency.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('parameterDependency', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
