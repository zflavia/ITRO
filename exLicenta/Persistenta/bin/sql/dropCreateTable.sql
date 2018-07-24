DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS RolePrivilege;
DROP TABLE IF EXISTS Usr;
DROP TABLE IF EXISTS Privilege;
DROP TABLE IF EXISTS Role;


CREATE TABLE Usr (
    userId              BIGINT(20)       NOT NULL,
    firstname           VARCHAR(100)     NOT NULL,
    lastname            VARCHAR(100)     NULL,
    logname             VARCHAR(50)      NOT NULL,
    password            VARCHAR(150)      NOT NULL,
    status              VARCHAR(20)      NOT NULL,
    failedLogins        BIGINT(2)        NULL,
    lockedStatus        BOOLEAN          NULL,
    email               VARCHAR(100)     NOT NULL,

CONSTRAINT pk_usr PRIMARY KEY (userId)
);

-- -----------------------------------------------------------------------------
--
-- structure of table 'role'
--
CREATE TABLE Role (
    roleId               BIGINT(4)        NOT NULL,
    label                VARCHAR(30)    NOT NULL,
    description          VARCHAR(200)    NOT NULL,
  
CONSTRAINT pk_role PRIMARY KEY (roleId)
);


-- -----------------------------------------------------------------------------
--
-- structure of table 'privilege'
--
CREATE TABLE Privilege (
    privilegeId          BIGINT(4)       NOT NULL,
    label                VARCHAR(30)     NOT NULL,
    description          VARCHAR(200)    NOT NULL,
  
CONSTRAINT pk_privilege PRIMARY KEY (privilegeId)
);



-- -----------------------------------------------------------------------------
--
-- structure of table 'roleprivilege'
--
CREATE TABLE RolePrivilege (
    rolePrivilegeId       BIGINT(4)        NOT NULL,
    roleId                BIGINT(4)        NOT NULL,
    privilegeId           BIGINT(4)        NOT NULL,
  
  CONSTRAINT pk_roleprivilege PRIMARY KEY (rolePrivilegeId)
);

ALTER TABLE RolePrivilege
    ADD CONSTRAINT fk_roleprivilege_role FOREIGN KEY (roleId)
       REFERENCES Role (roleId) ON DELETE CASCADE
;

ALTER TABLE RolePrivilege
    ADD CONSTRAINT fk_roleprivilege_privilege FOREIGN KEY (privilegeId)
       REFERENCES Privilege (privilegeId) ON DELETE CASCADE
;


-- -----------------------------------------------------------------------------
--
-- structure of table 'userrole'
--
CREATE TABLE UserRole (
    userRoleId              BIGINT(4)        NOT NULL,
    userId                  BIGINT(10)       NOT NULL,
    roleId                  BIGINT(4)        NOT NULL,    
  
CONSTRAINT pk_userrole PRIMARY KEY (userRoleId)
);

ALTER TABLE UserRole
    ADD CONSTRAINT fk_userrole_role FOREIGN KEY (roleId)
       REFERENCES Role (roleId) ON DELETE CASCADE
;

ALTER TABLE UserRole
    ADD CONSTRAINT fk_userrole_usr FOREIGN KEY (userId)
       REFERENCES Usr (id) ON DELETE CASCADE
;



INSERT INTO Usr (Id, firstName, username, password, status) VALUES (0, 'Administrator', 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'activ');


INSERT INTO Usr (userId, firstName, logname, password, status, email) VALUES (0, 'Administrator', 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'activ, 'admin@itro.ro');
INSERT INTO Role (roleId, label, description) VALUES (1, 'Administrator', 'Full rights role');
INSERT INTO Role (roleId, label, description) VALUES (2, 'User', 'Can add translators');
INSERT INTO UserRole (userRoleId, userId, roleId) VALUES (1,0,1);


COMMIT;