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
        console.log(data);
        $http.post(sumContextPath, data)
            .then(function successCallback(response) {
                console.log(response.data);
                $scope.sumWriting = response.data;

            }, function errorCallback(){
               $scope.getResult($scope.defaultInfo);
            });

    };

    $scope.getResult($scope.defaultInfo);

});