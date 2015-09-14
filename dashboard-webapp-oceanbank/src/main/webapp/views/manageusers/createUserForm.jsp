<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
   .user-success-dialog .modal-dialog {
        width: 300px;
    }
</style>
<script>
$(document).ready(function() {

	var url1 = "${userBootstrapValidatorUrl}";
	url1 = url1.substring(1, url1.length);
	var url2 = "${userCreateDatatableUrl}";
	url2 = url2.substring(1, url2.length);
	
	var table = $('#manageUserDatatable').DataTable();
	
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
	            if(this.name == 'roleNames'){
	        		o[this.name] = [];
	        		o[this.name].push(this.value || '');
	        	}else{
	        		o[this.name] = this.value || '';
	        	}
	        }
	    });
	    return o;
	};																																													
	
	var username = "${user.username}";
	var successDialog = new BootstrapDialog({
						closable: false,
			            title: 'Success',
			            message: 'The User is Created successfully.',
			            cssClass: 'user-success-dialog',
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-primary',
			                action: function(dialog){
			                    dialog.close();
			                    table.ajax.reload();
			                    newUserDialog.close();
			                }
			            }]
			        });	
	
	
	$('#userForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton){
			
		    $.ajax({
		    	type: "POST",
			  	url: url2,
			  	dataType: 'json', 
			    data: JSON.stringify(form.serializeObject()), 
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
					successDialog.open();        	
			    },
			    error:function(data,status,er) { 
			        alert("error: "+data+" status: "+status+" er:"+er);
			    }
			});
			
        },
        submitButtons : 'button[type="submit"]',
        fields: {
        	firstname: {
	                message: 'The first name must have value',
	                validators: {
	                    notEmpty: {
	                        message: 'The first name is required and cannot be empty'
	                    }
	                }
	            },
	        lastname: {
	                message: 'The last name must have value',
	                validators: {
	                    notEmpty: {
	                        message: 'The last name is required and cannot be empty'
	                    }
	                }
	            },
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
                    },
                    different: {
                        field: 'password',
                        message: 'The username and password cannot be the same as each other'
                    },
                    remote: {
                    	message: 'The username already exist',
                    	url: url1 + username, 
                    	type: 'POST'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and cannot be empty'
                    },
                    emailAddress: {
                        message: 'The email address is not a valid'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required and cannot be empty'
                    },
                    different: {
                        field: 'username',
                        message: 'The password cannot be the same as username'
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
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            roleNames: {
                validators: {
                    notEmpty: {
                        message: 'Please specify at least one Role as your access.'
                    }
                }
            },
            iseriesname: {
                validators: {
                    notEmpty: {
                        message: 'Please specify your iSeries username.'
                    }
                }
            }
        }
    });
	
});
</script>



<form id="userForm" class="form-horizontal">
	<input type="hidden" name="userId" value="<c:out value="${user.userId}" /> ">

	<div class="form-group">
		<label class="control-label col-md-4">${firstname1}</label> 
		<div class="col-md-7">
			<input class="form-control" name="firstname" placeholder="Enter First Name" value="<c:out value="${user.firstname}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${lastname1}</label> 
		<div class="col-md-7">
			<input class="form-control" name="lastname" placeholder="Enter Last Name" value="<c:out value="${user.lastname}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${username1}</label> 
		<div class="col-md-7">
			<input class="form-control" name="username" placeholder="Enter Username" value="<c:out value="${user.username}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${password1}</label> 
		<div class="col-md-7">
			<input type="password" class="form-control" name="password" placeholder="Enter Password" value="<c:out value="${user.password}" />">
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${matchPassword1}</label> 
		<div class="col-md-7">
			<input type="password" class="form-control" name="matchingPassword" placeholder="Confirm Password" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${email1}</label> 
		<div class="col-md-7">
			<input class="form-control" name="email" placeholder="Enter email" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">iSeries Name</label> 
		<div class="col-md-7">
			<input class="form-control" name="iseriesname" placeholder="Enter iSeries username" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-4">${role1}</label> 
		<div class="col-md-7">
		<c:forEach var="roleName" items="${obRoles.roleNames}">
			
				<div class="checkbox">
					<label><input type="checkbox" name="roleNames" value="<c:out value="${roleName}" />" 
						<c:if test="${roleName == 'Guest'}">checked="checked"</c:if> >${roleName}</label>
				</div>
			
		</c:forEach>
		</div>
	</div>
	
	
	<div class="form-group">
		<label class="control-label col-md-4"></label> 
		<div class="col-md-7">
			<button class="btn btn-info" id="submitUserForm" type="submit">Save</button>
		</div>
	</div>
</form>

