CREATE TABLE ma_accounts
(
  acount_number   BIGINT PRIMARY KEY NOT NULL,
  account_balance DOUBLE PRECISION,
  user_id         BIGINT             NOT NULL
);
CREATE TABLE ma_activity
(
  activity_pk  BIGINT PRIMARY KEY NOT NULL,
  app_date     TIMESTAMP,
  app_duration BIGINT,
  app_name     VARCHAR(255)       NOT NULL,
  device_id    VARCHAR(255),
  user_id      BIGINT,
  version      INTEGER            NOT NULL
);
CREATE TABLE ma_assignment
(
  assign_pk   BIGINT PRIMARY KEY NOT NULL,
  dateend     TIMESTAMP,
  datestart   TIMESTAMP,
  deadline    TIMESTAMP,
  description VARCHAR(255),
  lastchange  BIGINT,
  latitude    DOUBLE PRECISION,
  longitude   DOUBLE PRECISION,
  reason_id   BIGINT,
  status      VARCHAR(255)       NOT NULL,
  template_id BIGINT             NOT NULL,
  user_id     BIGINT             NOT NULL,
  version     INTEGER
);
CREATE TABLE ma_assignment_attribute
(
  asign_attr_pk BIGINT PRIMARY KEY NOT NULL,
  assignment_id BIGINT,
  description   VARCHAR(255),
  editable      BOOLEAN,
  lastchange    BIGINT,
  attr_name     VARCHAR(255),
  required      BOOLEAN,
  attr_value    VARCHAR(255),
  value_type    VARCHAR(255)       NOT NULL,
  visible       BOOLEAN
);
CREATE TABLE ma_assignment_task_controls
(
  asign_tc_pk        BIGINT PRIMARY KEY NOT NULL,
  assignment_task_id BIGINT,
  description        VARCHAR(255),
  lastchange         BIGINT,
  priority           INTEGER,
  required           BOOLEAN,
  validation_rule    VARCHAR(255),
  control_value      VARCHAR(255),
  value_type         VARCHAR(255)       NOT NULL
);
CREATE TABLE ma_assignment_tasks
(
  asign_tas_pk  BIGINT PRIMARY KEY NOT NULL,
  assignment_id BIGINT,
  description   VARCHAR(255),
  lastchange    BIGINT,
  priority      INTEGER,
  required      BOOLEAN,
  version       INTEGER            NOT NULL
);
CREATE TABLE ma_call
(
  call_pk       BIGINT PRIMARY KEY NOT NULL,
  assignment_id BIGINT,
  call_date     TIMESTAMP,
  call_duration INTEGER,
  call_number   VARCHAR(255),
  call_type     VARCHAR(255),
  contact_name  VARCHAR(255),
  device_id     VARCHAR(255),
  lastchange    BIGINT,
  user_id       BIGINT
);
CREATE TABLE ma_device
(
  device_pk  VARCHAR(255) PRIMARY KEY NOT NULL,
  googleid   VARCHAR(255),
  lastchange BIGINT
);
CREATE TABLE ma_location
(
  loc_pk        BIGINT PRIMARY KEY NOT NULL,
  location_date TIMESTAMP,
  device_id     VARCHAR(255),
  latitude      DOUBLE PRECISION,
  longitude     DOUBLE PRECISION,
  user_id       BIGINT
);
CREATE TABLE ma_onboards
(
  onboard_id_pk    BIGINT PRIMARY KEY NOT NULL,
  onboard_contents VARCHAR(250),
  onboard_desc     VARCHAR(50),
  onboard_filename VARCHAR(255)       NOT NULL,
  onboard_img      BYTEA              NOT NULL
);
CREATE TABLE ma_reason
(
  reason_pk   BIGINT PRIMARY KEY NOT NULL,
  description VARCHAR(255),
  name        VARCHAR(255),
  parent_id   BIGINT
);
CREATE TABLE ma_settings
(
  sett_pk                 BIGINT PRIMARY KEY NOT NULL,
  is_upload_apps          BOOLEAN,
  is_upload_calls         BOOLEAN,
  is_upload_location      BOOLEAN,
  upload_stats_start_time BIGINT,
  upload_stats_stop_time  BIGINT,
  user_id                 BIGINT
);
CREATE TABLE ma_sms_pass
(
  sms_pwd_pk   BIGINT PRIMARY KEY NOT NULL,
  sms_password VARCHAR(255),
  endperiod    TIMESTAMP          NOT NULL,
  sms_user_id  BIGINT             NOT NULL
);
CREATE TABLE ma_template
(
  templ_pk        BIGINT PRIMARY KEY NOT NULL,
  description     VARCHAR(255),
  name            VARCHAR(255)       NOT NULL,
  tmp_tmp_type_id BIGINT             NOT NULL
);
CREATE TABLE ma_template_attribute
(
  templ_attr_pk BIGINT PRIMARY KEY NOT NULL,
  description   VARCHAR(255),
  editable      BOOLEAN,
  name          VARCHAR(255),
  required      BOOLEAN,
  template_id   BIGINT             NOT NULL,
  value         VARCHAR(255),
  value_type    VARCHAR(255)       NOT NULL,
  visible       BOOLEAN
);
CREATE TABLE ma_template_task_controls
(
  temp_tk_contr_pk BIGINT PRIMARY KEY NOT NULL,
  description      VARCHAR(255),
  priority         INTEGER,
  required         BOOLEAN,
  template_task_id BIGINT,
  validation_rule  VARCHAR(255),
  value_type       VARCHAR(255)       NOT NULL
);
CREATE TABLE ma_template_tasks
(
  temp_tk_pk  BIGINT PRIMARY KEY NOT NULL,
  description VARCHAR(255),
  priority    INTEGER,
  required    BOOLEAN,
  template_id BIGINT
);
CREATE TABLE ma_template_types
(
  temp_type_pk      BIGINT PRIMARY KEY NOT NULL,
  temp_type_desc    VARCHAR(255)       NOT NULL,
  parent_temp_tp_pk BIGINT
);
CREATE TABLE ma_temporary_user
(
  temp_usr_pk        BIGINT PRIMARY KEY NOT NULL,
  tmp_usr_device_id  VARCHAR(255)       NOT NULL,
  tmp_e_mail         VARCHAR(255)       NOT NULL,
  tmp_confirm_expiry TIMESTAMP          NOT NULL,
  tmp_first_name     VARCHAR(255),
  tmp_otp            VARCHAR(255)       NOT NULL,
  tmp_pwd            VARCHAR(255)       NOT NULL,
  tmp_last_name      VARCHAR(255),
  login              VARCHAR(255)       NOT NULL
);
CREATE TABLE ma_time_config
(
  tconf_pk               INTEGER PRIMARY KEY NOT NULL,
  tconf_name             VARCHAR(255)        NOT NULL,
  tconf_interval_minutes VARCHAR(255)        NOT NULL
);
CREATE TABLE ma_tmp_types_roles
(
  temp_type_pk BIGINT NOT NULL,
  usr_rol_pk   BIGINT NOT NULL
);
CREATE TABLE ma_transactions
(
  trans_pk               BIGINT PRIMARY KEY NOT NULL,
  account_number         BIGINT             NOT NULL,
  is_increment_operation BOOLEAN            NOT NULL,
  transactioon_summ      NUMERIC(19, 2)     NOT NULL,
  transaction_date       TIMESTAMP          NOT NULL
);
CREATE TABLE ma_user
(
  usr_pk     BIGINT PRIMARY KEY NOT NULL,
  e_mail     VARCHAR(255),
  enabled    BOOLEAN,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  login      VARCHAR(255)       NOT NULL,
  u_role     BIGINT             NOT NULL
);
CREATE TABLE ma_user_device
(
  user_id   BIGINT       NOT NULL,
  device_id VARCHAR(255) NOT NULL
);
CREATE TABLE ma_user_personal
(
  user_pers_pk            BIGINT PRIMARY KEY    NOT NULL,
  usr_pers_att_counter    INTEGER DEFAULT 0     NOT NULL,
  usr_pers_block_expires  TIMESTAMP,
  usr_pers_for_pwd_expire TIMESTAMP,
  usr_pers_is_blocked     BOOLEAN DEFAULT FALSE NOT NULL,
  usr_pers_pwd            VARCHAR(255)          NOT NULL,
  ma_usr_id               BIGINT                NOT NULL,
  usr_pers_wrong_enters   INTEGER DEFAULT 0     NOT NULL
);
CREATE TABLE ma_user_roles
(
  usr_rol_pk BIGINT PRIMARY KEY NOT NULL,
  role       VARCHAR(255)       NOT NULL
);
ALTER TABLE ma_accounts ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
CREATE UNIQUE INDEX uk_8w54akrp1ibat54ed9xykws47 ON ma_accounts (user_id);
ALTER TABLE ma_activity ADD FOREIGN KEY (device_id) REFERENCES ma_device (device_pk);
ALTER TABLE ma_activity ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_assignment ADD FOREIGN KEY (reason_id) REFERENCES ma_reason (reason_pk);
ALTER TABLE ma_assignment ADD FOREIGN KEY (template_id) REFERENCES ma_template (templ_pk);
ALTER TABLE ma_assignment ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_assignment_attribute ADD FOREIGN KEY (assignment_id) REFERENCES ma_assignment (assign_pk);
ALTER TABLE ma_assignment_task_controls ADD FOREIGN KEY (assignment_task_id) REFERENCES ma_assignment_tasks (asign_tas_pk);
ALTER TABLE ma_assignment_tasks ADD FOREIGN KEY (assignment_id) REFERENCES ma_assignment (assign_pk);
ALTER TABLE ma_call ADD FOREIGN KEY (assignment_id) REFERENCES ma_assignment (assign_pk);
ALTER TABLE ma_call ADD FOREIGN KEY (device_id) REFERENCES ma_device (device_pk);
ALTER TABLE ma_call ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_location ADD FOREIGN KEY (device_id) REFERENCES ma_device (device_pk);
ALTER TABLE ma_location ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_reason ADD FOREIGN KEY (parent_id) REFERENCES ma_reason (reason_pk);
ALTER TABLE ma_settings ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_sms_pass ADD FOREIGN KEY (sms_user_id) REFERENCES ma_user (usr_pk);
CREATE UNIQUE INDEX uk_o08sg2c4hb9nsiqrugjih40f3 ON ma_sms_pass (sms_user_id);
ALTER TABLE ma_template ADD FOREIGN KEY (tmp_tmp_type_id) REFERENCES ma_template_types (temp_type_pk);
CREATE UNIQUE INDEX uk_mfxdrk701qj3oejscaqs7e16r ON ma_template (tmp_tmp_type_id);
ALTER TABLE ma_template_attribute ADD FOREIGN KEY (template_id) REFERENCES ma_template (templ_pk);
ALTER TABLE ma_template_task_controls ADD FOREIGN KEY (template_task_id) REFERENCES ma_template_tasks (temp_tk_pk);
ALTER TABLE ma_template_tasks ADD FOREIGN KEY (template_id) REFERENCES ma_template (templ_pk);
ALTER TABLE ma_template_types ADD FOREIGN KEY (parent_temp_tp_pk) REFERENCES ma_template_types (temp_type_pk);
CREATE UNIQUE INDEX uk_h3nd8ffwnvc2w5oissx3c3k36 ON ma_temporary_user (tmp_usr_device_id);
CREATE UNIQUE INDEX uk_2vs9otrppux1y8qq8948dts0m ON ma_temporary_user (tmp_e_mail);
CREATE UNIQUE INDEX uk_jqaf49vu5o7fduuiwfbwu1opa ON ma_temporary_user (login);
CREATE UNIQUE INDEX uk_im1qw7h3rqy0jp5r6rf9q3cs3 ON ma_time_config (tconf_name);
ALTER TABLE ma_tmp_types_roles ADD FOREIGN KEY (temp_type_pk) REFERENCES ma_template_types (temp_type_pk);
ALTER TABLE ma_tmp_types_roles ADD FOREIGN KEY (usr_rol_pk) REFERENCES ma_user_roles (usr_rol_pk);
ALTER TABLE ma_transactions ADD FOREIGN KEY (account_number) REFERENCES ma_accounts (acount_number);
ALTER TABLE ma_user ADD FOREIGN KEY (u_role) REFERENCES ma_user_roles (usr_rol_pk);
CREATE UNIQUE INDEX uk_eoy0y312ibge7flffe011b6me ON ma_user (login);
ALTER TABLE ma_user_device ADD FOREIGN KEY (user_id) REFERENCES ma_user (usr_pk);
ALTER TABLE ma_user_device ADD FOREIGN KEY (device_id) REFERENCES ma_device (device_pk);
ALTER TABLE ma_user_personal ADD FOREIGN KEY (ma_usr_id) REFERENCES ma_user (usr_pk);
CREATE UNIQUE INDEX uk_5vud6w4467ptvbtk4pt1yxl7p ON ma_user_personal (ma_usr_id);