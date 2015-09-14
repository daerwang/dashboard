<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function() {

	$('#transactionModal').attr('readonly', true);																																		
	
	var updateAmlBatchCifUrl = "<c:url value="${updateAmlBatchCif}"/>";
	
	var table = $('#amlBatchCifDatatable').DataTable();
	
	var status1 = "${amlBatchCif.status}";
	var approval = "Approved";
	var reversal = "Reversed";
	var disapproval = "Disapproved";
	
	if(reversal.trim().toLowerCase() === status1.trim().toLowerCase()
			|| approval.trim().toLowerCase() === status1.trim().toLowerCase()
			|| disapproval.trim().toLowerCase() === status1.trim().toLowerCase()){
		
		$("#cifReference").attr("readonly", true);
		$("#auditDescription").attr("readonly", true);
		$("#submitAmlBatchCif").attr("disabled", true);
		
		BootstrapDialog.alert({
        	title: 'WARNING',
            message: 'Sorry, this CIF can no longer be edited once Approved, Reversed or Disapproved.',
            type: BootstrapDialog.TYPE_WARNING, 
            closable: true, 
            draggable: true, 
            buttonLabel: 'Ok'
        });	
	}
	//alert($.trim(status1)=="Reversed");
	var successDialog = new BootstrapDialog({
						closable: false,
			            title: 'Success',
			            message: 'The CIF is Updated successfully.',
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-primary',
			                action: function(dialog){
			                    dialog.close();
			                    table.ajax.reload();
			                    updateAmlBatchCifDialog.close();
			                }
			            }]
			        });	
	
	
	$('#amlBatchCifForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton){

			$.ajax({
		    	type: "POST",
			  	url: updateAmlBatchCifUrl,
			  	dataType: 'json', 
			    data: JSON.stringify(form.serializeObject()), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
					successDialog.open();        	
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
			
        },
        submitButtons : 'button[type="submit"]',
        fields: {
        	cifReference: {
	                message: 'The CIF Reference must have value',
	                validators: {
	                    notEmpty: {
	                        message: 'The CIF Reference is required and cannot be empty'
	                    },
	                    stringLength: {
	                        min: 4,
	                        max: 20,
	                        message: 'The CIF Reference must be more than 4 and less than 20 characters long'
	                    }
	                }
	            },
	        description: {
	                message: 'The Description must have value',
	                validators: {
	                    notEmpty: {
	                        message: 'The Description is required and cannot be empty'
	                    }
	                }
	            }
        }
    });
	
	
});
</script>



<form id="amlBatchCifForm" class="form-horizontal">
	<input type="hidden" name="requestId" value="<c:out value="${amlBatchCif.requestId}" /> ">
	<input type="hidden" name="id" value="<c:out value="${amlBatchCif.id}" /> ">
	<input type="hidden" id="status" name="status" value="<c:out value="${amlBatchCif.status}" /> ">

	<div class="form-group">
		<label class="control-label col-md-3">${cifReference1}</label> 
		<div class="col-md-4">
			<input class="form-control" id="cifReference" name="cifReference" placeholder="Enter CIF" value="<c:out value="${amlBatchCif.cifReference}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">${description1}</label> 
		<div class="col-md-8">
			<input class="form-control" id="auditDescription" name="auditDescription" placeholder="Enter Description" value="<c:out value="${amlBatchCif.auditDescription}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-3">${transaction1}</label> 
		<div class="col-md-4">
			<input class="form-control" id="transactionModal" name="transactionType" placeholder="Enter Transaction" value="<c:out value="${amlBatchCif.transactionType}" />">
		</div>
	</div>
	
	
	
	<div class="form-group">
		<label class="control-label col-md-3"></label> 
		<div class="col-md-5">
			<button class="btn btn-info" id="submitAmlBatchCif" type="submit">Save</button>

		</div>
	</div>
</form>

