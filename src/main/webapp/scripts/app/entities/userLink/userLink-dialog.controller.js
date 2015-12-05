'use strict';

angular.module('olisParamsAndScriptsApp').controller('UserLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UserLink', 'Organization', 'AGACUser',
        function($scope, $stateParams, $modalInstance, entity, UserLink, Organization, AGACUser) {

        $scope.userLink = entity;
        $scope.organizations = Organization.query();
        $scope.agacusers = AGACUser.query();
        $scope.load = function(id) {
            UserLink.get({id : id}, function(result) {
                $scope.userLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:userLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userLink.id != null) {
                UserLink.update($scope.userLink, onSaveSuccess, onSaveError);
            } else {
                UserLink.save($scope.userLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
