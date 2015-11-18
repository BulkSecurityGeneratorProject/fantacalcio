'use strict';

angular.module('fantacalcioApp')
    .factory('FantaFormazione', function ($resource, DateUtils) {
        return $resource('api/fantaformazione/:id', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
