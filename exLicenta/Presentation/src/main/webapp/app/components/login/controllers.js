/**
 * Created by Tabi on 04-May-17.
 */

angular.module('traducatori.controllers')

    .controller('loginCtrl', function($scope, $state, $http) {
        $scope.user = {};
        $scope.error = {};
        var delete_cookie = function(name) {
            document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        };
        delete_cookie("nume");
        $scope.login = function() {
          //  if($scope.user.email === "administrator@e-uvt.ro" && $scope.user.password === "aaa") {
          //      $state.go('main.users.display');
          //  } else {
          //      $scope.error.message = "Incorrect email or password";
          //  }
            
            
        	var datauser = {'username' : $scope.user.username, "password" : $scope.user.password};
        
            var config = {
                headers : {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
            };
        	
            console.log('password:'+ $scope.user.password)
        	console.log('username:' + $scope.user.username);
            $http.post('loginITRO', datauser, config).then(function(data, status, headers, config) {
               
            	console.log('try redirect: main.users.display');
                $state.go('main.users.display');
                
            },function(data) {
                $scope.error.message = "Incorrect email or password";
            });

            console.log("POST done");
        }
    });