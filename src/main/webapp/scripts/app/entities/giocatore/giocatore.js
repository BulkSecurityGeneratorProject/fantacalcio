'use strict';

angular.module('fantacalcioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giocatore', {
                parent: 'entity',
                url: '/giocatores',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.giocatore.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giocatore/giocatores.html',
                        controller: 'GiocatoreController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giocatore');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giocatore.detail', {
                parent: 'entity',
                url: '/giocatore/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.giocatore.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giocatore/giocatore-detail.html',
                        controller: 'GiocatoreDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giocatore');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Giocatore', function($stateParams, Giocatore) {
                        return Giocatore.get({id : $stateParams.id});
                    }]
                }
            })
            .state('giocatore.new', {
                parent: 'giocatore',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/giocatore/giocatore-dialog.html',
                        controller: 'GiocatoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    codice: null,
                                    nome: null,
                                    nome_gazzetta: null,
                                    ruolo: null,
                                    presenze: null,
                                    cambio_in: null,
                                    cambio_out: null,
                                    gol: null,
                                    espulsioni: null,
                                    ammonizioni: null,
                                    media_punti: null,
                                    stato: null,
                                    ultima_modifica: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('giocatore', null, { reload: true });
                    }, function() {
                        $state.go('giocatore');
                    })
                }]
            })
            .state('giocatore.edit', {
                parent: 'giocatore',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/giocatore/giocatore-dialog.html',
                        controller: 'GiocatoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Giocatore', function(Giocatore) {
                                return Giocatore.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('giocatore', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
