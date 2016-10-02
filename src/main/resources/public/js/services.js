'use strict';

/* Services */

//var services = angular.module('app.services', ['ngResource']);

var baseUrl = 'http://localhost:8080';

app.service('FilesFactory', function($http) {
  this.getFiles = function() {
      return $http.get(baseUrl + '/arquivo/arquivos', {jid:'fcc'});
  };
});