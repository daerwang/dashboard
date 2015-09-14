<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Dialog 3 -->
<link href="<c:url value="/bootstrap/dialog3/css/bootstrap-dialog.min.css" />" rel="stylesheet">
<script src="<c:url value="/bootstrap/dialog3/js/bootstrap-dialog.min.js" />"></script>



<script type="text/javascript" charset="utf-8">
	
	$(document).ready(function() {
		
		var url1 = "${changePasswordUrl}";
		//url1 = url1.substring(1, url1.length);
		var url2 = '..' + "${loginPageUrl}";
		
		$("#backToLoginLink").attr("href", url2);
		
			        
		$.fn.serializeObject = function()
		{
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};	
		
		$('#changePasswordForm').bootstrapValidator({
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        submitHandler: function(validator, form, submitButton){
					
				    $.ajax({
				    	type: "POST",
					  	url: '..' + url1,
					  	dataType: 'json', 
					    data: JSON.stringify(form.serializeObject()), 
					    contentType: 'application/json',
					    mimeType: 'application/json',
					    success: function(data) { 
					    	
					    	console.log(data);
					    	var msg = data.message;
					    	var code = data.code;
		
					    	
					    	if(code == 'OK'){
					    	
					    		BootstrapDialog.show({
									closable: false,
						            title: 'Success',
						            message: msg,
						            cssClass: 'user-success-dialog',
						            buttons: [{
						                label: 'Close',
						                cssClass: 'btn-primary',
						                action: function(dialog){
						                    dialog.close();
						                }
						            }]
						        });	
					    		
					    	}else if(code == 'ERROR'){
					    	
					    		BootstrapDialog.show({
									closable: false,
						            title: 'Error',
						            message: msg,
						            cssClass: 'user-success-dialog',
						            buttons: [{
						                label: 'Close',
						                cssClass: 'btn-primary',
						                action: function(dialog){
						                    dialog.close();
						                    
						                    // reset the form fields
						        			form.find("input").val("");
						        			form.find("i").removeClass("glyphicon glyphicon-ok");

						                }
						            }]
						        });	
						        
						        
					    	}
					    	
							        	
					    },
					    error:function(data,status,er) { 
					        alert("error: "+data+" status: "+status+" er:"+er);
					    }
					});
					
		        },
		        submitButtons : 'button[type="submit"]',
		        fields: {
		            username: {
		            	trigger: 'blur',
		                message: 'The username is not valid',
		                validators: {
		                    notEmpty: {
		                        message: 'The username is required and cannot be empty'
		                    },
		                    stringLength: {
		                        min: 4,
		                        max: 20,
		                        message: 'The username must be more than 4 and less than 20 characters long'
		                    },
		                    regexp: {
		                        regexp: /^[a-zA-Z0-9]+$/,
		                        message: 'The username can only consist of alphabetical and number'
		                    }
		                }
		            },
		            oldPassword: {
		                validators: {
		                    notEmpty: {
		                        message: 'The password is required and cannot be empty'
		                    }
		                }
		            },
		            newPassword: {
		                validators: {
		                    notEmpty: {
		                        message: 'The password is required and cannot be empty'
		                    },
		                    different: {
		                        field: 'oldPassword',
		                        message: 'The password cannot be the same as old password'
		                    },
		                    stringLength: {
		                        min: 6,
		                        message: 'The password must have at least 6 characters'
		                    }
		                }
		            },
		            matchingPassword: {
		                validators: {
		                	notEmpty: {
		                        message: 'The confirm password is required and cannot be empty'
		                    },
		                    identical: {
		                        field: 'newPassword',
		                        message: 'The new password and its confirm are not the same'
		                    }
		                }
		            }
		        }
		    });
		
		
	});
	    
</script>
<form id="changePasswordForm" class="form-horizontal">

	<div class="form-group">
		<label class="control-label col-md-2">${username1}</label> 
		<div class="col-md-3">
			<input class="form-control" name="username" placeholder="Enter Username" value="<c:out value="${passwordObj.username}"/>" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-2">${oldPassword1}</label> 
		<div class="col-md-3">
			<input type="password" class="form-control" name="oldPassword" placeholder="Enter Old Password" value="<c:out value="${passwordObj.oldPassword}"/>" >
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-2">${newPassword1}</label> 
		<div class="col-md-3">
			<input type="password" class="form-control" name="newPassword" placeholder="Enter New Password" value="<c:out value="${passwordObj.newPassword}"/>">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-2">${matchPassword1}</label> 
		<div class="col-md-3">
			<input type="password" class="form-control" name="matchingPassword" placeholder="Confirm Password" value="<c:out value="${passwordObj.matchingPassword}"/>" >
		</div>
	</div>
	
	
	
	<div class="form-group">
		<label class="control-label col-md-2"></label> 
		<div class="col-md-5">
			<button class="btn btn-info" id="submitChangePasswordForm" type="submit">Save</button>
			<a class="btn btn-info" id="backToLoginLink" href="">Back to Login</a>
		</div>
	</div>
</form>