'use strict';

angular.module('fantacalcioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('formazione', {
                parent: 'entity',
                url: '/formaziones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.formazione.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formazione/formaziones.html',
                        controller: 'FormazioneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('formazione');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('formazione.detail', {
                parent: 'entity',
                url: '/formazione/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.formazione.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formazione/formazione-detail.html',
                        controller: 'FormazioneDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('formazione');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Formazione', function($stateParams, Formazione) {
                        return Formazione.get({id : $stateParams.id});
                    }]
                }
            })
            .state('formazione.new', {
                parent: 'formazione',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/formazione/formazione-dialog.html',
                        controller: 'FormazioneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    giornata: null,
                                    titolare: null,
                                    valutazione: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('formazione', null, { reload: true });
                    }, function() {
                        $state.go('formazione');
                    })
                }]
            })
            .state('formazione.edit', {
                parent: 'formazione',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/formazione/formazione-dialog.html',
                        controller: 'FormazioneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Formazione', function(Formazione) {
                                return Formazione.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('formazione', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
