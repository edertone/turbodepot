-- User tenant
CREATE TABLE `usr_tenant`
(
    `name`        varchar(250)  NOT NULL,
    `description` varchar(5000) NOT NULL,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User operation
CREATE TABLE `usr_operation`
(
    `tenant`      varchar(250) NOT NULL,
    `name`        varchar(250) NOT NULL,
    `description` varchar(250) NOT NULL,
    PRIMARY KEY (`tenant`, `name`),
    CONSTRAINT `usr_operation_usr_tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `usr_tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User operation role
CREATE TABLE `usr_operation_role`
(
    `tenant`    varchar(250) NOT NULL,
    `operation` varchar(250) NOT NULL,
    `role`      varchar(250) NOT NULL,
    PRIMARY KEY (`tenant`, `operation`, `role`),
    CONSTRAINT `usr_operation_role_usr_tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `usr_tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `usr_operation_role_usr_operation_fk` FOREIGN KEY (`tenant`, `operation`) REFERENCES `usr_operation` (`tenant`, `name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User role
CREATE TABLE `usr_role`
(
    `tenant`      varchar(250)  NOT NULL,
    `name`        varchar(250)  NOT NULL,
    `description` varchar(5000) NOT NULL,
    PRIMARY KEY (`tenant`, `name`),
    CONSTRAINT `usr_role_usr_tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `usr_tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User object
CREATE TABLE `usr_userobject`
(
    `dbid`               bigint unsigned NOT NULL AUTO_INCREMENT,
    `tenant`             varchar(250)    NOT NULL,
    `dbuuid`             varchar(36)     NOT NULL DEFAULT (UUID()),
    `dbcreationdate`     datetime(6)     NOT NULL,
    `dbmodificationdate` datetime(6)     NOT NULL,
    `dbdeleted`          datetime(6)              DEFAULT NULL,
    `username`           varchar(100)    NOT NULL,
    `description`        varchar(2000)   NULL,
    `data`               longtext        NULL,
    PRIMARY KEY (`dbid`),
    UNIQUE KEY `usr_userobject_tenant_username_uk` (`tenant`, `username`),
    UNIQUE KEY `usr_userobject_dbuuid_uk` (`dbuuid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User token
CREATE TABLE `usr_token`
(
    `dbid`             bigint unsigned NOT NULL AUTO_INCREMENT,
    `usrdbid`          bigint unsigned NOT NULL,
    `token`            text            NOT NULL,
    `dbcreationdate`   datetime(6)     NOT NULL,
    `dbexpirationdate` datetime(6)     NOT NULL,
    PRIMARY KEY (`dbid`),
    KEY `usr_token_usr_dbid_fk` (`usrdbid`),
    CONSTRAINT `usr_token_usrdbid_fk` FOREIGN KEY (`usrdbid`) REFERENCES `usr_userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY `usr_token_token_uk` (`token`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User custom fields
CREATE TABLE `usr_userobject_customfields`
(
    `dbid`       bigint unsigned NOT NULL,
    `name`       varchar(100)    NOT NULL,
    `surnames`   varchar(200)    NOT NULL,
    `phone`      varchar(25)     NULL,
    `company`    varchar(200)    NULL,
    `occupation` varchar(200)    NULL,
    `address`    varchar(300)    NULL,
    `city`       varchar(100)    NULL,
    `district`   varchar(100)    NULL,
    `postalcode` varchar(20)     NULL,
    PRIMARY KEY (`dbid`),
    CONSTRAINT `usr_userobject_customfields_usr_userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `usr_userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User mails
CREATE TABLE `usr_userobject_mail`
(
    `dbid`             bigint unsigned NOT NULL,
    `mail`             varchar(250)    NOT NULL,
    `isverified`       tinyint(1)      NOT NULL DEFAULT false,
    `verificationhash` varchar(20)              DEFAULT NULL,
    `comments`         varchar(1000)   NULL,
    `data`             longtext        NULL,
    PRIMARY KEY (`dbid`, `mail`),
    CONSTRAINT `usr_userobject_mail_usr_userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `usr_userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User password
CREATE TABLE `usr_userobject_password`
(
    `dbid`     bigint unsigned NOT NULL,
    `password` varchar(500)    NOT NULL,
    PRIMARY KEY (`dbid`),
    CONSTRAINT `usr_userobject_password_usr_userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `usr_userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User role
CREATE TABLE `usr_userobject_role`
(
    `dbid`       bigint unsigned NOT NULL,
    `value`      varchar(250)    NOT NULL,
    `arrayindex` bigint unsigned NOT NULL,
    UNIQUE KEY `usr_userobject_role_dbid_arrayindex_uk` (`dbid`, `arrayindex`),
    CONSTRAINT `usr_userobject_role_dbid_fk` FOREIGN KEY (`dbid`) REFERENCES `usr_userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
