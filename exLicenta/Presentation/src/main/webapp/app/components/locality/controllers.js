/**
 * Created by Tabi on 29-Jul-17.
 */

angular.module('traducatori.controllers')
    .controller('localityCtrl', function($scope, $state,$http) {
        $scope.message = "Locality";


        console.log("localityCtrl");

        $http({
            method : 'GET',
            url : 'rest/localitate/localitati'
	    }).then(function(data) {
	    	 $scope.localitys = data.data;
	    	 console.log($scope.localitys);
	    	 $scope.len = $scope.localitys.length;
	    },function(data, status, headers, config) {
	    	window.location.href = "/itroAdmin/#!/login";
	    	requestError($scope.error, data.data, data.status);
	    });
        
        //functia se repata in traducator ar trebui scoasa cumva
        $scope.getTara= function (judet, tara){
        	var ret="";
        	if (judet){
        		ret += judet.nume
        		if (judet.tara) ret += ", " + judet.tara.nume;
        		return ret;
        	}else{
        		if (tara)
        			return tara.nume;
        	}
        }
    })
    .controller('displayLocalityCtrl', function($scope, $state, $http) {

        console.log('displayLocalityCtrl');

        $scope.deleteLocality = function(id, index) {
        	if (confirm("Are you sure you want to delete?")){
	        	$http({
	                method : 'DELETE',
	                url : 'rest/localitate/mng/'+id
		        }).then(function(data) {
		             $scope.localitys.splice(index, 1);
		        },function(data, status, headers, config) {
		        	requestErrorDelete(data.data, data.status);
		        });
        	}
        };

        $scope.editLocality = function(id, index) {
            $state.go('^.edit', {id:id, index:index });
        };
        $scope.localitateFilter = "";
    })

    .controller('editLocalityCtrl', function($scope, $state, $stateParams, $http) {
    	$scope.goBack = function () {
            window.history.back();
        };
        console.log($stateParams.index);
    	$scope.error = {};
        var locality_copy = {};
        
        $http({
            method : 'GET',
            url : 'rest/tara/tari'
	    }).then(function(data) {
	    	 $scope.countries = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
        
        $http({
            method : 'GET',
            url : 'rest/localitate/mng/'+$stateParams.id
        }).then(function(data) {
        	$scope.locality=data.data;
        	console.log('edit: ',$scope.locality);
        	angular.copy($scope.locality, locality_copy);
        	
        	if ($scope.locality.judet){
        		$http({
                    method : 'GET',
                    url : 'rest/judet/judete/'+$scope.locality.tara.id
        	    }).then(function(data) {
        	    	 $scope.counties = data.data;
        	    },function(data, status, headers, config) {
        	    	requestError($scope.error, data.data, data.status);
        	    });
        	}
        },function(data, status, headers, config) {
        	requestError($scope.error, data.data, data.status);
        });
        

        $scope.onChanged = function() {
        	//alert($scope.locality.tara.id);
        	$http({
                method : 'GET',
                url : 'rest/judet/judete/'+$scope.locality.tara.id
    	    }).then(function(data) {
    	    	 $scope.counties = data.data;
    	    },function(data, status, headers, config) {
    	    	requestError($scope.error, data.data, data.status);
    	    });
        }
        
//        angular.copy($scope.localitys, localitys_copy);
//        console.log('locality id: ', $stateParams.id);
//        $scope.id = $stateParams.id;
//
//        console.log($scope.localitys[$scope.id]);
        $scope.resetData = function() {
            angular.copy(localitys_copy, $scope.localitys);
        };

        $scope.submit = function() {
        	var dataTara = {'id' : $stateParams.id, 'nume' : $scope.locality.nume, 'tara' : $scope.locality.tara, 'judet' : $scope.locality.judet};
        	console.log('params: all ', $scope.locality, $scope.locality.tara);
        	$http({
                method : 'PUT',
                url : 'rest/localitate/mng',
                data : dataTara
            }).then(function(data) {
            	console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams, $scope.locality, data.data);
            	 angular.copy(data.data, $scope.localitys[ $stateParams.index]);
            	$state.go('^.display');
            },function(data, status, headers, config) {
            	requestError($scope.error, data.data, data.status);
            });
        };
    })
    .controller('insertLocalityCtrl', function($scope, $state, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
    	$scope.error = {};
        $scope.locality = {};//{"tara":{}, "judet":{}};
        var localitys_copy = [];
        
        $http({
            method : 'GET',
            url : 'rest/tara/tari'
	    }).then(function(data) {
	    	 $scope.countries = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });

        $scope.onChanged = function() {
        	//alert($scope.locality.tara.id);
        	$http({
                method : 'GET',
                url : 'rest/judet/judete/'+$scope.locality.tara.id
    	    }).then(function(data) {
    	    	 $scope.counties = data.data;
    	    },function(data, status, headers, config) {
    	    	requestError($scope.error, data.data, data.status);
    	    });
        }
        var locality_copy = {};
        angular.copy($scope.locality, locality_copy);

        $scope.resetData = function() {
            angular.copy(locality_copy, $scope.locality);
        };

        $scope.submit = function() {
        	var dataTara = {'nume' : $scope.locality.nume, 'tara' : $scope.locality.tara, 'judet' : $scope.locality.judet};
        	console.log('params: all ', $scope.locality, $scope.locality.tara);

        	$http({
                method : 'POST',
                url : 'rest/localitate/mng',
                data : dataTara
            }).then(function(data) {
            	$scope.localitys.push(data.data);
            	console.log('insert', data.data);
            	$state.go('^.display');
            },function(data) {
            	requestError($scope.error, data.data, data.status);
            });
        };
    });


