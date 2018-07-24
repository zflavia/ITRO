angular.module('userView.controllers', [])
    .controller("mainCtrl", function($scope, $state, $translate ,$http) {
        console.log("main");

//        $scope.traducatori = [
//            {
//                id: 1,
//                firstName: "Petru",
//                lastName: "Pavel"
//            },
//            {
//                id: 2,
//                firstName: "Aron",
//                lastName: "Florian"
//            }
//        ];
        
        console.log("..traducatoriCtrl");
 	   
        $http({
                method : 'GET',
                url : 'traducatoriView'
        }).then(function(data) {
        	 $scope.traducatori = data.data;
        	 console.log("...............response: " , JSON.stringify(data) );
        	 console.log("...............traducatori: " , $scope.traducatori, " --- ",  data.data );
        },function(data, status, headers, config) {
        	console.log("...............traducatori wrong ",JSON.stringify(data));
        });
        
        $scope.letters = [
                          'A', 'B', 'C', 'D', 'E', 'F', 'G','H', 'I','J', 'K',
                          'L', 'M', 'N', 'O', 'P', 'Q', 'R','S', 'T','U', 'V',
                           'W', 'X', 'Y', 'Z'
                      ];
        $scope.changeLanguage = function (lang) {
            $translate.use(lang);
        };
        
    })
    .controller("indexMainCtrl",function ($scope, $state) {
        console.log("indexMain");

        $scope.traducatorLetter ="";
        $scope.traducatorFilter = "";

        $scope.filterByLetter = function(letter) {
        	$scope.filter =  letter;
        	$scope.traducatorLetter = letter;
        };

        

        $scope.display = function(val) {
        	console.log("...vizi filtru: ", val);
        }

    })
;