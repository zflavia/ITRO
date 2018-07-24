angular.module("traducatori")
    .config(function($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('main', {
            url: '/main',
            templateUrl: 'app/components/main/templates/main.html',
            controller: 'mainCtrl'
            })
        .state('main.users', {
            url: '/users',
            templateUrl: 'app/components/users/templates/users.html',
            controller: 'usersCtrl',
            abstract: true
        })
        .state('main.users.display', {
            url: '/display',
            templateUrl: 'app/components/users/templates/display.html',
            controller: 'displayUsersCtrl'
        })
        .state('main.users.edit', {
            url: '/edit/:id',
            templateUrl: 'app/components/users/templates/edit.html',
            controller: 'editUserCtrl'
        })
        .state('main.users.insert', {
            url: '/insert',
            templateUrl: 'app/components/users/templates/insert.html',
            controller: 'insertUserCtrl'
        })
        .state('main.translators', {
           url: '/translators',
            templateUrl: 'app/components/translators/templates/translators.html',
            controller: 'translatorsCtrl',
             abstract:true
        })
        .state('main.translators.display', {
            url: '/display',
            templateUrl: 'app/components/translators/templates/display.html',
            controller: 'displayTranslatorsCtrl'
        })
        .state('main.translators.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/translators/templates/modify.html',
            controller: 'editTranslatorsCtrl'
        })
        .state('main.translators.more-details', {
            url: '/more-details/:id/:index',
            templateUrl: 'app/components/translators/templates/more-details.html',
            controller: 'moreDetailsCtrl'
        })
        .state('main.translators.insert', {
            url: '/insert',
            templateUrl: 'app/components/translators/templates/modify.html',
            controller: 'insertTranslatorsCtrl'
        })
        .state('main.language', {
            url: '/language',
            templateUrl: 'app/components/language/templates/language.html',
            controller: 'languageCtrl'
        })
        .state('main.language.display', {
            url: '/display',
            templateUrl: 'app/components/language/templates/display.html',
            controller: 'displayLanguageCtrl'
        })
        .state('main.language.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/language/templates/modify.html',
            controller: 'editLanguageCtrl'
        })
        .state('main.language.insert', {
            url: '/insert',
            templateUrl: 'app/components/language/templates/modify.html',
            controller: 'insertLanguageCtrl'
        })
        .state('main.country', {
            url: '/country',
            templateUrl: 'app/components/country/templates/country.html',
            controller: 'countryCtrl',
            abstract: true
        })
        .state('main.country.display', {
            url: '/display',
            templateUrl: 'app/components/country/templates/display.html',
            controller: 'displayCountryCtrl'
        })
        .state('main.country.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/country/templates/modify.html',
            controller: 'editCountryCtrl'
        })
        .state('main.country.insert', {
            url: '/insert',
            templateUrl: 'app/components/country/templates/modify.html',
            controller: 'insertCountryCtrl'
        })
        .state('main.county', {
            url: '/county',
            templateUrl: 'app/components/county/templates/county.html',
            controller: 'countyCtrl',
            abstract:true
        })
        .state('main.county.display', {
            url: '/display',
            templateUrl: 'app/components/county/templates/display.html',
            controller: 'displayCountyCtrl'
        })
        .state('main.county.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/county/templates/modify.html',
            controller: 'editCountyCtrl'
        })
        .state('main.county.insert', {
            url: '/insert',
            templateUrl: 'app/components/county/templates/modify.html',
            controller: 'insertCountyCtrl'
        })
        .state('main.locality', {
            url: '/locality',
            templateUrl: 'app/components/locality/templates/locality.html',
            controller: 'localityCtrl',
            abstract:true
        })
        .state('main.locality.display', {
            url: '/display',
            templateUrl: 'app/components/locality/templates/display.html',
            controller: 'displayLocalityCtrl'
        })
        .state('main.locality.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/locality/templates/modify.html',
            controller: 'editLocalityCtrl'
        })
        .state('main.locality.insert', {
            url: '/insert',
            templateUrl: 'app/components/locality/templates/modify.html',
            controller: 'insertLocalityCtrl'
        })
        .state('main.author', {
            url: '/author',
            templateUrl: 'app/components/author/templates/author.html',
            controller: 'authorCtrl',
            abstract:true
        })
        .state('main.author.display', {
            url: '/display',
            templateUrl: 'app/components/author/templates/display.html',
            controller: 'displayAuthorCtrl'
        })
        .state('main.author.edit', {
            url: '/edit/:id/:index',
            templateUrl: 'app/components/author/templates/modify.html',
            controller: 'editAuthorCtrl'
        })
        .state('main.author.insert', {
            url: '/insert',
            templateUrl: 'app/components/author/templates/modify.html',
            controller: 'insertAuthorCtrl'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'app/components/login/templates/login.html',
            controller: 'loginCtrl'
        });

        $urlRouterProvider.otherwise('/login');

    });


