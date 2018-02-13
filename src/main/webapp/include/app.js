(function() {
	
	var app = angular.module("app", []);

	
	app.controller("HttpCtrl", function($scope, $http) {
		var app = this;
		$scope.navTitle = 'Data Asset Catalog';
		$scope.operation="";
		$scope.isSaveDisabled = true;
		$scope.isDeleteDisabled = true;
		
		var response = $http.get('/dockerpoc/rest/catalogs/');
		response.success(function(data) {
			$scope.catalogs = data;
			console.log("[main] # of items: " + data.length)
			angular.forEach(data, function(element) {
				console.log("[main] catalog: " + element.name);
			});
		})
		response.error(function(data, status, headers, config) {
			alert("AJAX failed to get data, status=" + status);
		})
		
		
		$scope.getCatalog = function(catid) {
			var response = $http.get('/dockerpoc/rest/catalogs/'+ catid );
			
			response.success(function(data) {
				$scope.catalog = data;
				$scope.operation="update";
				$scope.isSaveDisabled = false;
				$scope.isDeleteDisabled = false;
		    })
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		$scope.searchCatalog = function(name) {
			var app = this;
			$scope.navTitle = 'Search Criteria';
			
			var response = $http.get('/dockerpoc/rest/catalogs/search/' + name);
			response.success(function(data) {
				$scope.catalogs = data;
				$scope.$apply();

				console.log("[searchCatalog] # of items: " + data.length)
				angular.forEach(data, function(element) {
					console.log("[searchCatalog] catalog: " + element.name);
				});

		    });
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		$scope.clearForm = function() {
			$scope.catalog = {
					catid:'',
					name:'',
					region:'',
					assetname:'',
					assetdesc:'',
					cid:'',
					cname:'',
					sid:'',
					sname:'',
					model:'',
					tags:'',
					isrequired:''
			};
		}
		
		$scope.addNew = function(element) {
			$scope.operation="create";
			$scope.clearForm();
			main.catid.focus();
			$scope.isSaveDisabled = false;
			$scope.isDeleteDisabled = true;
		}
		
		$scope.saveCatalog = function(catid) {
			$scope.jsonObj = angular.toJson($scope.catalog, false);
			console.log("[update] data: " + $scope.jsonObj);

			if ($scope.operation == "update") {
				var response = $http.put('/dockerpoc/rest/catalogs/' + catid, $scope.jsonObj);
				response.success(function(data, status, headers, config) {
					$scope.resetSearch();
			    });
				
				response.error(function(data, status, headers, config) {
					alert("AJAX failed to get data, status=" + status);
				})
			} else if ($scope.operation == "create") {
				var response = $http.post('/dockerpoc/rest/catalogs/add', $scope.jsonObj);
				response.success(function(data, status, headers, config) {
					$scope.resetSearch();
			    });
				
				response.error(function(data, status, headers, config) {
					alert("AJAX failed to get data, status=" + status);
				})	
			}
		};
		
		$scope.deleteCatalog = function(catid) {
			var response = $http.delete('/dockerpoc/rest/catalogs/' + catid);
			response.success(function(data, status, headers, config) {
				$scope.resetSearch();
			});
				
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		$scope.resetSearch = function(name) {
			var app = this;
			$scope.operation="";
			$scope.clearForm();
			$scope.isSaveDisabled = true;
			$scope.isDeleteDisabled = true;
			$scope.navTitle = 'Data Asset Catalog';
			$scope.searchName = '';
			
			var response = $http.get('/dockerpoc/rest/catalogs/');
			response.success(function(data) {
				$scope.catalogs = data;
				$scope.$apply();
				console.log("[resetSearch] # of items: " + data.length)
		    });
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		$scope.exportToCsv = function(){
			
		}
		
	});	
})();