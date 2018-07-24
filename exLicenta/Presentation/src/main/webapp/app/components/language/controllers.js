/**
 * Created by Tabi on 26-Jul-17.
 */
angular.module('traducatori.controllers')
    .controller('languageCtrl', function($scope, $state, $http) {
        $scope.message = "Language";
        $http({
            method : 'GET',
            url : 'rest/limba/limba'
        }).then(function(data) {
            $scope.languages = data.data;
            $scope.len = $scope.languages.length;
        },function(data, status, headers, config) {
        	window.location.href = "/itroAdmin/#!/login";
            requestError($scope.error, data.data, data.status);
        });
        $scope.limbaFilter = "";
    })
    .controller('displayLanguageCtrl', function($scope, $state, $http) {
        $scope.deleteLanguage = function(id, index) {
            if (confirm("Are you sure you want to delete?")){
                $http({
                    method : 'DELETE',
                    url : 'rest/limba/mng/'+id
                }).then(function(data) {
                    $scope.languages.splice(index, 1);
                },function(data, status, headers, config) {
                    requestErrorDelete(data.data, data.status);
                });
            }
        };

        $scope.editLanguage = function(id, index) {
            console.log('index', index);
            $state.go('^.edit', {'id': id, 'index': index});
        };
        $scope.limbaFilter = "";
    })

    .controller('editLanguageCtrl', function($scope, $state, $stateParams, $http) {
    	 	$scope.goBack = function () {
             window.history.back();
         };
        $scope.error = {};

        var language_copy = {};

        $http({
            method : 'GET',
            url : 'rest/limba/mng/'+$stateParams.id
        }).then(function(data) {
            $scope.language=data.data;
            angular.copy($scope.language, language_copy);
        },function(data, status, headers, config) {
            requestError($scope.error, data.data, data.status);
        });

        $scope.resetData = function() {
            console.log('reset edit',$scope.language, language_copy);
            angular.copy(language_copy, $scope.language);
        };

        $scope.submit = function() {

            var dataLanguage = {'id' : $stateParams.id, 'nume' : $scope.language.nume};

            $http({
                method : 'PUT',
                url : 'rest/limba/mng',
                data : dataLanguage
            }).then(function(data) {
                console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
                $scope.language[ $stateParams.index] = data.data;
                $state.go('^.display');
            },function(data, status, headers, config) {
                requestError($scope.error, data.data, data.status);

            });
        };
    })
    .controller('insertLanguageCtrl', function($scope, $state, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
        $scope.error = {};
        $scope.language = {};
        var languages_copy = [];

        angular.copy($scope.language, languages_copy);

        $scope.resetData = function() {
            angular.copy(languages_copy, $scope.language);
        };

        $scope.submit = function() {
            var dataLanguage = {'nume' : $scope.language.nume};
            $http({
                method : 'POST',
                url : 'rest/limba/mng',
                data : dataLanguage
            }).then(function(data) {
                $scope.language.push(data.data)
                $state.go('^.display');
            },function(data) {
                requestError($scope.error, data.data, data.status);
            });

        };
    });