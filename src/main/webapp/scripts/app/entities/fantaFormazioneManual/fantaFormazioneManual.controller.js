'use strict';

angular.module('fantacalcioApp')
    .controller('FantaFormazioneManualController', 
    		function ($scope, $rootScope, $stateParams, Giocatore, FantaFormazione) {
    	
    	var formazioniValide = ["433","532","442","451","541","631","343"];
    	
    	$scope.portieri = 0;
    	$scope.difensori = 0;
    	$scope.centrocampisti = 0;
    	$scope.attacanti = 0;
    	
    	$scope.p_portieri = 0;
    	$scope.p_difensori = 0;
    	$scope.p_centrocampisti = 0;
    	$scope.p_attacanti = 0;
    	
    	$scope.clipboard = "";
    	
    	$scope.errorMessage = "";
    	
    	$scope.models = {
    	        selected: null,
    	        lists: { "Portieri": [], "Titolari": [], "Panchina": [], "Difensori": [], "Centrocampisti": [], "Attaccanti": []},
    	        allowedTypes : { "Portieri": ['P'], "Titolari": ['P','D','C','A'], 
    	        	"Panchina": ['P','D','C','A'], "Difensori": ['D'], "Centrocampisti":['C'], "Attaccanti":['A'] }
    	    };
    	
        $scope.loadAll = function () {
        	
        	var idsT;
        	var idsP;
        	
        	FantaFormazione.get({id: $stateParams.id}, function(result) {
        		
                $scope.models.lists.Panchina = result.panchina;
                $scope.models.lists.Titolari = result.titolari;
                
                $scope.models.lists.Portieri = result.portieri;
                $scope.models.lists.Difensori = result.difensori;
                $scope.models.lists.Centrocampisti = result.centrocampisti;
                $scope.models.lists.Attaccanti = result.attaccanti;
                
                
                $scope.updateFormazione();
                
               	//$scope.portieri = 3 - 1 - result.portieri.length;
            	//$scope.difensori = 8- 2 - result.difensori.length;
            	//$scope.centrocampisti = 7 - 2 - result.centrocampisti.length;
            	//$scope.attacanti = 5 - 2 - result.attaccanti.length;
                
            });

        	
        	console.log("LOAD giocatori...");
            
        };
        
        $scope.loadAll();
        
        $scope.dragoverCallback = function(event, index, external, type) {
            $scope.logListEvent('dragged over', event, index, external, type);
            // Disallow dropping in the third row. Could also be done with dnd-disable-if.
            
            if ( type == "Titolari") {
            	return $scope.models.lists.Titolari.length < 11;
            } else {
            	return true;
            }
            
            //else if ( type == "Panchina") {
            //	return $scope.models.lists.Panchina.length < 7;
            //} 
            
            
        };

        $scope.dropCallback = function(event, index, item, external, type, allowedType) {
            $scope.logListEvent('dropped at', event, index, external, type);
            if (external) {
                if (allowedType === 'itemType' && !item.label) return false;
                if (allowedType === 'containerType' && !angular.isArray(item)) return false;
            }
            return item;
        };

        $scope.logEvent = function(message, event) {
            console.log(message, '(triggered by the following', event.type, 'event)');
            console.log(event);
        };

        $scope.logListEvent = function(action, event, index, external, type) {
            var message = external ? 'External ' : '';
            message += type + ' element is ' + action + ' position ' + index;
            $scope.logEvent(message, event);
        };
        

        $scope.updateFormazione = function() {

        	var p = 0;
        	var d = 0;
        	var c = 0;
        	var a = 0;
        	
        	$scope.clipboard = "";
        	
        	
        	$scope.models.lists.Titolari.forEach( function(item) {
        		
	        	switch(item.ruolo) {
        		case "P":
        			p += 1;
        			break;
        		case "D":
        			d += 1;
        			break;
        		case "C":
        			c += 1;
        			break;
        		case "A":
        			a += 1;
        			break;  
        		default:	
	        	}
	        	
	        	$scope.clipboard += item.ruolo + "\t" + "10" + item.codice + "\t" + item.nome_gazzetta + "\n";
	        	
        	});
        	
        	$scope.portieri = p;
        	$scope.difensori = d;
        	$scope.centrocampisti = c;
        	$scope.attacanti = a;
        	
        	if ( p == 0 ) {
            	$scope.errorMessage = "Manca il portiere!";
            } else if ( formazioniValide.indexOf("" + d + c + a) == -1 ) {
            	$scope.errorMessage = "Formazione " + d + "-" + c + "-" + a + " non valida!";
            } else {
            	$scope.errorMessage = "";
            }

        	p = 0;
        	d = 0;
        	c = 0;
        	a = 0;
        	
        	$scope.clipboard += "\n";

        	$scope.models.lists.Panchina.forEach( function(item) {
        		
	        	switch(item.ruolo) {
        		case "P":
        			p += 1;
        			break;
        		case "D":
        			d += 1;
        			break;
        		case "C":
        			c += 1;
        			break;
        		case "A":
        			a += 1;
        			break;  
        		default:	
	        	}
	        	
	        	$scope.clipboard += item.ruolo + "\t" + "10" + item.codice + "\t" + item.nome_gazzetta + "\n";
	        	
        	});        	
        	$scope.p_portieri = p;
        	$scope.p_difensori = d;
        	$scope.p_centrocampisti = c;
        	$scope.p_attacanti = a;
        	
        	if ( "" + p + d + c + a != "1222" ) {
            	$scope.errorMessage += " Verificare i giocatori in panchina.";
            } else {
            	$scope.errorMessage += "";
            }
        };
        
    });
