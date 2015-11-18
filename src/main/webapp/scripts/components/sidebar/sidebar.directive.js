'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */

angular.module('fantacalcioApp')
  .directive('sidebar',['$location',function() {
    return {
      templateUrl:'scripts/components/sidebar/sidebar.html',
      restrict: 'E',
      replace: true,
      scope: {
      }
    }
  }]);
