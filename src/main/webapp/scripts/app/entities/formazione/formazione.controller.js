'use strict';

angular.module('fantacalcioApp')
    .controller('FormazioneController', function ($scope, Formazione, ParseLinks) {
        $scope.formaziones = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Formazione.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.formaziones = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Formazione.get({id: id}, function(result) {
                $scope.formazione = result;
                $('#deleteFormazioneConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Formazione.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFormazioneConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.formazione = {
                giornata: null,
                titolare: null,
                valutazione: null,
                id: null
            };
        };
    });
