-- User tenant
CREATE TABLE `${table-prefix}tenant`
(
    `name`        varchar(250)  NOT NULL,
    `description` varchar(5000) NOT NULL,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User operation
CREATE TABLE `${table-prefix}operation`
(
    `tenant`      varchar(250) NOT NULL,
    `name`        varchar(250) NOT NULL,
    `description` varchar(250) NOT NULL,
    PRIMARY KEY (`tenant`, `name`),
    CONSTRAINT `${table-prefix}operation_${table-prefix}tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `${table-prefix}tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User operation role
CREATE TABLE `${table-prefix}operation_role`
(
    `tenant`    varchar(250) NOT NULL,
    `operation` varchar(250) NOT NULL,
    `role`      varchar(250) NOT NULL,
    PRIMARY KEY (`tenant`, `operation`, `role`),
    CONSTRAINT `${table-prefix}operation_role_${table-prefix}tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `${table-prefix}tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `${table-prefix}operation_role_${table-prefix}operation_fk` FOREIGN KEY (`tenant`, `operation`) REFERENCES `${table-prefix}operation` (`tenant`, `name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User role
CREATE TABLE `${table-prefix}role`
(
    `tenant`      varchar(250)  NOT NULL,
    `name`        varchar(250)  NOT NULL,
    `description` varchar(5000) NOT NULL,
    PRIMARY KEY (`tenant`, `name`),
    CONSTRAINT `${table-prefix}role_${table-prefix}tenant_fk` FOREIGN KEY (`tenant`) REFERENCES `${table-prefix}tenant` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User object
CREATE TABLE `${table-prefix}userobject`
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
    UNIQUE KEY `${table-prefix}userobject_tenant_username_uk` (`tenant`, `username`),
    UNIQUE KEY `${table-prefix}userobject_dbuuid_uk` (`dbuuid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User token
CREATE TABLE `${table-prefix}token`
(
    `dbid`             bigint unsigned NOT NULL AUTO_INCREMENT,
    `usrdbid`          bigint unsigned NOT NULL,
    `token`            text            NOT NULL,
    `dbcreationdate`   datetime(6)     NOT NULL,
    `dbexpirationdate` datetime(6)     NOT NULL,
    PRIMARY KEY (`dbid`),
    KEY `${table-prefix}token_${table-prefix}dbid_fk` (`usrdbid`),
    CONSTRAINT `${table-prefix}token_usrdbid_fk` FOREIGN KEY (`usrdbid`) REFERENCES `${table-prefix}userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY `${table-prefix}token_token_uk` (`token`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User custom fields
CREATE TABLE `${table-prefix}userobject_customfields`
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
    CONSTRAINT `${table-prefix}userobject_customfields_${table-prefix}userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `${table-prefix}userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User mails
CREATE TABLE `${table-prefix}userobject_mail`
(
    `dbid`             bigint unsigned NOT NULL,
    `mail`             varchar(250)    NOT NULL,
    `isverified`       tinyint(1)      NOT NULL DEFAULT false,
    `verificationhash` varchar(20)              DEFAULT NULL,
    `comments`         varchar(1000)   NULL,
    `data`             longtext        NULL,
    PRIMARY KEY (`dbid`, `mail`),
    CONSTRAINT `${table-prefix}userobject_mail_${table-prefix}userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `${table-prefix}userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User password
CREATE TABLE `${table-prefix}userobject_password`
(
    `dbid`     bigint unsigned NOT NULL,
    `password` varchar(500)    NOT NULL,
    PRIMARY KEY (`dbid`),
    CONSTRAINT `${table-prefix}userobject_password_${table-prefix}userobject_fk` FOREIGN KEY (`dbid`) REFERENCES `${table-prefix}userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- User role
CREATE TABLE `${table-prefix}userobject_role`
(
    `dbid`       bigint unsigned NOT NULL,
    `value`      varchar(250)    NOT NULL,
    `arrayindex` bigint unsigned NOT NULL,
    UNIQUE KEY `${table-prefix}userobject_role_dbid_arrayindex_uk` (`dbid`, `arrayindex`),
    CONSTRAINT `${table-prefix}userobject_role_dbid_fk` FOREIGN KEY (`dbid`) REFERENCES `${table-prefix}userobject` (`dbid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
