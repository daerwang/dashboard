<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
   .user-success-dialog .modal-dialog {
        width: 300px;
    }
</style>
<script>
$(document).ready(function() {


	var token = Cookies.get('restToken');
	var restApi = Cookies.get('restApi');
	var userName = Cookies.get('userName');

	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}

	var url1 = "${userBootstrapValidatorUrl}";
	url1 = url1.substring(1, url1.indexOf('{'));
	var url2 = "${userUpdateDatatableUrl}";
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

	$('#userForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        submitHandler: function(validator, form, submitButton){

        	var formObj = form.serializeObject()
        	console.log(formObj);
        	formObj.accountNonLocked = 1;
        	formObj.accountNonExpired = 1;
        	if(formObj.accountNonLockedHtml === 'on'){
        		formObj.accountNonLocked = 0;
        	}
        	if(formObj.accountNonExpiredHtml === 'on'){
        		formObj.accountNonExpired = 0;
        	}

        	var formObj2 = formObj;
        	delete formObj2.accountNonLockedHtml;
        	delete formObj2.accountNonExpiredHtml;

			var jsonData = JSON.stringify(formObj2);
			console.log(jsonData);

			var url = getContextPath() + '/administration/user/update';

		    $.ajax({
		    	type: "PUT",
		    	url : url,
			  	dataType: 'json', 
			    data: jsonData,
			    contentType: 'application/json',
			    mimeType: 'application/json',
			    success: function(data) { 
				        BootstrapDialog.show({
				        type : BootstrapDialog.TYPE_SUCCESS,
			            title: 'Success',
			            message: 'The User is updated successfully.',
			            buttons: [{
			                label: 'Ok',
			                cssClass: 'btn-success',
			                action: function(dialog){
			                    dialog.close();
			                    //table.ajax.reload();
			                }
			            }]
			        });		
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
					remote : {
						message : 'The username already exist',
						url : '../../administration/user/validator/' + username,
						type : 'POST'
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
                    },
					stringLength : {
						min : 8,
						message : 'The AS400 username must be at least 8 characters.'
					}
                }
            }
        }
    });
	
});
</script>


<div class="container">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3">
			<form id="userForm" role="form">
				<input type="hidden" name="userId" value="<c:out value="${user.userId}" /> ">
				<input type="hidden" name="password" value="<c:out value="${user.password}" /> ">
				<h2>
					Edit User
				</h2>
				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-12 col-sm-6">
						<div class="form-group">
							<input type="text" name="firstname" class="form-control input-lg"
								placeholder="First Name" tabindex="1"
								value="<c:out value="${user.firstname}" />">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group">
							<input type="text" name="lastname" class="form-control input-lg"
								placeholder="Last Name" tabindex="2"
								value="<c:out value="${user.lastname}" />">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<input type="text" name="username" class="form-control input-lg"
								placeholder="Username" tabindex="3"
								value="<c:out value="${user.username}" />">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<div class="form-group">
							<input type="text" name="email" class="form-control input-lg"
								placeholder="Email" tabindex="4"
								value="<c:out value="${user.email}" />">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<input type="text" name="iseriesname" class="form-control input-lg"
								placeholder="AS400 Username" tabindex="5"
								value="<c:out value="${user.iseriesname}" />">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="checkbox">
								<label>
									<input type="checkbox" name="accountNonLockedHtml"
										<c:if test="${user.accountNonLocked == 0}">checked="checked" value="On"</c:if>
										/>

										Account Locked
								</label>
							</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="checkbox">
								<label>
									<input type="checkbox" name="accountNonExpiredHtml"
										<c:if test="${user.accountNonExpired == 0}">checked="checked" value="On"</c:if>
										/>

										Account Expired
								</label>
							</div>
					</div>
				</div>

				<div class="row">

					<div class="col-xs-12 col-sm-12">
						<p></p>
						<p class="lead">Assign Roles</p>
					</div>
				</div>
				<div class="row">

					<c:forEach var="roleName" items="${obRoles.roleNames}">
						<div class="col-sm-4">


							<div class="checkbox">
								<label>
									<input type="checkbox" name="roleNames" value="<c:out value="${roleName}" />"
									<c:forEach var="currentRole" items="${user.roleNames}">
										<c:if test="${roleName == currentRole}">checked="checked"</c:if>
									</c:forEach>>${roleName}
								</label>
							</div>

						</div>
					</c:forEach>
				</div>

				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-12 col-md-6">
						<button class="btn btn-primary btn-block btn-lg" id="submitUserForm"
							type="submit" tabindex="8">Update</button>
					</div>
					<div class="col-xs-12 col-md-6">
						<a class="btn btn-success btn-block btn-lg" href="../../administration" role="button" tabindex="9">Back</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>





