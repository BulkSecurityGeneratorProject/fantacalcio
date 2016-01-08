'use strict';

angular.module('fantacalcioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fantaFormazioneManual', {
                parent: 'entity',
                url: '/fantaFormazioneManual/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.fantaFormazioneManual.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fantaFormazioneManual/fanta-formazione-manual.html',
                        controller: 'FantaFormazioneManualController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fantaFormazioneManual');
                        return $translate.refresh();
                    }]
                }
            })
    });
