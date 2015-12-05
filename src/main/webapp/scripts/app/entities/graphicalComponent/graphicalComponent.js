'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('graphicalComponent', {
                parent: 'entity',
                url: '/graphicalComponents',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponent/graphicalComponents.html',
                        controller: 'GraphicalComponentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponent');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('graphicalComponent.detail', {
                parent: 'entity',
                url: '/graphicalComponent/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponent/graphicalComponent-detail.html',
                        controller: 'GraphicalComponentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponent');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GraphicalComponent', function($stateParams, GraphicalComponent) {
                        return GraphicalComponent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('graphicalComponent.new', {
                parent: 'graphicalComponent',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponent/graphicalComponent-dialog.html',
                        controller: 'GraphicalComponentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    script: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponent', null, { reload: true });
                    }, function() {
                        $state.go('graphicalComponent');
                    })
                }]
            })
            .state('graphicalComponent.edit', {
                parent: 'graphicalComponent',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponent/graphicalComponent-dialog.html',
                        controller: 'GraphicalComponentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GraphicalComponent', function(GraphicalComponent) {
                                return GraphicalComponent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('graphicalComponent.delete', {
                parent: 'graphicalComponent',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponent/graphicalComponent-delete-dialog.html',
                        controller: 'GraphicalComponentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GraphicalComponent', function(GraphicalComponent) {
                                return GraphicalComponent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
