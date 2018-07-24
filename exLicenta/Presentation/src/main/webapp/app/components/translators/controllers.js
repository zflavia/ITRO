/**
 * Created by Tabi on 01-May-17.
 */
angular.module('traducatori.controllers')
    .controller('translatorsCtrl', function ($scope, $http) {
        $scope.message = "Translators";

//        $scope.translators = [
//            {
//                id: 0,
//                name: "traducator 1"
//            }
//        ];
        $http({
            method : 'GET',
            url : 'traducatoriView'
    }).then(function(data) {
    	 $scope.translators = data.data;
    	 console.log("...............response: " , data );
    	 console.log("...............traducatori: " , $scope.translators );
    },function(data, status, headers, config) {
    	window.location.href = "/itroAdmin/#!/login";
    	console.log("...............traducatori wrong ");
    });


        console.log('translatorsCtrl');

        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };
        $scope.months = ['Ianuarie', 'Februarie', 'Martie', 'Aprilie', 'Mai', 'Iunie', 'Iulie', 'August', 'Septembrie',
            'Octombrie', 'Noiembrie', 'Decembrie'];
        $scope.days = [
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        ];

        $scope.range = function (min, step) {
            step = step || 1;
            var input = [];
            // console.log($scope.translator);
            // var month_index;
            //
            // if (typeof $scope.translator.month == "undefined" || $scope.translator.month == '') {
            //     month_index = 31;
            // } else {
            //     month_index = $scope.translator.month;
            // }
            // console.log("month index", month_index);

            // var max = $scope.days[month_index];
            // console.log(max);
            var max = 31;
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };


    })
    .controller('displayTranslatorsCtrl', function ($scope, $state) {
        console.log('displayTranslatorsCtrl');


        $scope.deleteTranslator = function (index) {
            $scope.translators.splice(index, 1);
        };

        $scope.editTranslator = function (id, index) {
            $state.go('^.edit', {id: id});
        };

        $scope.moreDetails = function(id, index) {
            $state.go('^.more-details', {id: id})
        }
        $scope.traducatorFilter = "";
    })

    .controller('editTranslatorsCtrl', function ($scope, $state, $stateParams, $timeout, $http) {
        //console.log($stateParams.id);
        $scope.goBack = function () {
            window.history.back();
        };
    	id = $stateParams.id;
        var datatraducator = {'id' : id};
        
        $scope.getUserName= function (user){
        	return user.lastName+ " "+user.firstName;
        }
        
        $scope.getLocality= function (locality){
        	var ret = locality.nume
        	if (locality.judet){
        		ret += ", " + locality.judet.nume
        		if (locality.judet.tara) ret += ", " + locality.judet.tara.nume;
        	}else{
        		if (locality.tara)
        			ret += ", " + locality.tara.nume;
        	}
        	return ret;
        }
    	
        $http({
            method : 'GET',
            url : 'rest/localitate/localitati'
	    }).then(function(data) {
	    	 $scope.localities = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
        
        $http({
            method : 'GET',
            url : 'rest/user/culegatori'
	    }).then(function(data) {
	    	 $scope.users = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
        //-------------------------------------- Pseudonime Stuff --------------------------
        $scope.getAllPseudonime = function(){
            $http({
                method: 'GET',
                url: 'rest/pseudonim/pseudonime/'
            }).then(function (data) {
                $scope.traducator.nicknames = data.data;
                console.log("$scope.traducator.pseudonime: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.newPseudonimeEntry = function () {
            $scope.pseudonimeEditIndex = -1;
            $scope.getAllPseudonime();
            $scope.pseudonimeEdit = {"alias": []};
        };

        $scope.editPseudonimeEntry = function (id, index) {
            $scope.pseudonimeEditIndex = index;
            console.log("Pseudonime ", index, $scope.pseudonimeEditIndex);
            $scope.getAllPseudonime();
            $http({
                method: 'GET',
                url: 'rest/pseudonim/pseudonime/' + id
            }).then(function (data) {
                //$scope.traducator.pseudonime = data.data;
                $scope.pseudonimeEdit = data.data;
                console.log("$scope.pseudonimeEdit: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };

        $scope.deletePseudonimeEntry = function (id, index) {
            console.log("Delete with id ", id, " index ", index);
            if (confirm("Are you sure you want to delete?")) {
                $http({
                    method: 'DELETE',
                    url: 'rest/pseudonim/pseudonime/' + id
                }).then(function (data) {
                    $scope.traducator.pseudonime.splice(index, 1);
                }, function (data, status, headers, config) {
                    requestErrorDelete(data.data, data.status);
                });
            }
        };
        $scope.submitPseudonimeModal = function () {
            console.log('save BDD: ', $scope.pseudonimeEdit)
            var json;
            console.log('json:', json);
            var method = '';
            console.log('save BDD: ', $scope.pseudonimeEdit.id)
            if ($scope.pseudonimeEdit.id) {
                method = 'POST';
                json = $scope.pseudonimeEdit;
            } else {
                method = 'PUT';
                json = {"alias": $scope.pseudonimeEdit.alias, "traducatorId": $scope.traducator.id};
            }
            console.log("..................methos: ", method, json, $scope.pseudonimeEditIndex)
            $http({
                method: method,
                url: 'rest/pseudonim/pseudonime/',
                data: json
            }).then(function (data) {
                console.log("DO UPDATE", data.data);
                $(document).ready(function () {
                    $('#pseudonimeModal').modal("hide");
                });
                if (method === "PUT") {
                    $scope.traducator.pseudonime.push(data.data)
                } else {
                    angular.copy(data.data, $scope.traducator.pseudonime[$scope.pseudonimeEditIndex]);
                }

                //console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
                //$scope.language[ $stateParams.index] = data.data;
                //$state.go('^.display');
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.removeAlias = function (id) {
            $scope.pseudonimeEdit.alias.splice(id, 1);
        };
        $scope.cancelPseudonimeModal = function () {
            //$scope.traducator.pseudonime = $scope.initialPseudonime;
        };
//--------------------------------------NickNames Stuff --------------------------
        $scope.editNickNamesEntry = function () {
            $scope.searchFilter = "";
            $http({
                method: 'GET',
                url: 'rest/pseudonim/pseudonime'
            }).then(function (data) {
                //$scope.traducator.pseudonime = data.data;
                $scope.initialPseudonime = data.data;
                console.log("$scope.pseudonime: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.cancelNickNamesModal = function () {
          //  $scope.traducator.pseudonime = $scope.initialPseudonime;
        };
        
    	$http({
			method : 'GET',
			url : 'traducator',
			params : datatraducator
		}).then(
				function(data) {
					$scope.traducator = data.data;
					console.log("...............traducator: ",
							$scope.traducator, " --- ", data);
				},
				function errorCallback(response) {
					console.log("...............traducator wrong ",
							response);
				});
    	
    	
//        var translators_copy = [];
//
//
//            angular.copy($scope.translators, translators_copy);
//
//        $scope.resetData = function () {
//            angular.copy(translators_copy, $scope.translators);
//        };
        $scope.submit = function () {

        	//uptate in DB
        	
var dataTraducator = {'type' : 'edit', 'id' : $stateParams.id, 'nume': $scope.traducator.nume, 'prenume': $scope.traducator.prenume,  
		'aspecteBibliografice': $scope.traducator.aspecteBibliografice, 'atributiiContributiiEditoriale': $scope.traducator.atributiiContributiiEditoriale, 
		'activitateTraducator' : $scope.traducator.activitateTraducator};
        	
        	console.log("update traducator:"+dataTraducator)
        	$http({
                method : 'POST',
                url : 'traducatorMng',
                data : dataTraducator
            }).then(function(data) {
            	// $scope.users = data.data;
            	 console.log("...............response updateTraducator: " , data );            	 
            	 $state.go('^.display')
            },function(data, status, headers, config) {
            	console.log("...............users wrong insert ");
            });
        	
            $state.go('^.display');
        };
    })
    .controller('insertTranslatorsCtrl', function ($scope, $state, $http) {
    	 $scope.goBack = function () {
             window.history.back();
         };
        $scope.translator = {};
        var translators_copy = [];
        
        $http({
            method : 'GET',
            url : 'rest/localitate/localitati'
	    }).then(function(data) {
	    	 $scope.localities = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
        
        $http({
            method : 'GET',
            url : 'rest/user/culegatori'
	    }).then(function(data) {
	    	 $scope.users = data.data;
	    },function(data, status, headers, config) {
	    	requestError($scope.error, data.data, data.status);
	    });
        
        $scope.getUserName= function (user){
        	return user.lastName+ " "+user.firstName;
        };
        
        $scope.getLocality= function (locality){
        	var ret = locality.nume;
        	if (locality.judet){
        		ret += ", " + locality.judet.nume;
        		if (locality.judet.tara) ret += ", " + locality.judet.tara.nume;
        	}else{
        		if (locality.tara)
        			ret += ", " + locality.tara.nume;
        	}
        	return ret;
        };

        angular.copy($scope.translators, translators_copy);


        $scope.resetData = function () {
            angular.copy(translators_copy, $scope.translators);
        };

        $scope.submit = function () {
            $scope.translator.id = $scope.translators[$scope.translators.length - 1].id + 1;
            $scope.translators.push($scope.translator);
            console.log($scope.translator);

            $state.go('^.display');
        };
        $scope.goBack = function () {
            window.history.back();
        };
    })
    .controller('moreDetailsCtrl', function($scope, $state, $stateParams, $timeout, $http, $sce) {
        $scope.traducator = {};
        $scope.prezenteBDDEdit = {};
        $scope.bibliografieBBEdit = {};
        var id = $stateParams.id;
        var datatraducator = {'id': id};

        $scope.goBack = function () {
            window.history.back();
        };
        $scope.renderHtml = function (html_code) {
            return $sce.trustAsHtml(html_code);
        };
        console.log(datatraducator);
        $http({
            method: 'GET',
            url: 'traducator',
            params: datatraducator
        }).then(
            function (data) {
                $scope.traducator = data.data;
                console.log("...............traducator: ",
                    $scope.traducator, " --- ", data);
            },
            function errorCallback(response) {
                console.log("...............traducator wrong ",
                    response);
            });
        $scope.urlPattern = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;

        $scope.editTraducereEntry = function () {
            console.log("Lista Traducere ");
        }
//-------------------------------------- Author Stuff --------------------------
        $scope.editAuthorEntry = function () {
            $scope.searchFilter = "";
            $http({
                method: 'GET',
                url: 'rest/autor/autori'
            }).then(function (data) {
                $scope.authors = data.data;
                $scope.initialAuthors = data.data;
                console.log("$scope.authors: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.cancelAutorModal = function () {
            $scope.authors = $scope.initialAuthors;
        };

        $scope.removeAuthor = function (id) {
            $scope.bibliografieEdit.autori.splice(id, 1);
        };

//-------------------------------------- Lista traduceri Stuff --------------------------
        $scope.newListaTraduceri = function () {
            $scope.listaTraduceriEditIndex = -1;
            $scope.listaTraduceriEdit = {"autori": []};
        };

        $scope.editTraducereEntry = function (id, index, type) {
            $scope.listaTraduceriEditIndex = index;
            console.log("Lista traduceri  ", index, $scope.listaTraduceriEdit, type);
            var _url = '';
            if (type === 'tradManuscript') {
                _url = 'rest/traducator/tradManuscript/' + id;
                $scope.listaTradEditTip = 'tradManuscript';
                $scope.loadListaTradTip = 'tradManuscript';
            } else if (type === 'edVeche') {
                _url = 'rest/traducator/edVeche/' + id;
                $scope.listaTradEditTip = 'edVeche';
                $scope.loadListaTradTip = 'edVeche';
            } else if (type === 'tradFaraIndicatii') {
                _url = 'rest/traducator/tradFaraIndicatii/' + id;
                $scope.listaTradEditTip = 'tradFaraIndicatii';
                $scope.loadListaTradTip = 'tradFaraIndicatii';
            } else if (type === 'tradModerna') {
                _url = 'rest/traducator/tradModerna/' + id;
                $scope.listaTradEditTip = 'tradModerna';
                $scope.loadListaTradTip = 'tradModerna';
            } else if (type === 'manual') {
                _url = 'rest/traducator/manual/' + id;
                $scope.listaTradEditTip = 'manual';
                $scope.loadListaTradTip = 'manual';
            } else {
                _url = 'rest/traducator/dictionar/' + id;
                $scope.listaTradEditTip = 'dictionar';
                $scope.loadListaTradTip = 'dictionar';
            }
            $http({
                method: 'GET',
                url: _url,
            }).then(function (data) {
                $scope.listaTraduceriEdit = data.data;
                console.log("$scope.listaTraduceriEdit: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.deleteTraducereEntry = function (id, index, type) {
            console.log("Delete with id ", id, " index ", index);
            if (confirm("Are you sure you want to delete?")) {
                var _url = '';
                if (type === 'tradManuscript') {
                    _url = 'rest/traducator/tradManuscript/' + id;
                } else if (type === 'edVeche') {
                    _url = 'rest/traducator/edVeche/' + id;
                } else if (type === 'tradFaraIndicatii') {
                    _url = 'rest/traducator/tradFaraIndicatii/' + id;
                } else if (type === 'tradModerna') {
                    _url = 'rest/traducator/tradModerna/' + id;
                } else if (type === 'manual') {
                    _url = 'rest/traducator/manual/' + id;
                } else {
                    _url = 'rest/traducator/dictionar/' + id;
                }
                $http({
                    method: 'DELETE',
                    url: _url
                }).then(function (data) {
                    if (type === 'tradManuscript') {
                        $scope.traducator.traduceriManuscrise.splice(index, 1);
                    } else if (type === 'edVeche') {
                        $scope.traducator.editiiVechi.splice(index, 1);
                    } else if (type === 'tradFaraIndicatii') {
                        $scope.traducator.traduceriFaraIndicatii.splice(index, 1);
                    } else if (type === 'tradModerna') {
                        $scope.traducator.editiiModerne.splice(index, 1);
                    } else if (type === 'dictionar') {
                        $scope.traducator.dictionare.splice(index, 1);
                    } else {
                        $scope.traducator.manuale.splice(index, 1);
                    }
                }, function (data, status, headers, config) {
                    requestErrorDelete(data.data, data.status);
                });
            }
        };
        $scope.removeAuthor = function (id) {
            $scope.listaTraduceriEdit.autori.splice(id, 1);
        };
        $scope.submitListaTraduceri = function () {
            console.log('save ListaTraduceri: ', $scope.listaTraduceriEdit)
            var json;
            console.log('json:', json);
            var method = '';
            console.log('save ListaTraduceri: ', $scope.listaTraduceriEdit.id)
            if ($scope.listaTraduceriEdit.id) {
                method = 'POST';
                json = $scope.listaTraduceriEdit;
            } else {
                method = 'PUT';
                json = {
                    "titlu": $scope.listaTraduceriEdit.titlu,
                    "an": $scope.listaTraduceriEdit.an,
                    "detalii": $scope.listaTraduceriEdit.detalii,
                    "traducatorId": $scope.traducator.id,
                    "autori": $scope.listaTraduceriEdit.autori
                };
            }

            var _url = "";
            if (listaTradEditTip === 'tradManuscript') {
                _url = 'rest/traducator/tradManuscript/' + id;
            } else if (listaTradEditTip === 'edVeche') {
                _url = 'rest/traducator/edVeche/' + id;
            } else if (listaTradEditTip === 'tradFaraIndicatii') {
                _url = 'rest/traducator/tradFaraIndicatii/' + id;
            } else if (listaTradEditTip === 'tradModerna') {
                _url = 'rest/traducator/tradModerna/' + id;
            } else if (listaTradEditTip === 'manual') {
                _url = 'rest/traducator/manual/' + id;
            } else {
                _url = 'rest/traducator/dictionar/' + id;
            }

            console.log("..................methos: ", method, json, $scope.listaTraduceriEditIndex, _url, $scope.listaTradEditTip)
            $http({
                method: method,
                url: _url,
                data: json
            }).then(function (data) {
                console.log("DO UPDATE", data.data);
                $(document).ready(function () {
                    $('#listaTraduceriModal').modal("hide");
                });
                if (method === "PUT") {
                    if ($scope.listaTradEditTip === 'tradManuscript') {
                        $scope.traducator.traduceriManuscrise.push(data.data)
                    } else if ($scope.listaTradEditTip === 'edVeche') {
                        $scope.traducator.editiiVechi.push(data.data)
                    } else if ($scope.listaTradEditTip === 'tradFaraIndicatii') {
                        $scope.traducator.traduceriFaraIndicatii.push(data.data)
                    } else if ($scope.listaTradEditTip === 'tradModerna') {
                        $scope.traducator.editiiModerne.push(data.data)
                    } else if ($scope.listaTradEditTip === 'dictionar') {
                        $scope.traducator.dictionare.push(data.data)
                    } else if ($scope.listaTradEditTip === 'manuale') {
                        $scope.traducator.manuale.push(data.data)
                    }
                } else {
                    if ($scope.listaTradEditTip === 'tradManuscript') {
                        console.log('tradManuscript....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.traduceriManuscrise[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove tradManuscript add to listaTraduceri');
                            $scope.traducator.traduceriManuscrise.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else if ($scope.listaTradEditTip === 'edVeche') {
                        console.log('EdVeche....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.editiiVechi[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove edVeche add to listaTraduceri');
                            $scope.traducator.editiiVechi.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else if ($scope.listaTradEditTip === 'tradFaraIndicatii') {
                        console.log('tradFaraIndicatii....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.traduceriFaraIndicatii[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove tradFaraIndicatii add to listaTraduceri');
                            $scope.traducator.traduceriFaraIndicatii.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else if ($scope.listaTradEditTip === 'tradModerna') {
                        console.log('tradModerna....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.editiiModerne[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove tradModerna add to listaTraduceri');
                            $scope.traducator.editiiModerne.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else if ($scope.listaTradEditTip === 'dictionar') {
                        console.log('dictionar....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.dictionare[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove dictionar add to listaTraduceri');
                            $scope.traducator.dictionare.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else if ($scope.listaTradEditTip === 'manuale') {
                        console.log('manuale....', $scope.loadListaTradTip, $scope.listaTradEditTip, $scope.loadListaTradTip === $scope.listaTradEditTip)
                        if ($scope.listaTradEditTip === $scope.listaTradEditTip)
                            angular.copy(data.data, $scope.traducator.manuale[$scope.listaTraduceriEditIndex]);
                        else {
                            console.log('remove manuale add to listaTraduceri');
                            $scope.traducator.manuale.splice($scope.listaTraduceriEditIndex, 1);
                            // $scope.traducator.bibliografieBPAT.push(data.data);
                        }

                    }
                }

                //console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
                //$scope.language[ $stateParams.index] = data.data;
                //$state.go('^.display');
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
//-------------------------------------- Bibliografie Stuff -----------------------------
        $scope.newBibliographyEntry = function () {
            $scope.bibliografieEditIndex = -1;
            $scope.bibliografieEdit = {"autori": []};
        };

        $scope.editBibliographyEntry = function (id, index, type) {
            $scope.bibliografieEditIndex = index;
            console.log("Bibliografie  ", index, $scope.bibliografieEditIndex, type);
            var _url = '';
            if (type === 'B') {
                _url = 'rest/traducator/biblioBEntry/' + id;
                $scope.bibliografieEditTip = 'bibliografie';
                $scope.loadBibliografieTip = 'bibliografie';
            } else {
                _url = 'rest/traducator/biblioBPATEntry/' + id;
                $scope.bibliografieEditTip = 'bibliografieBPAT';
                $scope.loadBibliografieTip = 'bibliografieBPAT';
            }
            $http({
                method: 'GET',
                url: _url,
            }).then(function (data) {
                $scope.bibliografieEdit = data.data;
                console.log("$scope.bibliografieEdit: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.deleteBibliographyEntry = function (id, index, type) {
            console.log("Delete with id ", id, " index ", index);
            if (confirm("Are you sure you want to delete?")) {
                var _url = '';
                if (type === 'B')
                    _url = 'rest/traducator/biblioBEntry/' + id;
                else
                    _url = 'rest/traducator/biblioBPATEntry/' + id;
                $http({
                    method: 'DELETE',
                    url: _url
                }).then(function (data) {
                    if (type === 'B')
                        $scope.traducator.bibliografie.splice(index, 1);
                    else
                        $scope.traducator.bibliografieBPAT.splice(index, 1);
                }, function (data, status, headers, config) {
                    requestErrorDelete(data.data, data.status);
                });
            }
        };
        $scope.submitBibliographyModal = function () {
            console.log('save BDD: ', $scope.bibliografieEdit)
            var json;
            console.log('json:', json);
            var method = '';
            console.log('save bibibliografie: ', $scope.bibliografieEdit.id)
            if ($scope.bibliografieEdit.id) {
                method = 'POST';
                json = $scope.bibliografieEdit;
            } else {
                method = 'PUT';
                json = {
                    "titlu": $scope.bibliografieEdit.titlu,
                    "an": $scope.bibliografieEdit.an,
                    "url": $scope.bibliografieEdit.url,
                    "detalii": $scope.bibliografieEdit.detalii,
                    "traducatorId": $scope.traducator.id,
                    "autori": $scope.bibliografieEdit.autori
                };
            }

            var _url = ""
            if ($scope.bibliografieEditTip === 'bibliografieBPAT') {
                _url = 'rest/traducator/biblioBPATEntry';
            } else {
                _url = 'rest/traducator/biblioBEntry';
            }
            console.log("..................methos: ", method, json, $scope.bibliografieEditIndex, _url, $scope.bibliografieEditTip)
            $http({
                method: method,
                url: _url,
                data: json
            }).then(function (data) {
                console.log("DO UPDATE", data.data);
                $(document).ready(function () {
                    $('#bibliografieModal').modal("hide");
                });
                if (method === "PUT") {
                    if ($scope.bibliografieEditTip === 'bibliografieBPAT') {
                        $scope.traducator.bibliografieBPAT.push(data.data)
                    } else {
                        $scope.traducator.bibliografie.push(data.data)
                    }
                } else {
                    if ($scope.bibliografieEditTip === 'bibliografieBPAT') {
                        console.log('bpat....', $scope.loadBibliografieTip, $scope.bibliografieEditTip, $scope.loadBibliografieTip === $scope.bibliografieEditTip)
                        if ($scope.loadBibliografieTip === $scope.bibliografieEditTip)
                            angular.copy(data.data, $scope.traducator.bibliografieBPAT[$scope.bibliografieEditIndex]);
                        else {
                            console.log('remove bpat add to bibliografie');
                            $scope.traducator.bibliografie.splice($scope.bibliografieEditIndex, 1);
                            $scope.traducator.bibliografieBPAT.push(data.data);
                        }
                    } else {
                        console.log('b....', $scope.loadBibliografieTip, $scope.bibliografieEditTip, $scope.loadBibliografieTip === $scope.bibliografieEditTip)
                        if ($scope.loadBibliografieTip === $scope.bibliografieEditTip)
                            angular.copy(data.data, $scope.traducator.bibliografie[$scope.bibliografieEditIndex]);
                        else {
                            console.log('remove bibliografie add to bpat');
                            $scope.traducator.bibliografieBPAT.splice($scope.bibliografieEditIndex, 1);
                            $scope.traducator.bibliografie.push(data.data);

                        }
                    }
                }

                //console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
                //$scope.language[ $stateParams.index] = data.data;
                //$state.go('^.display');
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
//-------------------------------------- BDD Stuff --------------------------
        $scope.newPrezenteDBB = function () {
            $scope.prezenteBDDEdit = {}
        }
        $scope.bddEditIndex = -1;
        $scope.editPrezenteBDDEntry = function (id, index) {
            $scope.bddEditIndex = index;
            console.log("Prezente BDD  ", index, $scope.bddEditIndex);

            $http({
                method: 'GET',
                url: 'rest/traducator/bddEntry/' + id
            }).then(function (data) {
                $scope.prezenteBDDEdit = data.data;
                //angular.copy($scope.language, language_copy);
                console.log("$scope.prezenteBDDEdit: ", data.data);
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };

        $scope.submitPrezenteBDDModal = function () {
            console.log('save BDD: ', $scope.prezenteBDDEdit)
            var json;
            console.log('json:', json);
            var method = '';
            console.log('save BDD: ', $scope.prezenteBDDEdit.id)
            if ($scope.prezenteBDDEdit.id) {
                method = 'POST';
                json = $scope.prezenteBDDEdit;
            } else {
                method = 'PUT';
                json = {
                    "url": $scope.prezenteBDDEdit.url,
                    "detalii": $scope.prezenteBDDEdit.detalii,
                    "traducatorId": $scope.traducator.id
                };
            }
            console.log("..................methos: ", method, json, $scope.bddEditIndex)
            $http({
                method: method,
                url: 'rest/traducator/bddEntry',
                data: json
            }).then(function (data) {
                console.log("DO UPDATE", data.data);
                $(document).ready(function () {
                    $('#prezenteBDDModal').modal("hide");
                });
                if (method === "PUT") {
                    $scope.traducator.vizibilitateBDD.push(data.data)
                } else {
                    angular.copy(data.data, $scope.traducator.vizibilitateBDD[$scope.bddEditIndex]);
                }

                //console.log('$stateParams.index', $stateParams.index, $stateParams.id, $stateParams);
                //$scope.language[ $stateParams.index] = data.data;
                //$state.go('^.display');
            }, function (data, status, headers, config) {
                requestError($scope.error, data.data, data.status);
            });
        };
        $scope.deletePrezenteBDDEntry = function (id, index) {
            console.log("Delete with id ", id, " index ", index);
            if (confirm("Are you sure you want to delete?")) {
                $http({
                    method: 'DELETE',
                    url: 'rest/traducator/bddEntry/' + id
                }).then(function (data) {
                    $scope.traducator.vizibilitateBDD.splice(index, 1);
                }, function (data, status, headers, config) {
                    requestErrorDelete(data.data, data.status);
                });
            }
        };

    });


