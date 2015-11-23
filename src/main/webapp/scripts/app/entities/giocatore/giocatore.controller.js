'use strict';

angular.module('fantacalcioApp')
    .controller('GiocatoreController', function ($scope, Giocatore, ParseLinks) {
        $scope.giocatores = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Giocatore.query({page: $scope.page, size: 25}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.giocatores = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Giocatore.get({id: id}, function(result) {
                $scope.giocatore = result;
                $('#deleteGiocatoreConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Giocatore.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGiocatoreConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.giocatore = {
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
        };
        
        $scope.scoreRole = function(giocatore) {
        	if ( giocatore.ruolo == 'P')  {
        		return 'active';
        	} else if ( giocatore.ruolo == 'D') {
        		return 'success';
        	} else if ( giocatore.ruolo == 'C' ) {
        		return 'warning';
        	} else
        		return 'danger';
        };
    });
