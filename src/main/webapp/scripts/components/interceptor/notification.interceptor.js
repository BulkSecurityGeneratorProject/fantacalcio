 'use strict';

angular.module('fantacalcioApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-fantacalcioApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-fantacalcioApp-params')});
                }
                return response;
            }
        };
    });
