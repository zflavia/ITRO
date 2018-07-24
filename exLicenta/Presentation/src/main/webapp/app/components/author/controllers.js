
angular.module('traducatori.controllers')
    .controller('authorCtrl', function($scope, $state, $http) {
        $scope.message = "Author";        
        $http({
            method : 'GET',
            url : 'rest/autor/autori'
	    }).then(function(data) {
	    	 $scope.authors = data.data;
	    	 $scope.len = $scope.authors.length;
	    	 console.log("autori", data.data);
	    },function(data, status, headers, config) {
	    	window.location.href = "/itroAdmin/#!/login";
	    	requestError($scope.error, data.data, data.status);
	    });
    })
    .controller('displayAuthorCtrl', function($scope, $state, $http) {
    	$scope.deleteAuthor = function(id, index) { 
    		if (confirm("Are you sure you want to delete?")){
	        	$http({
	                method : 'DELETE',
	                url : 'rest/autor/mng/'+id
		        }).then(function(data) {
		             $scope.authors.splice(index, 1);
		        },function(data, status, headers, config) {
		        	requestErrorDelete(data.data, data.status);
		        });
    		}
        };

        $scope.editAuthor = function(id, index) {
        	console.log('index', index);
            $state.go('^.edit', {'id': id, 'index': index});
        };
        $scope.autorFilter = "";
    })

    .controller('editAuthorCtrl', function($scope, $state, $stateParams, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
    	$scope.error = {};
    	
    	var author_copy = {};
    	
    	$http({
            method : 'GET',
            url : 'rest/autor/mng/'+$stateParams.id
        }).then(function(data) {
        	$scope.author=data.data;
        	angular.copy($scope.author, author_copy);
        },function(data, status, headers, config) {
        	requestError($scope.error, data.data, data.status);
        });
    	
        $scope.resetData = function() {
        	console.log('reset edit',$scope.author, author_copy);
            angular.copy(author_copy, $scope.author);
        };

        $scope.submit = function() {
        	
        	var dataTara = {'id' : $stateParams.id, 'nume' : $scope.author.nume, 'prenume' : $scope.author.prenume, 'titlu' : $scope.author.titlu, 'detalii' : $scope.author.detalii};
        	
        	$http({
                method : 'PUT',
                url : 'rest/autor/mng',
                data : dataTara
            }).then(function(data) {
            	console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
            	$scope.authors[ $stateParams.index] = data.data;
            	$state.go('^.display');
            },function(data, status, headers, config) {
            	requestError($scope.error, data.data, data.status);
                 
            });
        };
    })
    .controller('insertAuthorCtrl', function($scope, $state, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
    	$scope.error = {};
        $scope.author = {};
        var authors_copy = [];
        
        angular.copy($scope.authors, authors_copy);

        $scope.resetData = function() {
            angular.copy(authors_copy, $scope.authors);
        };

        $scope.submit = function() {
        	var dataTara = {'nume' : $scope.author.nume, 'prenume' : $scope.author.prenume, 'titlu' : $scope.author.titlu, 'detalii' : $scope.author.detalii};
        	$http({
                method : 'POST',
                url : 'rest/autor/mng',
                data : dataTara
            }).then(function(data) {
            	$scope.authors.push(data.data)
            	$state.go('^.display');
            },function(data) {
            	requestError($scope.error, data.data, data.status);
            });

        };
    });