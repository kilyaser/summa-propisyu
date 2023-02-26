angular.module('app', ['ngStorage']).controller('sumController', function ($scope, $http, $localStorage, $rootScope, $interval) {
    const sumContextPath = 'http://localhost:8080/api/v1/convert'

    $scope.formInfo = {
        currency: "RUB",
        inputVAT: "20",
        point: "0",
        sum: "555.75"
    };

    $scope.getResult = function () {
        console.log($scope.formInfo);
    };
    $scope.getResult();

});