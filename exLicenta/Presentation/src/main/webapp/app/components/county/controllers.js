/**
 * Created by Tabi on 26-Jul-17.
 */
angular.module('traducatori.controllers')
    .controller('countyCtrl', function($scope, $state, $http) {
        $scope.message = "County";       
        $http({
            method : 'GET',
            url : 'rest/judet/judete'
	    }).then(function(data) {
	    	 $scope.countys = data.data;
	    	 $scope.len = $scope.countys.length;
	    },function(data, status, headers, config) {
	    	window.location.href = "/itroAdmin/#!/login";
	    	requestError($scope.error, data.data, data.status);
	    });
    })
    .controller('displayCountyCtrl', function($scope, $state, $stateParams, $http) {
        console.log('displayCountyCtrl');
        $scope.deleteCounty = function(id, index) {
        	if (confirm("Are you sure you want to delete?")){
	        	$http({
	                method : 'DELETE',
	                url : 'rest/judet/mng/'+id
		        }).then(function(data) {
		             $scope.countys.splice(index, 1);
		        },function(data, status, headers, config) {
		        	requestErrorDelete(data.data, data.status);
		        });
        	}
        };

        $scope.editCounty = function(id,index) {
            $state.go('^.edit', {id:id, index:index});
        };
    })

    .controller('editCountyCtrl', function($scope, $state, $stateParams,$http) {
        //console.log($stateParams.id);
    	$http({
            method : 'GET',
            url : 'rest/tara/tari'
	    }).then(function(data) {
	    	 $scope.countries = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
    	
    	$scope.error = {};
    	var county_copy = {};
    	
    	
    	$http({
            method : 'GET',
            url : 'rest/judet/mng/'+$stateParams.id
        }).then(function(data) {
        	$scope.county=data.data;
        	console.log('edit: ',$scope.county);
        	angular.copy($scope.county, county_copy);
        },function(data, status, headers, config) {
        	requestError($scope.error, data.data, data.status);
        });

        $scope.resetData = function() {
            angular.copy(county_copy, $scope.county);
        };

        $scope.submit = function() {
        	var dataTara = {'id' : $stateParams.id, 'nume' : $scope.county.nume, 'tara' : $scope.county.tara};
        	console.log('params: all ', $scope.county, $scope.county.tara);
        	$http({
                method : 'PUT',
                url : 'rest/judet/mng',
                data : dataTara
            }).then(function(data) {
            	console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams, $scope.county, data.data);
            	 angular.copy(data.data, $scope.countys[ $stateParams.index]);
            	$state.go('^.display');
            },function(data, status, headers, config) {
            	requestError($scope.error, data.data, data.status);
            });
        };
    })
    .controller('insertCountyCtrl', function($scope, $state, $http) {

    	$http({
            method : 'GET',
            url : 'rest/tara/tari'
	    }).then(function(data) {
	    	 $scope.countries = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
    	
    	$scope.error = {};
        $scope.county = { "tara":{}};
        var county_copy = {};
        
        angular.copy($scope.county, county_copy);

        $scope.resetData = function() {
            angular.copy(county_copy, $scope.county);
        };

        $scope.submit = function() {
        	var dataTara = {'nume' : $scope.county.nume, 'tara' : $scope.county.tara};
        	console.log('params: all ', $scope.county, $scope.county.tara);

        	$http({
                method : 'POST',
                url : 'rest/judet/mng',
                data : dataTara
            }).then(function(data) {
            	$scope.countys.push(data.data);
            	console.log('insert', data.data);
            	$state.go('^.display');
            },function(data) {
            	requestError($scope.error, data.data, data.status);
            });

        };
    });