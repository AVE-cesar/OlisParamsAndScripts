'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('datasource', {
                parent: 'entity',
                url: '/datasources',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.datasource.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasource/datasources.html',
                        controller: 'DatasourceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasource');
                        $translatePartialLoader.addPart('datasourceType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('datasource.detail', {
                parent: 'entity',
                url: '/datasource/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.datasource.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasource/datasource-detail.html',
                        controller: 'DatasourceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasource');
                        $translatePartialLoader.addPart('datasourceType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Datasource', function($stateParams, Datasource) {
                        return Datasource.get({id : $stateParams.id});
                    }]
                }
            })
            .state('datasource.new', {
                parent: 'datasource',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource/datasource-dialog.html',
                        controller: 'DatasourceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    condition: null,
                                    type: null,
                                    value: null,
                                    code: null,
                                    datasourceLink: null,
                                    sql: null,
                                    script: null,
                                    description: null,
                                    url: null,
                                    request: null,
                                    response: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('datasource', null, { reload: true });
                    }, function() {
                        $state.go('datasource');
                    })
                }]
            })
            /* état pour dupliquer un enregistrement (sans ses enfants ?); comment avoir l'id sans l'afficher dans l'url ?*/
            .state('datasource.duplicate', {
                parent: 'datasource',
                url: '/{id}/duplicate',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource/datasource-dialog.html',
                        controller: 'DatasourceDialogController',
                        size: 'lg',
                        resolve: {
                        	entity: ['Datasource', function(Datasource) {
                                var temp = Datasource.get({id : $stateParams.id}, function() {
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
                        $state.go('datasource', null, { reload: true });
                    }, function() {
                    	//console.log("step: clique sur cancel, on retourne sur la liste des datasources sans recharger");
                        $state.go('datasource');
                    })
                }] // fin du onEnter
            })
            .state('datasource.edit', {
                parent: 'datasource',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource/datasource-dialog.html',
                        controller: 'DatasourceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Datasource', function(Datasource) {
                                return Datasource.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('datasource', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('datasource.delete', {
                parent: 'datasource',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource/datasource-delete-dialog.html',
                        controller: 'DatasourceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Datasource', function(Datasource) {
                                return Datasource.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('datasource', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
