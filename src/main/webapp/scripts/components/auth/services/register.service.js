'use strict';

angular.module('fantacalcioApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


