'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userLink', {
                parent: 'entity',
                url: '/userLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.userLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userLink/userLinks.html',
                        controller: 'UserLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userLink.detail', {
                parent: 'entity',
                url: '/userLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.userLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userLink/userLink-detail.html',
                        controller: 'UserLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UserLink', function($stateParams, UserLink) {
                        return UserLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userLink.new', {
                parent: 'userLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userLink/userLink-dialog.html',
                        controller: 'UserLinkDialogController',
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
                        $state.go('userLink', null, { reload: true });
                    }, function() {
                        $state.go('userLink');
                    })
                }]
            })
            .state('userLink.edit', {
                parent: 'userLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userLink/userLink-dialog.html',
                        controller: 'UserLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserLink', function(UserLink) {
                                return UserLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userLink.delete', {
                parent: 'userLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userLink/userLink-delete-dialog.html',
                        controller: 'UserLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserLink', function(UserLink) {
                                return UserLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
