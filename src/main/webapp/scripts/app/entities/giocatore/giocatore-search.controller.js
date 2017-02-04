'use strict';

angular.module('fantacalcioApp')
    .controller('GiocatoreSearchController', function ($scope, Giocatore, ParseLinks) {
        $scope.giocatores = [];
        $scope.page = 0;
        
        $scope.load = function (name) {
            Giocatore.search({name: name}, function(result) {
                $scope.giocatores = result;
            });
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
