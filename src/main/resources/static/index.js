angular.module('app', ['ngStorage']).controller('sumController', function ($scope, $http) {
    const sumContextPath = 'http://localhost:8080/api/v1/convert'

    $scope.formInfo = {};

    $scope.defaultInfo = {
        currency: "RUB",
        inputVAT: "20",
        point: "0",
        sum: "555.75"
    };

    $scope.getResult = function (data) {
        console.log($scope.formInfo);
        $http.post(sumContextPath, data)
            .then(function successCallback(response) {
                $scope.sumWriting = response
            }, function errorCallback(response){
               $scope.getResult($scope.defaultInfo);
            });

    };

    $scope.getResult($scope.defaultInfo);

});