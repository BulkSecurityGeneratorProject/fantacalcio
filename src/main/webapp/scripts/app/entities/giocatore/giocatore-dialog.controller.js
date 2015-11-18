'use strict';

angular.module('fantacalcioApp').controller('GiocatoreDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Giocatore', 'User',
        function($scope, $stateParams, $modalInstance, entity, Giocatore, User) {

        $scope.giocatore = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Giocatore.get({id : id}, function(result) {
                $scope.giocatore = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('fantacalcioApp:giocatoreUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.giocatore.id != null) {
                Giocatore.update($scope.giocatore, onSaveFinished);
            } else {
                Giocatore.save($scope.giocatore, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
