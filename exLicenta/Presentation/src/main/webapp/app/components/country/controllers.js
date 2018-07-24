/**
 * Created by Tabi on 26-Jul-17.
 */
angular.module('traducatori.controllers')
    .controller('countryCtrl', function($scope, $state, $http) {
        $scope.message = "Country";        
        $http({
            method : 'GET',
            url : 'rest/tara/tari'
	    }).then(function(data) {
	    	 $scope.countrys = data.data;
	    	 $scope.len = $scope.countrys.length;
	    },function(data, status, headers, config) {
	    	window.location.href = "/itroAdmin/#!/login";
	    	requestError($scope.error, data.data, data.status);
	    });
        $scope.taraFilter = "";
    })
    .controller('displayCountryCtrl', function($scope, $state, $http) {
    	$scope.deleteCountry = function(id, index) { 
    		if (confirm("Are you sure you want to delete?")){
	        	$http({
	                method : 'DELETE',
	                url : 'rest/tara/mng/'+id
		        }).then(function(data) {
		             $scope.countrys.splice(index, 1);
		        },function(data, status, headers, config) {
		        	requestErrorDelete(data.data, data.status);
		        });
    		}
        };

        $scope.editCountry = function(id, index) {
        	console.log('index', index);
            $state.go('^.edit', {'id': id, 'index': index});
        };
        $scope.taraFilter = "";
    })

    .controller('editCountryCtrl', function($scope, $state, $stateParams, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
    	$scope.error = {};
    	
    	var country_copy = {};
    	
    	$http({
            method : 'GET',
            url : 'rest/tara/mng/'+$stateParams.id
        }).then(function(data) {
        	$scope.country=data.data;
        	angular.copy($scope.country, country_copy);
        },function(data, status, headers, config) {
        	requestError($scope.error, data.data, data.status);
        });
    	
        $scope.resetData = function() {
        	console.log('reset edit',$scope.country, country_copy);
            angular.copy(country_copy, $scope.country);
        };

        $scope.submit = function() {
        	
        	var dataTara = {'id' : $stateParams.id, 'nume' : $scope.country.nume};
        	
        	$http({
                method : 'PUT',
                url : 'rest/tara/mng',
                data : dataTara
            }).then(function(data) {
            	console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
            	$scope.countrys[ $stateParams.index] = data.data;
            	$state.go('^.display');
            },function(data, status, headers, config) {
            	requestError($scope.error, data.data, data.status);
                 
            });
        };
    })
    .controller('insertCountryCtrl', function($scope, $state, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
    	$scope.error = {};
        $scope.country = {};
        var countrys_copy = [];
        
        angular.copy($scope.countrys, countrys_copy);

        $scope.resetData = function() {
            angular.copy(countrys_copy, $scope.countrys);
        };

        $scope.submit = function() {
        	var dataTara = {'nume' : $scope.country.nume};
        	$http({
                method : 'POST',
                url : 'rest/tara/mng',
                data : dataTara
            }).then(function(data) {
            	$scope.countrys.push(data.data)
            	$state.go('^.display');
            },function(data) {
            	requestError($scope.error, data.data, data.status);
            });

        };
    });