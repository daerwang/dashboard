<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

$(document).ready(function(){

	var token = Cookies.get('restToken');
	var restApi = Cookies.get('restApi');
	
	
	setToken();
	
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	
	function setToken(){
		
		var url = getContextPath() + '/login/getApiToken';
		
		$.ajax({
			type: 'GET',
			url: url,
			success: function(data){
				Cookies.set('restToken', data.accessToken);
				Cookies.set('restApi', data.restApi);
				
				token = data.accessToken;
				restApi = data.restApi;
			}
		});
	}
	
	function changeForgotPassword(jsonData, url){
		$.ajax({
			type: 'PUT',
			data: jsonData,
			url: url,
			contentType : 'application/json',
			mimeType : 'application/json',
			success: function(data){

				BootstrapDialog.show({
			        type : BootstrapDialog.TYPE_SUCCESS,
		            title: 'Success',
		            message: 'The Password is changed successfully.',
		            buttons: [{
		                label: 'Ok',
		                cssClass: 'btn-success',
		                action: function(dialog){
		                    dialog.close();
		                    $(location).attr('href', getContextPath() + '/j_spring_security_logout');
		                }
		            }]
		         });
			},
			beforeSend: function (xhr) {
			    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			},
		    error: function (data, status, er) {
		        var json = data.responseText;
		        var errorObj = $.parseJSON(json);
		        BootstrapDialog.show({
			        type : BootstrapDialog.TYPE_WARNING,
		            title: 'Warning',
		            message: 'Error: ' + errorObj.message + ' by ' + errorObj.cause,
		            buttons: [{
		                label: 'Ok',
		                cssClass: 'btn-warning',
		                action: function(dialog){
		                    dialog.close();
		                    $(location).attr('href', getContextPath() + '/j_spring_security_logout');
		                }
		            }]
		         });
		    }
		});
	}
	
	
	$('#userForm1')
	.bootstrapValidator(
	{
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		submitHandler : function(validator,form, submitButton) {
			
			var formObj = form.serializeObject()
			var userObj = new Object();
			userObj.resetToken = formObj.resetToken;
			userObj.password = formObj.newPassword;
			var jsonData = JSON.stringify(userObj);

			var url = restApi + '/api/user/changeForgotPassword';
			console.log(jsonData);
			changeForgotPassword(jsonData, url);

		},
		submitButtons : 'button[type="submit"]',
		fields : {
			newPassword : {
				validators : {
					notEmpty : {
						message : 'The password is required and cannot be empty'
					},
					stringLength : {
						min : 6,
						message : 'The password must have at least 6 characters'
					},
					regexp : {
						regexp : /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]+$/,
						message : 'The password must contain one digit 0-9, one lowercase letter, one uppercase letter, and one special symbol in the list "@#$%&"'
					}
				}
			},
			matchingPassword : {
				validators : {
					notEmpty : {
						message : 'The confirm password is required and cannot be empty'
					},
					identical : {
						field : 'newPassword',
						message : 'The password and its confirm are not the same'
					}
				}
			}
		}
	});
	
});

</script>


<div class="container">
	<div class="row">
		<div class="col-xs-12 col-sm-8 col-md-5 col-md-offset-3">
			<form id="userForm1" role="form">
				<input type="hidden" name="resetToken" value="<c:out value="${activation}" /> ">
				<h2>
					Change Password
				</h2>
				<p>Use the form below to change your password. Your password cannot be the same as your username.</p>
				<hr class="colorgraph">
				
				<div class="row">
					<div class="col-xs-12 col-md-12">
						<div class="form-group">
							<input type="password" name="newPassword"
								class="form-control input-lg" placeholder="New Password"
								tabindex="1">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-12">
						<div class="form-group">
							<input type="password" name="matchingPassword"
								class="form-control input-lg" placeholder="Confirm Password"
								tabindex="2">
						</div>
					</div>
				</div>

				
				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-12 col-md-7 col-md-offset-2">
						<button class="btn btn-primary btn-block btn-lg" 
							type="submit" tabindex="3">Change Password</button>
					</div>
				</div>
				
			</form>
		</div>
	</div>
</div>