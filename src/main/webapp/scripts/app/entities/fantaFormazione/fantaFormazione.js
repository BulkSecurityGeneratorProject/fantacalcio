'use strict';

angular.module('fantacalcioApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fantaFormazione', {
                parent: 'entity',
                url: '/fantaFormazione/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'fantacalcioApp.fantaFormazione.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fantaFormazione/fanta-formazione.html',
                        controller: 'FantaFormazioneController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fantaFormazione');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'FantaFormazione', function($stateParams, FantaFormazione) {
                        return FantaFormazione.get({id : $stateParams.id});
                    }]
                }
            })
    });
