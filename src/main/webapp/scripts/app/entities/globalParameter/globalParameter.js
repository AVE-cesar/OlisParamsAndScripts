'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('globalParameter', {
                parent: 'entity',
                url: '/globalParameters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.globalParameter.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/globalParameter/globalParameters.html',
                        controller: 'GlobalParameterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('globalParameter');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('globalParameter.detail', {
                parent: 'entity',
                url: '/globalParameter/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.globalParameter.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/globalParameter/globalParameter-detail.html',
                        controller: 'GlobalParameterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('globalParameter');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GlobalParameter', function($stateParams, GlobalParameter) {
                        return GlobalParameter.get({id : $stateParams.id});
                    }]
                }
            })
            .state('globalParameter.new', {
                parent: 'globalParameter',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameter/globalParameter-dialog.html',
                        controller: 'GlobalParameterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    key: null,
                                    value: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameter', null, { reload: true });
                    }, function() {
                        $state.go('globalParameter');
                    })
                }]
            })
            .state('globalParameter.edit', {
                parent: 'globalParameter',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameter/globalParameter-dialog.html',
                        controller: 'GlobalParameterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GlobalParameter', function(GlobalParameter) {
                                return GlobalParameter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('globalParameter.delete', {
                parent: 'globalParameter',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameter/globalParameter-delete-dialog.html',
                        controller: 'GlobalParameterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GlobalParameter', function(GlobalParameter) {
                                return GlobalParameter.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameter', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
