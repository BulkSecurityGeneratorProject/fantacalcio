'use strict';

angular.module('fantacalcioApp')
    .factory('Giocatore', function ($resource, DateUtils) {
        return $resource('api/giocatores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.ultima_modifica = DateUtils.convertDateTimeFromServer(data.ultima_modifica);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
