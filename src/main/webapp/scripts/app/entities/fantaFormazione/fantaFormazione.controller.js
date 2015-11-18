'use strict';

angular.module('fantacalcioApp')
    .controller('FantaFormazioneController', function ($scope, $rootScope, $stateParams, entity, FantaFormazione) {
        $scope.fantaFormazione = entity;
        var str = $stateParams.id;
        $scope.modulo = str.substring(0, 1) + '-' + str.substring(1, 2) + "-" + str.substring(2, 3);
        $scope.load = function (id) {
            FantaFormazione.get({id: id}, function(result) {
                $scope.fantaFormazione = result;
            });
        };
    });
