'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('authorizationLink', {
                parent: 'entity',
                url: '/authorizationLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationLink/authorizationLinks.html',
                        controller: 'AuthorizationLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('authorizationLink.detail', {
                parent: 'entity',
                url: '/authorizationLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.authorizationLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/authorizationLink/authorizationLink-detail.html',
                        controller: 'AuthorizationLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('authorizationLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AuthorizationLink', function($stateParams, AuthorizationLink) {
                        return AuthorizationLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('authorizationLink.new', {
                parent: 'authorizationLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationLink/authorizationLink-dialog.html',
                        controller: 'AuthorizationLinkDialogController',
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
                        $state.go('authorizationLink', null, { reload: true });
                    }, function() {
                        $state.go('authorizationLink');
                    })
                }]
            })
            .state('authorizationLink.edit', {
                parent: 'authorizationLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationLink/authorizationLink-dialog.html',
                        controller: 'AuthorizationLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AuthorizationLink', function(AuthorizationLink) {
                                return AuthorizationLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('authorizationLink.delete', {
                parent: 'authorizationLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/authorizationLink/authorizationLink-delete-dialog.html',
                        controller: 'AuthorizationLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AuthorizationLink', function(AuthorizationLink) {
                                return AuthorizationLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('authorizationLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
