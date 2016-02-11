<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Datatables Version 3 -->
<link href="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.css" />" rel="stylesheet">
<script src="<c:url value="/datatables/media/js/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/datatables/integration/bootstrap/3/dataTables.bootstrap.js" />"></script>

<!-- Dialog 3 -->
<link href="<c:url value="/bootstrap/dialog3/css/bootstrap-dialog.min.css" />" rel="stylesheet">
<script src="<c:url value="/bootstrap/dialog3/js/bootstrap-dialog.min.js" />"></script>

<style>
.centerclass{
	text-align:center; 
	vertical-align:middle; 
}
</style>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
	
		var url = "<c:url value="${irsFormDatatableUrl}"/>";
		var mergingPdfFromDiskDirectUrl = "<c:url value="${mergingPdfFromDiskDirectUrl}"/>";
		var selectedIrsFormUrl = "<c:url value="${selectedIrsFormUrl}"/>";
		var selectedIrsFormAllUrl = "<c:url value="${selectedIrsFormAllUrl}"/>";
		var irsMailCodeSearchUrl = "<c:url value="${irsMailCodeSearchUrl}"/>";
		
		
		var row_id;
		var highlight = 'active'; 
		var isHighlight = false;
		var types = [BootstrapDialog.TYPE_DEFAULT, 
                     BootstrapDialog.TYPE_INFO, 
                     BootstrapDialog.TYPE_PRIMARY, 
                     BootstrapDialog.TYPE_SUCCESS, 
                     BootstrapDialog.TYPE_WARNING, 
                     BootstrapDialog.TYPE_DANGER];	
		var selected = [];
		var selectedPdf = '';
		
		$('#w8BeneFormDatatable').dataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : {
	            "url": "w8beneform/dataTable"
	        },
			"rowCallback": function(row, data ) {
				if ( $.inArray(data.DT_RowId, selected) !== -1 ) {
	                $(row).addClass(highlight);
	            }
	        },
	        "columnDefs": [
	                       { className: "centerclass", "targets": [ 0 ] },
	                       { "visible": true, "targets": 1 },
	                       { "visible": true, "targets": 2 }
	                     ]
		});
		

		
		// Call datatables, and return the API to the variable for use in our code
		// Binds datatables to all elements with a class of datatable
		var dtable = $("#w8BeneFormDatatable").dataTable().api();

		// Grab the datatables input box and alter how it is bound to events
		$("#w8BeneFormDatatable_filter input")
		    .unbind() // Unbind previous default bindings
		    .bind("input", function(e) { // Bind our desired behavior
		        // If the length is 3 or more characters, or the user pressed ENTER, search
		        if(this.value.length >= 3 || e.keyCode == 13) {
		        	selected = [];
		        	isHighlight = false;
		            dtable.search(this.value).draw();
		        }
		        // Ensure we clear the search if they backspace far enough
		        if(this.value == "") {
		            dtable.search("").draw();
		        }
		        return;
		    });
		
		
		
		
		$('#w8BeneFormDatatable tbody').on('click', 'tr', function(){
			
			var id = this.id;
	        var index = $.inArray(id, selected);
	 
	        if ( index === -1 ) {
	            selected.push( id );
	        } else {
	            selected.splice( index, 1 );
	        }
	 
	        $(this).toggleClass(highlight);
            
	        isHighlight = false;
            $('#w8BeneFormDatatable tbody').find('tr').each(function(){
	            if ($(this).hasClass(highlight) ) {
	            	
	                isHighlight = true;
	            }
            });
            
            // clear for new values
            selectedPdf = '';
	    });
	    
	    
	    
	    var noSelectionDialog = new BootstrapDialog({
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
		        
	    
	 
	 	$('#openButton').on('click', function(event) {
			if(!isHighlight){
				noSelectionDialog.open();
			}else{
				selectedPdf = '{"selected":' + JSON.stringify(selected) + '}';	
				$.ajax({
			    	type: "POST",
				  	url: "w8beneform/createPdfToDisk",
				  	dataType: 'json', 
				    data: selectedPdf, 
				    contentType: 'application/json',
				    mimeType: 'application/json',
				    success: function(data) { 
				    
					        window.open("w8beneform/openPdf", '_blank');
					        
				    },
				    error:function(data,status,er) { 
				        alert("error: "+data+" status: "+status+" er:"+er);
				    }
				});
			}
		});   
	 	
	 	
		
		
		
	});
</script>

<div class="page-header">
	<h1>W-8BEN-E Form for 2014</h1>
	<p class="lead">Select and download W-8BEN-E PDF copy for selected CIF.</p>
</div>



<p>
	<button class="btn btn-default btn-sm" id="openButton">Open PDF</button>
</p>

<table id="w8BeneFormDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>CIF</th>
			<th>Name</th>
			<th>Country</th>
		</tr>
	</thead>
</table>

<input type="hidden" id="hide"/>


