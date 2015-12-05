'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('datasourcePosition', {
                parent: 'entity',
                url: '/datasourcePositions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.datasourcePosition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePositions.html',
                        controller: 'DatasourcePositionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasourcePosition');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('datasourcePosition.detail', {
                parent: 'entity',
                url: '/datasourcePosition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.datasourcePosition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePosition-detail.html',
                        controller: 'DatasourcePositionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasourcePosition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DatasourcePosition', function($stateParams, DatasourcePosition) {
                        return DatasourcePosition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('datasourcePosition.new', {
                parent: 'datasourcePosition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePosition-dialog.html',
                        controller: 'DatasourcePositionDialogController',
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
                        $state.go('datasourcePosition', null, { reload: true });
                    }, function() {
                        $state.go('datasourcePosition');
                    })
                }]
            })
            .state('datasourcePosition.edit', {
                parent: 'datasourcePosition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePosition-dialog.html',
                        controller: 'DatasourcePositionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DatasourcePosition', function(DatasourcePosition) {
                                return DatasourcePosition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('datasourcePosition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('datasourcePosition.delete', {
                parent: 'datasourcePosition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasourcePosition/datasourcePosition-delete-dialog.html',
                        controller: 'DatasourcePositionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DatasourcePosition', function(DatasourcePosition) {
                                return DatasourcePosition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('datasourcePosition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
