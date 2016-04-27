<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- JQuery File Upload 9.9.3 -->
<script src="<c:url value="/bootstrap/fileupload/jquery.fileupload.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.iframe-transport.js" />"></script>
<script src="<c:url value="/bootstrap/fileupload/jquery.ui.widget.js" />"></script>
<link href="<c:url value="/bootstrap/fileupload/jquery.fileupload.css" />" rel="stylesheet">

<style>
.centerclass{
	text-align:center; 
	vertical-align:middle; 
}
</style>
<script type="text/javascript" charset="utf-8">

$(document).ready(function() {
		
		var row_id;
		var highlight = 'active'; 
		var isHighlight = false;
		var row_idUpload;

		var selected = [];
		var selectedPdf = '';
		
		var table = $('#w8BeneFormDirectDatatable').DataTable({
			"processing" : true,
			"serverSide" : true,
			"ajax" : {
	            "url": "w8beneformDirect/dataTable"
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
		

		
	

		$('#w8BeneFormDirectDatatable tbody').on('click', 'tr', function(){
			
			var id = this.id;
			row_id = this.id;
	        var index = $.inArray(id, selected);
	 
	        if ( index === -1 ) {
	            selected.push( id );
	        } else {
	            selected.splice( index, 1 );
	        }
	 
	        $(this).toggleClass(highlight);
            
	        isHighlight = false;
            $('#w8BeneFormDirectDatatable tbody').find('tr').each(function(){
	            if ($(this).hasClass(highlight) ) {
	            	
	                isHighlight = true;
	            }
            });
            
            // clear for new values
            selectedPdf = '';
	    });
	    
	    
	    
	    var noSelectionDialog = new BootstrapDialog({
					type : BootstrapDialog.TYPE_WARNING,
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
				  	url: "w8beneformDirect/createPdfToDisk",
				  	dataType: 'json', 
				    data: selectedPdf, 
				    contentType: 'application/json',
				    mimeType: 'application/json',
				    success: function(data) { 
				    
					        window.open("w8beneformDirect/openPdf", '_blank');
					        
				    },
				    error:function(data, status, er) {
				    	var res = data.responseJSON.message;
				    	var json = '[' + res + ']';
				    	var errorMsg = '';
				    	var code = '';
				    	$.each(JSON.parse(json), function(idx, obj) {
				    		code = obj.code;
				    		errorMsg = obj.message;
				    	});

				    	BootstrapDialog.alert({
			            	title: 'WARNING',
				            message: code + '</br>' + errorMsg,
				            type: BootstrapDialog.TYPE_DANGER,
				            closable: true,
				            draggable: true,
				            buttonLabel: 'Ok'
				        });
				    }
				});
			}
		});   
	 	
	 	
		// ----------- for upload -----------

	 	var table2 = $('#directTemplateDatatable').DataTable({
			"processing" : true,
			"serverSide" : true,
			"searching": false,
			"ajax" : {
	            "url": "w8beneformDirect/uploadDataTable"
	            },
	        "columnDefs": [
	                       { className: "centerclass", "targets": [ 0 ] },
	                       { "visible": true, "targets": 1 },
	                       { "visible": true, "targets": 2 },
	                       {
	                    	   "visible": true,
	                    	   "targets": 3,
	                    	   "orderable": false,
	                           "searchable": false,
	                           "className": 'centerclass',
	                           "render": function(data, type, full, meta) {
	                        	   if(data){

	                        		   return '<input type="checkbox" checked>';
	                        	   }else{

	                        		   return '<input type="checkbox">';
	                        	   }
	                           }
	                       }
	                     ],
	       "select": {
	    	   style : 'os',
	    	   selector : 'td:first-child'
	       }

		});

	 	var highlight = 'active';
	 	var isHighlightUpload = false;

	    $('#directTemplateDatatable tbody').on('change', 'input[type="checkbox"]', function(){
	        var $table = table2.table().container();
	        var $chkbox_checked = $('tbody input[type="checkbox"]:checked', $table);
	        var checkboxRow = $(this).closest('tr');
	        var d = table2.row(checkboxRow).data();
	        var url = 'w8beneform/updateCheckbox';
	        var desc = null;

	        if($chkbox_checked.length > 1){
	        	$(this).attr('checked', false);
	        	$(this).closest('tr').removeClass(highlight);
	        	BootstrapDialog.alert({
	            	title: 'WARNING',
		            message: 'Only 1 PDF template is allowed to be enabled.',
		            type: BootstrapDialog.TYPE_DANGER,
		            closable: true,
		            draggable: true,
		            buttonLabel: 'Ok'
		        });
	        	var req = new Object();
	        	req.id = d.DT_RowId;
	        	req.description = desc;
	           	var reqData = JSON.stringify(req);

	        	updateCheckbox(url, reqData);
	        }else{
	        	$(this).closest('tr').addClass(highlight);
	        	if(this.checked){
		        	desc = 'active';
		        }
	           	var myObject = new Object();
	           	myObject.id = d.DT_RowId;
	           	myObject.description = desc;
	           	var data = JSON.stringify(myObject);

	           	updateCheckbox(url, data);
	        }

			//table2.draw();

	     });


	    function updateCheckbox(url, param){
	    	$.ajax({
		    	type: "PUT",
			  	url: url,
			  	dataType: 'json',
			    data: param,
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) {
			    },
			    error: function(data) {
			    	var res = data.responseJSON.message;
			    	var json = '[' + res + ']';
			    	var errorMsg = '';
			    	var code = '';
			    	$.each(JSON.parse(json), function(idx, obj) {
			    		code = obj.code;
			    		errorMsg = obj.message;
			    	});

			    	BootstrapDialog.alert({
		            	title: 'WARNING',
			            message: code + '</br>' + errorMsg,
			            type: BootstrapDialog.TYPE_DANGER,
			            closable: true,
			            draggable: true,
			            buttonLabel: 'Ok'
			        });
			    }
			});
	    }

		$('#directTemplateDatatable tbody').on('click', 'tr', function () {

			row_idUpload = this.id;

	        if ( $(this).hasClass(highlight) ) {
                $(this).removeClass(highlight);
                isHighlightUpload = false;
            }
            else {
            	// remove all highlighted rows first
            	table2.$('tr.' + highlight).removeClass(highlight);
            	// apply highlight to new row
                $(this).addClass(highlight);

                isHighlightUpload = true;
            }

	    });



		$('#newUploadButton').on('click', function(e) {
			e.preventDefault();

			dialog1 = new BootstrapDialog({
				title : 'Attachment',
				draggable: true,
				message : function(dialog) {
					var $message = $('<div></div>');
					var pageToLoad = dialog.getData('pageToLoad');
					$message.load(pageToLoad);
					return $message;
				},
				data : {
					'pageToLoad' : 'w8beneformDirect/directTemplateUpload'
				}
			 });

			dialog1.open();
		});

		$('#deleteUploadButton').on('click', function(e) {
			e.preventDefault();

			if(!isHighlightUpload){
				noSelectionDialog.open();
			}else{

				BootstrapDialog.show({
					type : BootstrapDialog.TYPE_DANGER,
		            title: 'Information',
		            draggable: true,
		            message: 'Do you really want to delete?',
		            buttons: [{
		            	label: 'Delete',
		            	action: function(dialog){
		            		dialog.close();
		            		$.ajax({
		        		    	type: "DELETE",
		        			  	url: 'w8beneformDirect/' + row_idUpload + '/deletePdfUpload',
		        			    success: function(data) {
		        			    	   table = $('#directTemplateDatatable').DataTable();
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

		$('#openUploadButton').on('click', function(e) {
			e.preventDefault();
			var openUploadUrl = 'w8beneformDirect/' + row_idUpload + '/openPdfTemplate';
			if(!isHighlightUpload){
				noSelectionDialog.open();
			}else{

				window.open(openUploadUrl, '_blank');

			}

		});
		
		


		

	});
</script>

<div class="page-header">
	<h1>W-8BEN-E Form (Direct)</h1>
	<p class="lead">Select and download W-8BEN-E PDF copy for selected CIF.</p>
</div>



<p>
	<button class="btn btn-default btn-sm" id="openButton">Open PDF</button>
</p>

<table id="w8BeneFormDirectDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>CIF</th>
			<th>Name</th>
			<th>Country</th>
		</tr>
	</thead>
</table>

<input type="hidden" id="hide"/>


<div style="margin-top: 100px;">

<h4>W-8BEN-E Form Template</h4>
<p>Please attach your pdf template file here.</p>
<hr>
<p>
	<button type="button" class="btn btn-default btn-sm" id="openUploadButton">Open</button>
	<button type="button" class="btn btn-default btn-sm" id="deleteUploadButton">Delete</button>
	<button type="button" class="btn btn-default btn-sm" id="newUploadButton">Upload Attachment</button>
</p>

<table id="directTemplateDatatable" class="table table-striped table-bordered" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th>File Name</th>
			<th>Created By</th>
			<th>Created On</th>
			<th>Enable</th>
		</tr>
	</thead>
</table>
</div>


<div class="modal fade modal-cif" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Processing</h4>
      </div>
      <div class="modal-body">

	    <div class="progress">
		  <div class="progress-bar progress-bar-success progress-bar-striped active">
		  </div>
		</div>
      </div>
<!--       <div class="modal-footer"> -->
<!--         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
<!--         <button type="button" class="btn btn-primary">Save changes</button> -->
<!--       </div> -->
    </div>
  </div>
</div>

