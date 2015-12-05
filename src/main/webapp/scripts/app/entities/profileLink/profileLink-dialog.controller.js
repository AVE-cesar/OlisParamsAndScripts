'use strict';

angular.module('olisParamsAndScriptsApp').controller('ProfileLinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProfileLink', 'AGACUser', 'Profile',
        function($scope, $stateParams, $modalInstance, entity, ProfileLink, AGACUser, Profile) {

        $scope.profileLink = entity;
        $scope.agacusers = AGACUser.query();
        $scope.profiles = Profile.query();
        $scope.load = function(id) {
            ProfileLink.get({id : id}, function(result) {
                $scope.profileLink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:profileLinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.profileLink.id != null) {
                ProfileLink.update($scope.profileLink, onSaveSuccess, onSaveError);
            } else {
                ProfileLink.save($scope.profileLink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
