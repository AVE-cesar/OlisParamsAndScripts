'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('graphicalComponentParam', {
                parent: 'entity',
                url: '/graphicalComponentParams',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponentParam.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponentParams.html',
                        controller: 'GraphicalComponentParamController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponentParam');
                        $translatePartialLoader.addPart('mode');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('graphicalComponentParam.detail', {
                parent: 'entity',
                url: '/graphicalComponentParam/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.graphicalComponentParam.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponentParam-detail.html',
                        controller: 'GraphicalComponentParamDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('graphicalComponentParam');
                        $translatePartialLoader.addPart('mode');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GraphicalComponentParam', function($stateParams, GraphicalComponentParam) {
                        return GraphicalComponentParam.get({id : $stateParams.id});
                    }]
                }
            })
            .state('graphicalComponentParam.new', {
                parent: 'graphicalComponentParam',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponentParam-dialog.html',
                        controller: 'GraphicalComponentParamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    value: null,
                                    mode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentParam', null, { reload: true });
                    }, function() {
                        $state.go('graphicalComponentParam');
                    })
                }]
            })
            .state('graphicalComponentParam.edit', {
                parent: 'graphicalComponentParam',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponentParam-dialog.html',
                        controller: 'GraphicalComponentParamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GraphicalComponentParam', function(GraphicalComponentParam) {
                                return GraphicalComponentParam.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentParam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('graphicalComponentParam.delete', {
                parent: 'graphicalComponentParam',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/graphicalComponentParam/graphicalComponentParam-delete-dialog.html',
                        controller: 'GraphicalComponentParamDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GraphicalComponentParam', function(GraphicalComponentParam) {
                                return GraphicalComponentParam.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('graphicalComponentParam', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
