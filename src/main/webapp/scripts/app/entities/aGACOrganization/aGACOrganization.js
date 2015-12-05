'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('aGACOrganization', {
                parent: 'entity',
                url: '/aGACOrganizations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACOrganization.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACOrganization/aGACOrganizations.html',
                        controller: 'AGACOrganizationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACOrganization');
                        $translatePartialLoader.addPart('level');
                        $translatePartialLoader.addPart('boolValue');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('aGACOrganization.detail', {
                parent: 'entity',
                url: '/aGACOrganization/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.aGACOrganization.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/aGACOrganization/aGACOrganization-detail.html',
                        controller: 'AGACOrganizationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aGACOrganization');
                        $translatePartialLoader.addPart('level');
                        $translatePartialLoader.addPart('boolValue');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AGACOrganization', function($stateParams, AGACOrganization) {
                        return AGACOrganization.get({id : $stateParams.id});
                    }]
                }
            })
            .state('aGACOrganization.new', {
                parent: 'aGACOrganization',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACOrganization/aGACOrganization-dialog.html',
                        controller: 'AGACOrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    type: null,
                                    status: null,
                                    rootOrganization: null,
                                    fatherOrganization: null,
                                    theme: null,
                                    email: null,
                                    level: null,
                                    internal: null,
                                    creationDate: null,
                                    creatorUserId: null,
                                    modificationDate: null,
                                    updatorUserId: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('aGACOrganization', null, { reload: true });
                    }, function() {
                        $state.go('aGACOrganization');
                    })
                }]
            })
            .state('aGACOrganization.edit', {
                parent: 'aGACOrganization',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACOrganization/aGACOrganization-dialog.html',
                        controller: 'AGACOrganizationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AGACOrganization', function(AGACOrganization) {
                                return AGACOrganization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACOrganization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('aGACOrganization.delete', {
                parent: 'aGACOrganization',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/aGACOrganization/aGACOrganization-delete-dialog.html',
                        controller: 'AGACOrganizationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AGACOrganization', function(AGACOrganization) {
                                return AGACOrganization.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('aGACOrganization', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
