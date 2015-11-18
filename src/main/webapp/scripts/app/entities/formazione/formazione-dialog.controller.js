'use strict';

angular.module('fantacalcioApp').controller('FormazioneDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Formazione', 'Giocatore',
        function($scope, $stateParams, $modalInstance, entity, Formazione, Giocatore) {

        $scope.formazione = entity;
        $scope.giocatores = Giocatore.query();
        $scope.load = function(id) {
            Formazione.get({id : id}, function(result) {
                $scope.formazione = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fantacalcioApp:formazioneUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.formazione.id != null) {
                Formazione.update($scope.formazione, onSaveFinished);
            } else {
                Formazione.save($scope.formazione, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
