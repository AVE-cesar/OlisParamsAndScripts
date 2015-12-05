'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('globalParameters', {
                parent: 'entity',
                url: '/globalParameterss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.globalParameters.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/globalParameters/globalParameterss.html',
                        controller: 'GlobalParametersController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('globalParameters');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('globalParameters.detail', {
                parent: 'entity',
                url: '/globalParameters/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.globalParameters.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/globalParameters/globalParameters-detail.html',
                        controller: 'GlobalParametersDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('globalParameters');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GlobalParameters', function($stateParams, GlobalParameters) {
                        return GlobalParameters.get({id : $stateParams.id});
                    }]
                }
            })
            .state('globalParameters.new', {
                parent: 'globalParameters',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameters/globalParameters-dialog.html',
                        controller: 'GlobalParametersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    technicalName: null,
                                    type: null,
                                    script: null,
                                    ttl: null,
                                    defaultValue: null,
                                    order: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameters', null, { reload: true });
                    }, function() {
                        $state.go('globalParameters');
                    })
                }]
            })
            .state('globalParameters.edit', {
                parent: 'globalParameters',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameters/globalParameters-dialog.html',
                        controller: 'GlobalParametersDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GlobalParameters', function(GlobalParameters) {
                                return GlobalParameters.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameters', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('globalParameters.delete', {
                parent: 'globalParameters',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/globalParameters/globalParameters-delete-dialog.html',
                        controller: 'GlobalParametersDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GlobalParameters', function(GlobalParameters) {
                                return GlobalParameters.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('globalParameters', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
