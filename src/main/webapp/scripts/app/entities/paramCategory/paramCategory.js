'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('paramCategory', {
                parent: 'entity',
                url: '/paramCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.paramCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/paramCategory/paramCategorys.html',
                        controller: 'ParamCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('paramCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('paramCategory.detail', {
                parent: 'entity',
                url: '/paramCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.paramCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/paramCategory/paramCategory-detail.html',
                        controller: 'ParamCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('paramCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ParamCategory', function($stateParams, ParamCategory) {
                        return ParamCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('paramCategory.new', {
                parent: 'paramCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/paramCategory/paramCategory-dialog.html',
                        controller: 'ParamCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('paramCategory', null, { reload: true });
                    }, function() {
                        $state.go('paramCategory');
                    })
                }]
            })
            .state('paramCategory.edit', {
                parent: 'paramCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/paramCategory/paramCategory-dialog.html',
                        controller: 'ParamCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ParamCategory', function(ParamCategory) {
                                return ParamCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('paramCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('paramCategory.delete', {
                parent: 'paramCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/paramCategory/paramCategory-delete-dialog.html',
                        controller: 'ParamCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ParamCategory', function(ParamCategory) {
                                return ParamCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('paramCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
