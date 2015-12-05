'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('checkScript', {
                parent: 'entity',
                url: '/checkScripts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.checkScript.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/checkScript/checkScripts.html',
                        controller: 'CheckScriptController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('checkScript');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('checkScript.detail', {
                parent: 'entity',
                url: '/checkScript/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.checkScript.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/checkScript/checkScript-detail.html',
                        controller: 'CheckScriptDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('checkScript');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CheckScript', function($stateParams, CheckScript) {
                        return CheckScript.get({id : $stateParams.id});
                    }]
                }
            })
            .state('checkScript.new', {
                parent: 'checkScript',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/checkScript/checkScript-dialog.html',
                        controller: 'CheckScriptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    script: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('checkScript', null, { reload: true });
                    }, function() {
                        $state.go('checkScript');
                    })
                }]
            })
            .state('checkScript.edit', {
                parent: 'checkScript',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/checkScript/checkScript-dialog.html',
                        controller: 'CheckScriptDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CheckScript', function(CheckScript) {
                                return CheckScript.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('checkScript', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('checkScript.delete', {
                parent: 'checkScript',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/checkScript/checkScript-delete-dialog.html',
                        controller: 'CheckScriptDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CheckScript', function(CheckScript) {
                                return CheckScript.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('checkScript', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
