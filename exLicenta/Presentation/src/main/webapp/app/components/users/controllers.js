angular.module('traducatori.controllers')
    .controller('usersCtrl', function($scope, $state, $http) {
    $scope.message = "User";

    console.log("..usersCtrl");
    	   
        $http({
                method : 'GET',
                url : 'userMng'
        }).then(function(data) {
        	 $scope.users = data.data;
        	 console.log("...............response: " , data );
        	 console.log("...............users: " , $scope.users, " --- ",  data.data );
        },function(data, status, headers, config) {
        	window.location.href = "/itroAdmin/#!/login";
        	console.log("...............users wrong ");
        });

    })
    .controller('displayUsersCtrl', function($scope, $state, $http, $q) {

        console.log('..displayUsersCtrl');

        $scope.deleteUser = function(id, index) {
        	console.log("...delete");
           
        	var datauser = {'type' : 'delete', 'id' : id};
        	
        	$http({
                method : 'POST',
                url : 'userMng',
                data : datauser
	        }).then(function(data) {
	        	// $scope.users = data.data;
	        	 console.log("...............response delete: " , data );
	        	 console.log("...............users delete: " , $scope.users, " --- ",  data.data );
	        	 //$state.go('main.users.display');
	        	 var index = $scope.users.indexOf(id);
	             $scope.users.splice(index, 1);
	        },function(data, status, headers, config) {
	        	console.log("...............users wrong ");
	        });
        	
        };

        
        
//        $scope.editUserAux = function(id) {
//        	var deferred = $q.defer();
//        	var datauser = {'type' : 'edit', 'id' : id};
//        	
//        	$scope.selectedUser={}
//        	$http({
//                method : 'POST',
//                url : 'userMng',
//                data : datauser
//	        }).then(function(data) {
//	        	 deferred.resolve(data.data)
//	        	
//	        	 //$scope.$apply();
//	        	// console.log("...............response update: " , data );
//	        	 //console.log("...............user to update: " , $scope.selectedUser);
//	        	// $state.go('^.edit', {id: id});
//	        },function(data, status, headers, config) {
//	        	console.log("...............users wrong ");
//	        	 deferred.resolve(status)
//	        });
//        	 console.log("...............user to update after request: " , $scope.selectedUser);
//        	 return deferred.promise;
//        }
//        
        $scope.editUser = function(id) {
        	 
//        	var myPromise = $scope.editUserAux(id);
//
//            // wait until the promise return resolve or eject
//            //"then" has 2 functions (resolveFunction, rejectFunction)
//            myPromise.then(function(resolve){
//                console.log("fct sincron: ", resolve );
//                $scope.selectedUser=resolve;
//                //$scope.selectedUser=resolve.data;
//                $state.go('^.edit', {id: id});
//                }, function(reject){
//                	console.log("fct sincron -reject-: ", reject );  
//            });
        	 $state.go('^.edit', {id: id});
        }
    })

    .controller('editUserCtrl', function($scope, $state, $stateParams, $http) {
    	$scope.goBack = function () {
            window.history.back();
        };
    	
    	var id =  $stateParams.id;
    	
    	console.log("!!!!!!!!!!!!!!editUserCtrl",id);
    	var datauser = {'type' : 'edit', 'id' : id};
    	$http({
            method : 'POST',
            url : 'userMng',
            data : datauser
        }).then(function(data) {
        	// deferred.resolve(data.data)
        	$scope.selectedUser=data.data;
        	 //$scope.$apply();
        	// console.log("...............response update: " , data );
        	 //console.log("...............user to update: " , $scope.selectedUser);
        	// $state.go('^.edit', {id: id});
        },function(data, status, headers, config) {
        	console.log("...............users wrong ");
        	 deferred.resolve(status)
        });
    	
        //console.log($stateParams.id);
        var users_copy = [];

//        angular.copy($scope.users, users_copy);
//        console.log('user id: ', $stateParams.id);
//        $scope.id = $stateParams.id;


        $scope.resetData = function() {
            angular.copy(users_copy, $scope.users);
        };

        $scope.submit = function() {
            $state.go('^.display')
        }
    })
    .controller('insertUserCtrl', function($scope, $state, $http
    		) {

       console.log("....displayInser");
       $scope.user = {};
       $scope.goBack = function () {
           window.history.back();
       };
    	
    	
    	var users_copy = [];

        angular.copy($scope.users, users_copy);


        $scope.resetData = function() {
            angular.copy(users_copy, $scope.users);
        };

        $scope.submit = function() {
        	
        	console.log("............submit insert",$scope);
        	console.log("pass: ",$scope.user.password);
        	console.log("firstName: ",$scope.user.firstName);
        	console.log("lastName: ",$scope.user.lastName);
        	console.log("userName: ",$scope.user.userName);
        	console.log("alias: ",$scope.user.alias);
        	console.log("alias: ",$scope.user.email);
        	
        	var datauserinsert = {'type' : 'insert', 'firstName': $scope.user.firstName, 'lastName': $scope.user.lastName,  'userName': $scope.user.userName, 'password': $scope.user.password, 'alias' : $scope.user.alias, 'email': $scope.user.email};
        	
        	console.log("insert data:"+datauserinsert)
        	$http({
                method : 'POST',
                url : 'userMng',
                data : datauserinsert
            }).then(function(data) {
            	// $scope.users = data.data;
            	 console.log("...............response insert: " , data );
            	 console.log("...............users insert: " , $scope.users, " --- ",  data.data );
            	 
            	 $state.go('^.display')
            },function(data, status, headers, config) {
            	console.log("...............users wrong insert ");
            });
        	
        	
           
        }
    })
;