<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JQuery File Upload 9.9.3 -->
<script src="<c:url value="/bootstrap/fileupload/jquery.fileupload.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.iframe-transport.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.ui.widget.js" />"></script>
<link href="<c:url value="/bootstrap/fileupload/jquery.fileupload.css" />" rel="stylesheet">

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		
		var amlBatchDatatable = "<c:url value="${amlBatchDatatable1}"/>";
		var deleteAmlBatchRequest = "${deleteAmlBatchRequest}";
		deleteAmlBatchRequest = deleteAmlBatchRequest.substring(1, deleteAmlBatchRequest.indexOf('{'));
		
		var amlBatchRequestPageUrl = "<c:url value="${amlBatchRequestPage}"/>";
		var generateRequestIdUrl = "<c:url value="${generateRequestId}"/>";
		
		var table;
		var row_id;
		var highlight = 'success'; 
		var isHighlight = false;
		var amlBatchRequestPageUrlFinal = '';
		var types = [BootstrapDialog.TYPE_DEFAULT, 
                     BootstrapDialog.TYPE_INFO, 
                     BootstrapDialog.TYPE_PRIMARY, 
                     BootstrapDialog.TYPE_SUCCESS, 
                     BootstrapDialog.TYPE_WARNING, 
                     BootstrapDialog.TYPE_DANGER];	
		
		table = $('#amlBatchDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : amlBatchDatatable,
			"scrollX": true, 
			"columns": [
			               { "width": "10%"},
			               { "width": "8%"},
			               { "width": "10%"},
			               { "width": "15%"},
			               { "width": "9%"},
			               { "width": "8%"},
			               { "width": "16%"},
			               { "width": "9%"},
			               { "width": "18%"},
			             ],
			 "order": [[ 0, "desc" ]]
			 
		});
		
		$('#amlBatchDatatable tbody').on('click', 'tr', function () {
			
			row_id = this.id;
			
			amlBatchRequestPageUrlFinal = '';
			amlBatchRequestPageUrlFinal = amlBatchRequestPageUrl + '?id=' + row_id;
			
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
		
		var noSelectionDialog = new BootstrapDialog({
			type : BootstrapDialog.TYPE_DANGER,
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
				
				return false;
			}else{

				$(this).prop('href', amlBatchRequestPageUrlFinal);
				window.open(this.href, '_self');
				return false;
			}
			
			
			
		});   
		
		
		function openWindowPage(url){

			window.open(url);
		}
		
		$('#newButton').on('click', function(e) {
			
			e.preventDefault();
			$(this).prop('href', generateRequestIdUrl);
			var link = this;
			
			BootstrapDialog.show({
				type : BootstrapDialog.TYPE_INFO,
	            title: 'Information',
	            draggable: true, 
	            message: 'Are you sure you want to Create a New Batch AML Request?',
	            buttons: [{
	            	label: 'Yes',
	            	action: function(dialog){
	            		dialog.close();
	            		
						window.location = link.href;
						
	            	}
	            },{
	                label: 'Close',
	                action: function(dialog){
	                    dialog.close();
	                }
	            }]
	        });
			
		});   
		

		$('#deleteButton').on('click', function(e) {
			e.preventDefault();
			
			if(!isHighlight){
				noSelectionDialog.open();
			}else{

				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
		            title: 'Information',
		            draggable: true, 
		            message: 'Do you really want to delete ' + row_id+ '?',
		            buttons: [{
		            	label: 'Delete',
		            	action: function(dialog){
		            		dialog.close();
		            		$.ajax({
		        		    	type: "DELETE",
		        			  	url: deleteAmlBatchRequest + row_id,
		        			    success: function(data) { 
		        			    	   
		        				       table = $('#amlBatchDatatable').DataTable();
		        					   table.ajax.reload();
		        					   isHighlight = false;
		        			    },
		        			    error:function(data) { 
		        			        isHighlight = false;
		        			        var res = data.responseJSON.message;
							    	BootstrapDialog.alert({
		        		            	title: 'WARNING',
		        			            message: res,
		        			            type: BootstrapDialog.TYPE_DANGER, 
		        			            closable: true, 
		        			            draggable: true, 
		        			            buttonLabel: 'Ok'
		        			        });	
							    	
		        			    }
		        			});
		            	}
		            },{
		                label: 'Close',
		                action: function(dialog){
		                    dialog.close();
		                }
		            }]
		        });
				
			}
			
			
			
		});
		
		
		$('#refreshButton').on('click', function(e) {
			table = $('#amlBatchDatatable').DataTable();
			table.ajax.reload();
			
		});

	});
</script>

<div class="page-header">
	<h1>AML Batch Approval and Disapproval</h1>
	<p class="lead">Create AML Batch request by loading CIF for approval, disapproval or reversal.</p>
</div>



<p>
	<a href="#" class="btn btn-default btn-sm" id="newButton">New</a>
	<a href="#" class="btn btn-default btn-sm" id="editButton">View</a>
	<button type="button" class="btn btn-default btn-sm" id="deleteButton">Delete</button>
	<button type="button" class="btn btn-default btn-sm" id="refreshButton">Refresh</button>
</p>

<table id="amlBatchDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>Request Id</th>
			<th>Transaction</th>
			<th>Name</th>
			<th>Description</th>
			<th>Batch Status</th>
			<th>Created By</th>
			<th>Created On</th>
			<th>Modified By</th>
			<th>Modified On</th>
		</tr>
	</thead>
</table>