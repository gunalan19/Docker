<html ng-app="app">
	<head>
		<title>Show Data Asset Catalog</title>
		<link href="include/styles.css" rel="stylesheet">
		<!-- Use Bootstrap -->
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script>
		<script type="text/javascript" src="include/app.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	
	<body ng-controller="HttpCtrl as app">
  	<div class="container">
		<div class="header">
			<h1 class="custom">Data Asset Catalog</h1>
		</div>

 		<div class="leftPanel">
			<!--  <image src="{{catalog.image}}" width="220">-->
			<form>
		    	<table>
					<tr><td><input type="text" ng-model="searchName" size = 7></td> </tr>
					<tr><td><button type="button" ng-click="searchCatalog(searchName)" class="btn btn-primary btn-sm">
      					<span class="glyphicon glyphicon-search"></span>  Search &nbsp;</button></td></tr>
					<tr><td><button ng-click="addNew()" class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-plus"></span> Add New </button></td></tr>
					<tr><td><button ng-click="resetSearch()"  class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-refresh"></span> Reset &nbsp;&nbsp;&nbsp;&nbsp;</button></td></tr>
					<tr><td><button ng-click="exportToCsv()"  class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-refresh"></span> Export &nbsp;&nbsp;&nbsp;&nbsp;</button></td></tr>
				</table>
		    </form>
		</div>  
		
		
		<div class="RightPanel">
			<div class="LeftPanelHeader">{{navTitle}}</div>
			<table border = 1>
				<tr>
					<th class="side">DAC ID</th>
					<th class="side">Name</th>
					<th class="side">Region</th>
					<th class="side">Asset Name</th>
					<th class="side">Asset Desc</th>
					<th class="side">Customer ID</th>
					<th class="side">Customer Name</th>
					<th class="side">Supplier ID</th>
					<th class="side">Supplier Name</th>
					<th class="side">Data Model</th>
					<th class="side">Tags</th>
					<th class="side">DUA required</th>
				</tr>
				<tr ng-repeat="a in catalogs"  >
				    <td class="side1" ng-click="getCatalog(a.catid)" >{{a.catid}}</td>
				    <td class="side">{{a.name}}</td>
				    <td class="side">{{a.region}}</td>
				    <td class="side">{{a.assetname}}</td>
				    <td class="side">{{a.assetdesc}}</td>
				    <td class="side">{{a.cid}}</td>
				    <td class="side">{{a.cname}}</td>
				    <td class="side">{{a.sid}}</td>
				    <td class="side">{{a.sname}}</td>
				    <td class="side">{{a.model}}</td>
				    <td class="side">{{a.tags}}</td>
				    <td class="side">{{a.isrequired}}</td>
				</tr>
			</table>
		</div>
	
		
		<div class="MainBody">
		    
		    
			<form id="main">
				<table>
					<tr>
						<td class="display_bold"><label for="catalog.name">DAC ID:</label></td>
						<td class="display"><input id="catid" type="text" ng-model="catalog.catid" size="5"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Name:</label></td>
						<td class="display"><input type="text" ng-model="catalog.name" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Region</label></td>
						<td class="display"><input type="text" ng-model="catalog.region" size="15"></td>
					</tr>
					
					<tr>
						<td class="display_bold"><label for="name">Asset Name</label></td>
						<td class="display"><input type="text" ng-model="catalog.assetname" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Asset Description</label></td>
						<td class="display"><input type="text" ng-model="catalog.assetdesc" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Customer ID</label></td>
						<td class="display"><input type="text" ng-model="catalog.cid" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Customer Name</label></td>
						<td class="display"><input type="text" ng-model="catalog.cname" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Supplier ID</label></td>
						<td class="display"><input type="text" ng-model="catalog.sid" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Supplier Name</label></td>
						<td class="display"><input type="text" ng-model="catalog.sname" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Data Model/Layout</label></td>
						<td class="display"><input type="text" ng-model="catalog.model" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">Tags</label></td>
						<td class="display"><input type="text" ng-model="catalog.tags" size="15"></td>
					</tr>
					<tr>
						<td class="display_bold"><label for="name">DUA Required</label></td>
						<td class="display"><input type="text" ng-model="catalog.isrequired" size="5"></td>
					</tr>
					
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>
						  <table>						  	
						  	<tr><td>&nbsp;</td>
						  	  <td><button ng-click="saveCatalog(catalog.catid)" class="btn btn-success btn-sm" title="Save DAC details..." ng-disabled="isSaveDisabled">
						  	  <span class="glyphicon glyphicon-plus"></span> Save </button></td>
						
							  <td>&nbsp;</td>  	  
						  	</tr>
						  </table>
						</td>
						<td> <table>						  	
						  	<tr>
						  		<td><button ng-click="deleteCatalog(catalog.catid)" class="btn btn-danger btn-sm" ng-disabled="isDeleteDisabled">
							  		<span class="glyphicon glyphicon-trash"></span> Delete </button></td>
						  		<td>&nbsp;</td>
						  	</tr>
						  	</table>
						</td>
					</tr>
					
				</table>
			</form>
		</div>

		<div class="footer">Data Asset Catalog & Data User Authorization</div>
	</div>
	</body>
</html>