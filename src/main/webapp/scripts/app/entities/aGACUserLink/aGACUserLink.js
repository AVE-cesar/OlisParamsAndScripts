'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aGACUserLink', {
                parent: 'entity',
                url: '/aGACUserLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACUserLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACUserLink/aGACUserLinks.html',
                        controller: 'AGACUserLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACUserLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aGACUserLink.detail', {
                parent: 'entity',
                url: '/aGACUserLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACUserLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACUserLink/aGACUserLink-detail.html',
                        controller: 'AGACUserLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACUserLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AGACUserLink', function($stateParams, AGACUserLink) {
                        return AGACUserLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aGACUserLink.new', {
                parent: 'aGACUserLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUserLink/aGACUserLink-dialog.html',
                        controller: 'AGACUserLinkDialogController',
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
                        $state.go('aGACUserLink', null, { reload: true });
                    }, function() {
                        $state.go('aGACUserLink');
                    })
                }]
            })
            .state('aGACUserLink.edit', {
                parent: 'aGACUserLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUserLink/aGACUserLink-dialog.html',
                        controller: 'AGACUserLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AGACUserLink', function(AGACUserLink) {
                                return AGACUserLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACUserLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aGACUserLink.delete', {
                parent: 'aGACUserLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUserLink/aGACUserLink-delete-dialog.html',
                        controller: 'AGACUserLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AGACUserLink', function(AGACUserLink) {
                                return AGACUserLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACUserLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
