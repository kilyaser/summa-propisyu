angular.module('app', ['ngStorage']).controller('sumController', function ($scope, $http) {
    const sumContextPath = 'http://localhost:8080/api/v1/convert'

    $scope.formInfo = {};

    $scope.defaultInfo = {
        currency: "RUB",
        inputVAT: "20",
        point: "0",
        sum: "479.11"
    };

    $scope.getResult = function (data) {
        $http.post(sumContextPath, data)
            .then(function successCallback(response) {
                $scope.sumWriting = response.data;

            }, function errorCallback(){
               $scope.getResult($scope.defaultInfo);
            });

    };

    $scope.copyResult = function (result, event) {


        var txtArea = document.createElement("textarea");
        txtArea.id = 'txt';
        txtArea.style.position = 'fixed';
        txtArea.style.top = '0';
        txtArea.style.left = '0';
        txtArea.style.opacity = '0';
        txtArea.value = result;
        document.body.appendChild(txtArea);
        txtArea.select();

        try {
            var successful = document.execCommand('copy');
            var msg = successful ? 'successful' : 'unsuccessful';
            console.log('Copying text command was ' + msg);
            if (successful) {
                $scope.changeAttr(event.target.id);
                return true;
            }
        } catch (err) {
            console.log('Oops, unable to copy');
        } finally {
            document.body.removeChild(txtArea);
        }
        return false;

    };

    $scope.changeAttr = function (id) {
        console.log(id);
        let img = angular.element(document.getElementById(id));
        img.attr('src', "image/copied.png");
        setTimeout(function(){ img.attr('src', "image/copy.png") }, 500);
    };

    $scope.getResult($scope.defaultInfo);

});