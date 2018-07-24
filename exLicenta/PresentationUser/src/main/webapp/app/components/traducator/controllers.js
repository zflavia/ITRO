angular.module('userView.controllers')
    .controller("traducatorSingleCtrl", function($scope, $stateParams, $state, $http, $sce,$translate) {

        console.log("... traducatorCtrl")

        function getTrad() {
            var id = 0;
            id = $stateParams.id;
            var datatraducator = {'id': id};
            console.log("...getTrad()", id, datatraducator);
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
        }

        getTrad();

        $scope.limbaPagina = $translate.use();
        console.log("limbaPagina==" + $scope.limbaPagina);

        if ($scope.limbaPagina === 'ro') {
        	$scope.traducatorFoto = 'Poza Traducator';
        	$scope.traducatorNume = 'Nume Traducator';
            $scope.traducatorListaPseudonime = 'Lista Pseudonime';
            $scope.traducatorAspecteBibliografice = 'Aspecte Bibliografice';
            $scope.traducatorAtributiiEditoriale = 'Atributii/Contributii editorial, sociale';
            $scope.traducatorActivitate = 'Activitate Traducator';
			$scope.traducatorListaTraduceri = 'Lista Traduceri';
			$scope.traducatorTraduceriManuscrise = 'Traduceri Manuscrise';
			$scope.traducatorEditiiVechi = 'Editii Vechi';
			$scope.traducatorTraduceriFaraIndicatii = 'Traduceri Fara Indicatii';
			$scope.traducatorEditiiModerne = 'Editii Moderne';
			$scope.traducatorDictionare = 'Dictionare';
			$scope.traducatorManuale = 'Manuale';
			$scope.traducatorBibliografie = 'Bibliografie';
			$scope.traducatorBibliografieActivitate = 'Bibliografie privind activitatea de traducere';
            $scope.traducatorPrezenteBDD = 'Prezente in BDD';
            $scope.traducatorPrelucrat = 'Prelucrat de';
            $scope.traducatorPreluat = 'Preluat de';
        }
        if ($scope.limbaPagina === 'en') {
			$scope.traducatorFoto =  'Translator Picture';
			$scope.traducatorNume = 'Translator Name';
            $scope.traducatorListaPseudonime = 'NickNames List';
            $scope.traducatorAspecteBibliografice = 'Bibliographic Aspects';
            $scope.traducatorAtributiiEditoriale = 'Attributions / Contributions Editorial, Social';
            $scope.traducatorActivitate = 'Translator Activity';
            $scope.traducatorListaTraduceri = 'List of Translations';
            $scope.traducatorTraduceriManuscrise = 'Translations Manuscripts';
            $scope.traducatorEditiiVechi = 'Old Editions';
            $scope.traducatorTraduceriFaraIndicatii = 'Translation Without Indications';
            $scope.traducatorEditiiModerne = 'Modern Editions';
            $scope.traducatorDictionare = 'Dictionaries';
            $scope.traducatorManuale = 'Manuals';
            $scope.traducatorBibliografie = 'Bibliography';
            $scope.traducatorBibliografieActivitate = 'Bibliography on translation';
            $scope.traducatorPrezenteBDD = 'Present in BDD';
            $scope.traducatorPrelucrat = 'Processed by';
            $scope.traducatorPreluat = 'Taken over by';
        }
        if ($scope.limbaPagina === 'fr')
		{
			$scope.traducatorFoto = 'Image du traducteur';
			$scope.traducatorNume = 'Nom du traducteur';
            $scope.traducatorListaPseudonime = 'Pseudonymis Liste';
            $scope.traducatorAspecteBibliografice = 'Aspects bibliographiques';
            $scope.traducatorAtributiiEditoriale = 'Attributions /Contributions Éditorial, Sociales';
            $scope.traducatorActivitate = 'Activité de traducteur';
            $scope.traducatorListaTraduceri = 'Liste des traductions';
            $scope.traducatorTraduceriManuscrise = 'Traductions Manuscrits';
            $scope.traducatorEditiiVechi = 'Anciennes modifications';
            $scope.traducatorTraduceriFaraIndicatii = 'Traductions sans indications';
            $scope.traducatorEditiiModerne = 'Editions Modernes';
            $scope.traducatorDictionare = 'Dictionnaires';
            $scope.traducatorManuale = 'Manuels';
            $scope.traducatorBibliografie = 'Bibliographie';
            $scope.traducatorBibliografieActivitate = 'Bibliographie sur la traduction';
            $scope.traducatorPrezenteBDD = 'Présent dans BDD';
            $scope.traducatorPrelucrat = 'Traité par';
            $scope.traducatorPreluat = 'Repris par';
        }
        $scope.renderHtml = function(html_code)
        {
            return $sce.trustAsHtml(html_code);
        };
    });