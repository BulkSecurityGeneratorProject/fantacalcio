'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('fantacalcioApp')
	.directive('header',function(){
		return {
        templateUrl:'scripts/components/header/header.html',
        restrict: 'E',
        replace: true,
    	}
	});


