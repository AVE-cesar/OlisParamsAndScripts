'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('authorizationSetLink', {
                parent: 'entity',
                url: '/authorizationSetLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationSetLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationSetLink/authorizationSetLinks.html',
                        controller: 'AuthorizationSetLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationSetLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('authorizationSetLink.detail', {
                parent: 'entity',
                url: '/authorizationSetLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationSetLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationSetLink/authorizationSetLink-detail.html',
                        controller: 'AuthorizationSetLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationSetLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AuthorizationSetLink', function($stateParams, AuthorizationSetLink) {
                        return AuthorizationSetLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('authorizationSetLink.new', {
                parent: 'authorizationSetLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSetLink/authorizationSetLink-dialog.html',
                        controller: 'AuthorizationSetLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    validityStartDate: null,
                                    validityEndDate: null,
                                    creationDate: null,
                                    creatorUserId: null,
                                    modificationDate: null,
                                    updatorUserId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationSetLink', null, { reload: true });
                    }, function() {
                        $state.go('authorizationSetLink');
                    })
                }]
            })
            .state('authorizationSetLink.edit', {
                parent: 'authorizationSetLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSetLink/authorizationSetLink-dialog.html',
                        controller: 'AuthorizationSetLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AuthorizationSetLink', function(AuthorizationSetLink) {
                                return AuthorizationSetLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationSetLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('authorizationSetLink.delete', {
                parent: 'authorizationSetLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationSetLink/authorizationSetLink-delete-dialog.html',
                        controller: 'AuthorizationSetLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AuthorizationSetLink', function(AuthorizationSetLink) {
                                return AuthorizationSetLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationSetLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
