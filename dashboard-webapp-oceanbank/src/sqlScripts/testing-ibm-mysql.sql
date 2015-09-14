select * from dashboarduser;
select * from dashboardrole;
select * from dashboardamlbatchrequest;
select * from dashboardamlbatchcontainer;
select * from usersdb.users_test;

UPDATE roles a SET a.role_name = 'Administrator' WHERE role_id = 1;

select * from users a 
WHERE a.username like '%dina%'
	OR a.firstname like '%dina%'
	OR a.lastname like '%dina%'
	OR a.email like '%dina%';
	
	
	
-- -----------------------------------------------------
-- Table `usersdb`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `usersdb`.`DashboardUser` ;

CREATE TABLE IF NOT EXISTS `usersdb`.`DashboardUser` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `enabled` INT NOT NULL DEFAULT 1,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `idx_username` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `usersdb`.`Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `usersdb`.`DashboardRole` ;

CREATE TABLE IF NOT EXISTS `usersdb`.`DashboardRole` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`role_id`),
  INDEX `fk_roles_users_idx` (`user_id` ASC),
  CONSTRAINT `fk_roles_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `usersdb`.`DashboardUser` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;