'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profileLink', {
                parent: 'entity',
                url: '/profileLinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.profileLink.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/profileLink/profileLinks.html',
                        controller: 'ProfileLinkController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('profileLink');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('profileLink.detail', {
                parent: 'entity',
                url: '/profileLink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.profileLink.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/profileLink/profileLink-detail.html',
                        controller: 'ProfileLinkDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('profileLink');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProfileLink', function($stateParams, ProfileLink) {
                        return ProfileLink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('profileLink.new', {
                parent: 'profileLink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/profileLink/profileLink-dialog.html',
                        controller: 'ProfileLinkDialogController',
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
                        $state.go('profileLink', null, { reload: true });
                    }, function() {
                        $state.go('profileLink');
                    })
                }]
            })
            .state('profileLink.edit', {
                parent: 'profileLink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/profileLink/profileLink-dialog.html',
                        controller: 'ProfileLinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProfileLink', function(ProfileLink) {
                                return ProfileLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profileLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('profileLink.delete', {
                parent: 'profileLink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/profileLink/profileLink-delete-dialog.html',
                        controller: 'ProfileLinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProfileLink', function(ProfileLink) {
                                return ProfileLink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('profileLink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
