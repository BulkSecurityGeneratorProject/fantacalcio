'use strict';

/**
 * @ngdoc controller
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */

angular.module('fantacalcioApp')
.controller('HeaderController', function ($scope, $location, $state, Auth, Principal, ENV) {
    $scope.isAuthenticated = Principal.isAuthenticated;
    $scope.$state = $state;
    $scope.inProduction = ENV === 'prod';
});

