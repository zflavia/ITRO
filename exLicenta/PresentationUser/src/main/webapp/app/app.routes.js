angular.module("userView")
    .config(function($stateProvider, $urlRouterProvider, $translateProvider) {
        $translateProvider
            .useStaticFilesLoader({
                prefix: 'translations/',
                suffix: '.json'
            })
            .preferredLanguage('ro')
            .useLocalStorage()
            .useMissingTranslationHandlerLog();


$stateProvider
    .state('main', {
        abstract : true,
        url: '/main',
        templateUrl: 'app/components/main/templates/main.html',
        controller: 'mainCtrl'
    })
    .state('main.index', {
        url: '/index',
        templateUrl: 'app/components/main/templates/index.html',
        controller: 'indexMainCtrl'
    })
    .state('main.medalion', {
        url: '/medalion',
        templateUrl: 'app/components/medalion/templates/medalion.html',
        controller: 'medalionCtrl'
    })
    .state('main.traducator', {
        url: '/traducator/:id',
        templateUrl: 'app/components/traducator/templates/traducator.html',
        controller: 'traducatorSingleCtrl'
    })
    .state('main.contact', {
        url: '/contact',
        templateUrl: 'app/components/contact/templates/contact.html',
        controller: 'contactCtrl'
    });
        $urlRouterProvider.otherwise('/main/index');

        //$locationProvider.html5Mode(true);
    });



