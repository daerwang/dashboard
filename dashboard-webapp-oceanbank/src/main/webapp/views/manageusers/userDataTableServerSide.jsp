<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Datatables Version 3 -->
<link href="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.css" />" rel="stylesheet">
<script src="<c:url value="/datatables/media/js/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.js" />"></script>

<!-- Dialog 3 -->
<link href="<c:url value="/bootstrap/dialog3/css/bootstrap-dialog.min.css" />" rel="stylesheet">
<script src="<c:url value="/bootstrap/dialog3/js/bootstrap-dialog.min.js" />"></script>


<script type="text/javascript" charset="utf-8">
	var newUserDialog;
	
	$(document).ready(function() {
		
		
		var noSelectionDialog;
		
		var table;
		var row_id;
		var highlight = 'active'; 
		var isHighlight = false;
		var types = [BootstrapDialog.TYPE_DEFAULT, 
                     BootstrapDialog.TYPE_INFO, 
                     BootstrapDialog.TYPE_PRIMARY, 
                     BootstrapDialog.TYPE_SUCCESS, 
                     BootstrapDialog.TYPE_WARNING, 
                     BootstrapDialog.TYPE_DANGER];	
		var updateModalPage = "${updateModalPageUrl}";
		updateModalPage = updateModalPage.substring(1, updateModalPage.indexOf('{'));
		var newModalPage = "${newModalPageUrl}"
		newModalPage = newModalPage.substring(1, newModalPage.length);
		var deleteUserUrl = "${deleteUserUrl}";
		deleteUserUrl = deleteUserUrl.substring(1, deleteUserUrl.indexOf('{'));
		
		var userDatatableJson = "${userDatatableUrl}"
		userDatatableJson = userDatatableJson.substring(1, userDatatableJson.length);
		
		
		
		table = $('#manageUserDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : userDatatableJson
		});
		
		
		$('#manageUserDatatable tbody').on('click', 'tr', function () {
			
			row_id = this.id;
			
	        if ( $(this).hasClass(highlight) ) {
                $(this).removeClass(highlight);
                isHighlight = false;
            }
            else {
            	// remove all highlighted rows first
            	table.$('tr.' + highlight).removeClass(highlight);
            	// apply highlight to new row
                $(this).addClass(highlight);
                
                isHighlight = true;
            }
            
	    });
		
		noSelectionDialog = new BootstrapDialog({
					type : types[5],
		            title: 'Error',
		            message: 'There is no selection made.',
		            buttons: [{
		                label: 'Close',
		                cssClass: 'btn btn-danger',
		                action: function(dialog){
		                    dialog.close();
		                }
		            }]
		        });
		        
		
	 
	 	$('#editButton').on('click', function(event) {
	 		if(!isHighlight){
				noSelectionDialog.open();
				return;
	 		}
	 		$(location).attr('href', 'users/editUserForm/' + row_id);
		});   
		
		
		$('#newButton').on('click', function(event) {
			$(location).attr('href', 'users/createUserForm');
		});   

		$('#changePasswordButton').on('click', function(event) {
			if(!isHighlight){
				noSelectionDialog.open();
				return;
	 		}
			
			$(location).attr('href', 'users/changePassword/' + row_id);
		}); 

		$('#deleteButton').on('click', function(event) {

			$.ajax({
		    	type: "DELETE",
			  	url: deleteUserUrl + row_id,
			    success: function(data) { 
				        BootstrapDialog.show({
			            title: 'Success',
			            message: 'The User is Deleted successfully.',
			            cssClass: 'user-success-dialog',
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-primary',
			                action: function(dialog){
			                    dialog.close();
			                    table = $('#manageUserDatatable').DataTable();
			                    table.ajax.reload();
			                }
			            }]
			        });		
			    },
			    error:function(data,status,er) { 
			        alert("error: "+data+" status: "+status+" er:"+er);
			    }
			});
			
		});
		
		
		
		
		
	});
	    
</script>

<div class="page-header">
	<h3>Administration</h3>
	<p>Admin page to manage Users and Roles like resetting password or
		adding new Users.</p>
</div>

<p>
	<button type="button" class="btn btn-default btn-sm" id="newButton">New</button>
	<button type="button" class="btn btn-default btn-sm" id="editButton">Edit</button>
	<button type="button" class="btn btn-default btn-sm" id="deleteButton">Delete</button>
	<button type="button" class="btn btn-default btn-sm" id="changePasswordButton">Change Password</button>
</p>

<table id="manageUserDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Username</th>
			<th>Password</th>
			<th>Email</th>
		</tr>
	</thead>
</table>
