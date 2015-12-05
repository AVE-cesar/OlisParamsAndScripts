'use strict';

angular.module('olisParamsAndScriptsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('report', {
                parent: 'entity',
                url: '/reports',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.report.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/report/reports.html',
                        controller: 'ReportController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        $translatePartialLoader.addPart('specific');
                        $translatePartialLoader.addPart('raw');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('report.detail', {
                parent: 'entity',
                url: '/report/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'olisParamsAndScriptsApp.report.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/report/report-detail.html',
                        controller: 'ReportDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        $translatePartialLoader.addPart('specific');
                        $translatePartialLoader.addPart('raw');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Report', function($stateParams, Report) {
                        return Report.get({id : $stateParams.id});
                    }]
                }
            })
            /* état pour remplir les onglets des filles */
            .state('PromptPosition.detail', {
                parent: 'entity',
                url: '/promptPosition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PromptPosition'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promptPosition/promptPosition-detail.html',
                        controller: 'PromptPositionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PromptPosition', function($stateParams, PromptPosition) {
                        return PromptPosition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('report.new', {
                parent: 'report',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/report/report-dialog.html',
                        controller: 'ReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    triggerCode: null,
                                    type: null,
                                    domain: null,
                                    generationType: null,
                                    specific: null,
                                    raw: null,
                                    fatRawReport: null,
                                    userAccess: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('report', null, { reload: true });
                    }, function() {
                        $state.go('report');
                    })
                }]
            })
            .state('report.edit', {
                parent: 'report',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/report/report-dialog.html',
                        controller: 'ReportDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Report', function(Report) {
                                return Report.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('report', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('report.delete', {
                parent: 'report',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/report/report-delete-dialog.html',
                        controller: 'ReportDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Report', function(Report) {
                                return Report.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('report', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
