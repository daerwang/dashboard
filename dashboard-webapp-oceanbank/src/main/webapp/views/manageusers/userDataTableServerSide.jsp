<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript" charset="utf-8">
	var newUserDialog;
	
	$(document).ready(function() {
		
		var token = Cookies.get('restToken');
		var restApi = Cookies.get('restApi');
		
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
		
		function showDeleteUser(url){
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_DANGER,
	            title: 'Warning',
	            draggable: true,
	            message: 'Do you really want to delete?',
	            buttons: [{
	            	label: 'Delete',
	            	action: function(dialog){
	            		dialog.close();
	            		deleteUser(url);
	            	}
	            },{
	                label: 'Close',
	                action: function(dialog){
	                    dialog.close();
	                }
	            }]
	        });
		}
		
		function deleteUser(url){
			$.ajax({
				type: 'DELETE',
				url: url,
				success: function(data){
					table = $('#manageUserDatatable').DataTable();
                    table.ajax.reload();
				},
				beforeSend: function (xhr) {
				    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
				},
			    error: function (data, status, er) {
			        var json = data.responseText;
			        var errorObj = $.parseJSON(json);
			        BootstrapDialog.show({
				        type : BootstrapDialog.TYPE_WARNING,
			            title: 'Warning',
			            message: 'Error: ' + errorObj.message + ' by ' + errorObj.cause,
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-warning',
			                action: function(dialog){
			                    dialog.close();
			                    table = $('#manageUserDatatable').DataTable();
			                    table.ajax.reload();
			                }
			            }]
			         });
			    }
			});
		}

		$('#deleteButton').on('click', function(event) {
			
			var array = row_id.split('_');
			var id = array[1];
			var url = restApi + '/api/user/delete/' + id;

			showDeleteUser(url);

		});
		
		
		
		
		
	});
	    
</script>

<div class="page-header">
	<h3>User Management</h3>
	<p>This is an admin page to manage Users and Roles.</p>
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
