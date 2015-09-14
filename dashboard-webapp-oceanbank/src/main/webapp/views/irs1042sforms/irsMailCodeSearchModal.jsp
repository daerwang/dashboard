<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
   .user-success-dialog .modal-dialog {
        width: 300px;
    }
</style>
<script>
$(document).ready(function() {
	
	var table = $('#irsFormDatatable').DataTable();
	var selchecked = '';
	var selected = [];
	var mergingPdfFromDiskDirectUrl = "<c:url value="${mergingPdfFromDiskDirectUrl}"/>";
	var selectedIrsMailCodeUrl = "<c:url value="${selectedIrsMailCodeUrl}"/>";
	
	$('#searchButton').on('click', function(event) {
		
		selchecked = '';
		
		if($('input[name="mailcbx"]:checked').length > 0){
	 		$('input[name="mailcbx"]:checked').each(function() {
	 			selchecked = selchecked + this.value + ',';
	 		});
			//console.log(selchecked);
		    $('#hide').val(selchecked);
		    
			table.ajax.reload();
			
		}else{
			
			BootstrapDialog.alert({
            	title: 'WARNING',
	            message: 'A checkbox selection is required.',
	            type: BootstrapDialog.TYPE_WARNING, 
	            closable: true, 
	            draggable: true, 
	            buttonLabel: 'OK', 
	            callback: function(result) {

	            }
	        });
			
		}

		
	});   

	$('#openPdfButton').on('click', function(event) {
		selected = [];
		if($('input[name="mailcbx"]:checked').length > 0){
			
			var $btn = $(this);
		    $btn.button('loading');
			
			$('input[name="mailcbx"]:checked').each(function() {
					
					var id = this.value;
			        var index = $.inArray(id, selected);
			 
			        if ( index === -1 ) {
			            selected.push( id );
			        } else {
			            selected.splice( index, 1 );
			        }
	
			});
			var selectedPdf = '{"selected":' + JSON.stringify(selected) + '}';	
			
			$.ajax({
		    	type: "POST",
			  	url: selectedIrsMailCodeUrl,
			  	dataType: 'json', 
			    data: selectedPdf, 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    		$btn.button('reset');
				        window.open(mergingPdfFromDiskDirectUrl, '_blank');
				        
			    },
			    error:function(data,status,er) { 
			    	BootstrapDialog.alert({
		            	title: 'Error',
			            message: 'The request to server failed. Please try again or contact web admin.',
			            type: BootstrapDialog.TYPE_WARNING, 
			            closable: true, 
			            draggable: true, 
			            buttonLabel: 'OK', 
			            callback: function(result) {

			            }
			        });
			        $btn.button('reset');
			    }
			});
			
		}else{
			
			BootstrapDialog.alert({
            	title: 'WARNING',
	            message: 'A checkbox selection is required.',
	            type: BootstrapDialog.TYPE_WARNING, 
	            closable: true, 
	            draggable: true, 
	            buttonLabel: 'OK', 
	            callback: function(result) {

	            }
	        });
			
		}
		
 		

	});   
	
});
</script>



<div id="mailCodeForm" class="form-horizontal">

	<div class="form-group">
		<label class="control-label col-md-2">Mail Code</label> 
		<div class="col-md-7">
			<c:forEach var="code" items="${codeList}">
				<div class="checkbox">
					<label>
						<input type="checkbox" name="mailcbx" value="<c:out value="${code.code}" />">${code.description}
					</label>
				</div>
			</c:forEach>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="control-label col-md-2"></label> 
		<div class="col-md-7">
			<button class="btn btn-info" id="searchButton">Refresh Table</button>
			<button class="btn btn-info" id="openPdfButton">Open PDF</button>
		</div>
	</div>
	
</div>



