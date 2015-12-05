'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aGACUser', {
                parent: 'entity',
                url: '/aGACUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACUser/aGACUsers.html',
                        controller: 'AGACUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACUser');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('language');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aGACUser.detail', {
                parent: 'entity',
                url: '/aGACUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACUser/aGACUser-detail.html',
                        controller: 'AGACUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACUser');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('language');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AGACUser', function($stateParams, AGACUser) {
                        return AGACUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aGACUser.new', {
                parent: 'aGACUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUser/aGACUser-dialog.html',
                        controller: 'AGACUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    login: null,
                                    externalId: null,
                                    gender: null,
                                    firstName: null,
                                    lastName: null,
                                    language: null,
                                    email: null,
                                    phone: null,
                                    cellularPhone: null,
                                    fax: null,
                                    authenticationType: null,
                                    theme: null,
                                    creationDate: null,
                                    creatorUserId: null,
                                    modificationDate: null,
                                    updatorUserId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aGACUser', null, { reload: true });
                    }, function() {
                        $state.go('aGACUser');
                    })
                }]
            })
            .state('aGACUser.edit', {
                parent: 'aGACUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUser/aGACUser-dialog.html',
                        controller: 'AGACUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AGACUser', function(AGACUser) {
                                return AGACUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aGACUser.delete', {
                parent: 'aGACUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACUser/aGACUser-delete-dialog.html',
                        controller: 'AGACUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AGACUser', function(AGACUser) {
                                return AGACUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
