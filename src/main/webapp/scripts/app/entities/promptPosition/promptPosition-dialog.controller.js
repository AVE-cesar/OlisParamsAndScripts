'use strict';

/**
 * AngularJS default filter with the following expression:
 * "person in people | filter: {name: $select.search, age: $select.search}"
 * performs a AND between 'name: $select.search' and 'age: $select.search'.
 * We want to perform a OR.
 */
angular.module('olisParamsAndScriptsApp').filter('propsFilter', function() {
  return function(items, props) {
    var out = [];

    if (angular.isArray(items)) {
      items.forEach(function(item) {
        var itemMatches = false;

        var keys = Object.keys(props);
        for (var i = 0; i < keys.length; i++) {
          var prop = keys[i];
          var text = props[prop].toLowerCase();
          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
            itemMatches = true;
            break;
          }
        }

        if (itemMatches) {
          out.push(item);
        }
      });
    } else {
      // Let the output be the input untouched
      out = items;
    }

    return out;
  }
});

angular.module('olisParamsAndScriptsApp').controller('PromptPositionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PromptPosition', 'Report', 'Prompt',
        function($scope, $stateParams, $modalInstance, entity, PromptPosition, Report, Prompt) {

        $scope.promptPosition = entity;
        $scope.reports = Report.query();
        $scope.prompts = Prompt.query();
        $scope.load = function(id) {
            PromptPosition.get({id : id}, function(result) {
                $scope.promptPosition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('olisParamsAndScriptsApp:promptPositionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.promptPosition.id != null) {
                PromptPosition.update($scope.promptPosition, onSaveSuccess, onSaveError);
            } else {
                PromptPosition.save($scope.promptPosition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
