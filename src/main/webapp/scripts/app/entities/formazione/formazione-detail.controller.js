'use strict';

angular.module('fantacalcioApp')
    .controller('FormazioneDetailController', function ($scope, $rootScope, $stateParams, entity, Formazione, Giocatore) {
        $scope.formazione = entity;
        $scope.load = function (id) {
            Formazione.get({id: id}, function(result) {
                $scope.formazione = result;
            });
        };
        $rootScope.$on('fantacalcioApp:formazioneUpdate', function(event, result) {
            $scope.formazione = result;
        });
    });
