'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('authorizationSet', {
                parent: 'entity',
                url: '/authorizationSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationSet/authorizationSets.html',
                        controller: 'AuthorizationSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('authorizationSet.detail', {
                parent: 'entity',
                url: '/authorizationSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationSet/authorizationSet-detail.html',
                        controller: 'AuthorizationSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AuthorizationSet', function($stateParams, AuthorizationSet) {
                        return AuthorizationSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('authorizationSet.new', {
                parent: 'authorizationSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSet/authorizationSet-dialog.html',
                        controller: 'AuthorizationSetDialogController',
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
                        $state.go('authorizationSet', null, { reload: true });
                    }, function() {
                        $state.go('authorizationSet');
                    })
                }]
            })
            .state('authorizationSet.edit', {
                parent: 'authorizationSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSet/authorizationSet-dialog.html',
                        controller: 'AuthorizationSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AuthorizationSet', function(AuthorizationSet) {
                                return AuthorizationSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('authorizationSet.delete', {
                parent: 'authorizationSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSet/authorizationSet-delete-dialog.html',
                        controller: 'AuthorizationSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AuthorizationSet', function(AuthorizationSet) {
                                return AuthorizationSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
