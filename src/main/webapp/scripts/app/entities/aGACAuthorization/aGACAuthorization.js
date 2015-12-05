'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aGACAuthorization', {
                parent: 'entity',
                url: '/aGACAuthorizations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACAuthorization.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACAuthorization/aGACAuthorizations.html',
                        controller: 'AGACAuthorizationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACAuthorization');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aGACAuthorization.detail', {
                parent: 'entity',
                url: '/aGACAuthorization/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACAuthorization.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACAuthorization/aGACAuthorization-detail.html',
                        controller: 'AGACAuthorizationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACAuthorization');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AGACAuthorization', function($stateParams, AGACAuthorization) {
                        return AGACAuthorization.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aGACAuthorization.new', {
                parent: 'aGACAuthorization',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACAuthorization/aGACAuthorization-dialog.html',
                        controller: 'AGACAuthorizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aGACAuthorization', null, { reload: true });
                    }, function() {
                        $state.go('aGACAuthorization');
                    })
                }]
            })
            .state('aGACAuthorization.edit', {
                parent: 'aGACAuthorization',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACAuthorization/aGACAuthorization-dialog.html',
                        controller: 'AGACAuthorizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AGACAuthorization', function(AGACAuthorization) {
                                return AGACAuthorization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACAuthorization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aGACAuthorization.delete', {
                parent: 'aGACAuthorization',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACAuthorization/aGACAuthorization-delete-dialog.html',
                        controller: 'AGACAuthorizationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AGACAuthorization', function(AGACAuthorization) {
                                return AGACAuthorization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACAuthorization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
