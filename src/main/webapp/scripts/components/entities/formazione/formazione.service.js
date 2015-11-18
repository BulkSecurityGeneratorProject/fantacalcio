'use strict';

angular.module('fantacalcioApp')
    .factory('Formazione', function ($resource, DateUtils) {
        return $resource('api/formaziones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
