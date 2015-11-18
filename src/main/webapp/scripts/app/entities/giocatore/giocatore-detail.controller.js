'use strict';

angular.module('fantacalcioApp')
    .controller('GiocatoreDetailController', function ($scope, $rootScope, $stateParams, entity, Giocatore, User) {
        $scope.giocatore = entity;
        $scope.load = function (id) {
            Giocatore.get({id: id}, function(result) {
                $scope.giocatore = result;
            });
        };
        $rootScope.$on('fantacalcioApp:giocatoreUpdate', function(event, result) {
            $scope.giocatore = result;
        });
    });
