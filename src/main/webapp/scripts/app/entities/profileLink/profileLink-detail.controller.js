'use strict';

angular.module('olisParamsAndScriptsApp')
    .controller('ProfileLinkDetailController', function ($scope, $rootScope, $stateParams, entity, ProfileLink, AGACUser, Profile) {
        $scope.profileLink = entity;
        $scope.load = function (id) {
            ProfileLink.get({id: id}, function(result) {
                $scope.profileLink = result;
            });
        };
        var unsubscribe = $rootScope.$on('olisParamsAndScriptsApp:profileLinkUpdate', function(event, result) {
            $scope.profileLink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
