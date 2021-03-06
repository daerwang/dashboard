<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function() {																																
	
	var updateDashboardCommentUrl = "<c:url value="${updateDashboardComment}"/>";
	
	var table = $('#dashboardCommentDatatable').DataTable();
	
	var successDialog = new BootstrapDialog({
						closable: false,
			            title: 'Success',
			            message: 'Comment is updated successfully.',
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-primary',
			                action: function(dialog){
			                    dialog.close();
			                    table.ajax.reload();
			                    updateDashboardCommentDialog.close();
			                }
			            }]
			        });	
	
	
	$('#dashboardUpdateCommentForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton){
			
		    $.ajax({
		    	type: "POST",
			  	url: updateDashboardCommentUrl,
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
        	message: {
	                message: 'The Comment box must have value',
	                validators: {
	                    notEmpty: {
	                        message: 'The Comment box is required and cannot be empty'
	                    }
	                }
	            }
        }
    });
	
	
});
</script>



<form id="dashboardUpdateCommentForm" class="form-horizontal">
	<input type="hidden" name="id" value="<c:out value="${dashboardComment.id}" /> ">

	<div class="form-group">
		<label class="control-label col-md-3">${message1}</label> 
		<div class="col-md-6">
			<textarea class="form-control" name="message" rows="4" placeholder="Enter your comment" value="<c:out value="${dashboardComment.message}" />"><c:out value="${dashboardComment.message}" /></textarea>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-3"></label> 
		<div class="col-md-5">
			<button class="btn btn-info" id="submitComment" type="submit">Update</button>

		</div>
	</div>
</form>

