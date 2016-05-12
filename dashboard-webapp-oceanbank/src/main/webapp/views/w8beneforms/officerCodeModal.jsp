<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
   .filter-dialog .modal-dialog {
        width: 490px;
    }
    
    .filter-dialog .modal-content {
    	height: 500px;
	}

	.filter-dialog .modal-body {
	    max-height: calc(100% - 120px);
	    overflow-y: scroll;
	}
</style>

<script>
$(document).ready(function() {
	
	var table = $('#w8BeneFormDirectDatatable').DataTable();
	var selchecked = '';
	var selected = [];
	
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
			  	url: 'w8beneformDirect/createPdfToDiskFromFilter',
			  	dataType: 'json', 
			    data: selectedPdf, 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
			    		$btn.button('reset');
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



<div class="row">
	<div class="form-group">
		<label class="control-label col-md-4">Officer Codes</label> 
		
		<div class="col-sm-4">
			<c:forEach var="code" items="${codeList}">
				<div class="checkbox">
					<label>
						<input type="checkbox" name="mailcbx" value="<c:out value="${code.code}" />">${code.code} - ${code.description}
					</label>
				</div>
			</c:forEach>
		</div>
		
	</div>
</div>
<div class="row">
	<div class="form-group">
		<label class="control-label col-sm-4"></label> 
		<div class="col-xs-8 col-sm-3">
			<button class="btn btn-info" id="searchButton">Refresh Table</button>
		</div>
		<div class="col-xs-8 col-sm-3">
			<button class="btn btn-info" id="openPdfButton">Open PDF</button>
		</div>
	</div>
</div>





