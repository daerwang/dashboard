<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>
	$(document)
			.ready(
					function() {

						var url1 = "${userBootstrapValidatorUrl}";
						url1 = url1.substring(1, url1.length);
						var url2 = "${userCreateDatatableUrl}";
						url2 = url2.substring(1, url2.length);

						var table = $('#manageUserDatatable').DataTable();

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
									if (this.name == 'roleNames') {
										o[this.name] = [];
										o[this.name].push(this.value || '');
									} else {
										o[this.name] = this.value || '';
									}
								}
							});
							return o;
						};

						var username = "${user.username}";
						var successDialog = new BootstrapDialog({
							type : BootstrapDialog.TYPE_SUCCESS,
							closable : false,
							title : 'Success',
							message : 'A new User is registered successfully.',
							buttons : [ {
								label : 'Ok',
								cssClass : 'btn-success',
								action : function(dialog) {
									dialog.close();
									//table.ajax.reload();
									//newUserDialog.close();
									$(location).attr('href', '../administration');
								}
							} ]
						});

						$('#userForm')
								.bootstrapValidator(
										{
											feedbackIcons : {
												valid : 'glyphicon glyphicon-ok',
												invalid : 'glyphicon glyphicon-remove',
												validating : 'glyphicon glyphicon-refresh'
											},
											submitHandler : function(validator,
													form, submitButton) {
												$
														.ajax({
															type : "POST",
															url : '../administration/user/create',
															dataType : 'json',
															data : JSON.stringify(form.serializeObject()),
															contentType : 'application/json',
															mimeType : 'application/json',
															success : function(data) {
																successDialog.open();
															},
															error : function(
																	data,
																	status, er) {
																alert("error: "
																		+ data
																		+ " status: "
																		+ status
																		+ " er:"
																		+ er);
															}
														});

											},
											submitButtons : 'button[type="submit"]',
											fields : {
												firstname : {
													message : 'The first name must have value',
													validators : {
														notEmpty : {
															message : 'The first name is required and cannot be empty'
														}
													}
												},
												lastname : {
													message : 'The last name must have value',
													validators : {
														notEmpty : {
															message : 'The last name is required and cannot be empty'
														}
													}
												},
												username : {
													trigger : 'blur',
													message : 'The username is not valid',
													validators : {
														notEmpty : {
															message : 'The username is required and cannot be empty'
														},
														stringLength : {
															min : 4,
															max : 20,
															message : 'The username must be more than 4 and less than 20 characters long'
														},
														regexp : {
															regexp : /^[a-zA-Z0-9]+$/,
															message : 'The username can only consist of alphabetical and number.'
														},
														different : {
															field : 'password',
															message : 'The username and password cannot be the same as each other'
														},
														remote : {
															message : 'The username already exist',
															url : '../administration/user/validator/' + username,
															type : 'POST'
														}
													}
												},
												email : {
													validators : {
														notEmpty : {
															message : 'The email address is required and cannot be empty'
														},
														emailAddress : {
															message : 'The email address is not a valid'
														}
													}
												},
												password : {
													validators : {
														notEmpty : {
															message : 'The password is required and cannot be empty'
														},
														different : {
															field : 'username',
															message : 'The password cannot be the same as username'
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
															field : 'password',
															message : 'The password and its confirm are not the same'
														}
													}
												},
												roleNames : {
													validators : {
														notEmpty : {
															message : 'Please specify at least one Role as your access.'
														}
													}
												},
												iseriesname : {
													validators : {
														notEmpty : {
															message : 'Please specify your AS400 username.'
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
				<input type="hidden" name="userId"
					value="<c:out value="${user.userId}" /> ">
				<h2>
					User Registration
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
								placeholder="Email" tabindex="4">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<input type="text" name="iseriesname" class="form-control input-lg" 
								placeholder="AS400 Username" tabindex="5">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-6">
						<div class="form-group">
							<input type="password" name="password"
								class="form-control input-lg" placeholder="Password"
								tabindex="6" value="<c:out value="${user.password}" />">
						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="form-group">
							<input type="password" name="matchingPassword"
								class="form-control input-lg" placeholder="Confirm Password"
								tabindex="7">
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
								<label><input type="checkbox" name="roleNames"
									value="<c:out value="${roleName}" />"
									<c:if test="${roleName == 'Guest'}">checked="checked"</c:if>>${roleName}</label>
							</div>


						</div>
					</c:forEach>
				</div>

				<hr class="colorgraph">
				<div class="row">
					<div class="col-xs-12 col-md-6 col-md-offset-3">
						<button class="btn btn-primary btn-block btn-lg" id="submitUserForm" 
							type="submit" tabindex="8">Register</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>