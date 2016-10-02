'use strict';

/* Controllers */

// Clear browser cache (in development mode)
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
  $rootScope.$on('$viewContentLoaded', function () {
    $templateCache.removeAll();
  });
});

app.controller('home', ['$http', '$location', 'FilesFactory', function($http, $location, FilesFactory) {
	var self = this;
	
	$http.get('/user').success(function(data) {
		self.user = data.name;
		self.id = data.id;
		self.authenticated = true;
	}).error(function() {
		self.user = 'N/A';
		self.authenticated = false;
	});
	
	self.logout = function() {
		$http.post('/logout', {}).success(function() {
 			self.authenticated = false;
 			$location.path('/');
		}).error(function(data) {
			console.log('Logout failed')
			self.authenticated = false;
		});
	};
	
	self.refreshFiles = function() {
		FilesFactory.getFiles().success(function(result) {
			self.files = result;
		});
	}
	
	FilesFactory.getFiles().success(function(result) {
		self.files = result;
	});
}]);