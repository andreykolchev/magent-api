TRUNCATE
ma_tmp_types_roles,
ma_template_types,
ma_user_roles,
ma_user_personal,
ma_settings,
ma_activity,
ma_location,
ma_call,
ma_assignment_task_controls,
ma_assignment_tasks,
ma_assignment_attribute,
ma_assignment,
ma_template_task_controls,
ma_template_tasks,
ma_template_attribute,
ma_template,
ma_reason,
ma_user_device,
ma_device,
ma_sms_pass,
ma_user,
ma_transactions,
ma_accounts,
ma_onboards;

INSERT INTO ma_user_roles (usr_rol_pk, role) VALUES (1,'ADMIN');
INSERT INTO ma_user_roles (usr_rol_pk, role) VALUES (2,'BACK_OFFICE_EMPLOYEE');
INSERT INTO ma_user_roles (usr_rol_pk, role) VALUES (3,'REMOTE_SELLER_STAFFER');
INSERT INTO ma_user_roles (usr_rol_pk, role) VALUES (4,'SALES_AGENT_FREELANCER');
INSERT INTO ma_user_roles (usr_rol_pk, role) VALUES (5,'SALES_AGENT_FREELANCER_LEAD_GEN');
--
-- Data for Name: ds_user; Type: TABLE DATA; Schema: public; Owner: magent
--
/*user1 pass testUser*/
INSERT INTO ma_user (usr_pk, enabled, login,  u_role, first_name, last_name, e_mail)
VALUES (1, TRUE, 'user1',  1, 'user1', 'Userov1', NULL);
INSERT INTO ma_user (usr_pk, enabled, login,  u_role, first_name, last_name, e_mail)
VALUES (2, TRUE, 'admin',  1, 'user2', 'Userov2', NULL);
INSERT INTO ma_user (usr_pk, enabled, login,  u_role, first_name, last_name, e_mail)
VALUES (3, TRUE, '+380506847580', 2, NULL, NULL, NULL);

INSERT INTO ma_user (usr_pk, enabled, login,  u_role, first_name, last_name, e_mail)
VALUES (4, TRUE, 'user2', 3, NULL, NULL, NULL);

INSERT INTO ma_user_personal(user_pers_pk, usr_pers_pwd, ma_usr_id)  VALUES (1,'63874c0f0b6ba858cc832af759ca4954c2c84d4f',1);
INSERT INTO ma_user_personal(user_pers_pk, usr_pers_pwd, ma_usr_id)  VALUES (2,'63874c0f0b6ba858cc832af759ca4954c2c84d4f',2);
INSERT INTO ma_user_personal(user_pers_pk, usr_pers_pwd, ma_usr_id)  VALUES (3,'63874c0f0b6ba858cc832af759ca4954c2c84d4f',3);
INSERT INTO ma_user_personal(user_pers_pk, usr_pers_pwd, ma_usr_id)  VALUES (4,'63874c0f0b6ba858cc832af759ca4954c2c84d4f',4);


INSERT INTO ma_sms_pass(sms_pwd_pk, sms_password, sms_user_id,endperiod) VALUES (1,'ef9ff97e3bd1408eac6f802186af89da55eb184d',1,'2016-01-01');

/*account*/
INSERT INTO ma_accounts (acount_number, account_balance, user_id) VALUES (999999999999, 1400.09, 1);
INSERT INTO ma_accounts (acount_number, account_balance, user_id) VALUES (999999999998, 1500.00, 2);
INSERT INTO ma_accounts (acount_number, account_balance, user_id) VALUES (999999999997, 1500.00, 3);
INSERT INTO ma_accounts (acount_number, account_balance, user_id) VALUES (999999999996, 1350.01, 4);

INSERT INTO ma_transactions (trans_pk, account_number, is_increment_operation, transactioon_summ, transaction_date)
VALUES (1, 999999999999, TRUE, 15.00, '2016-04-10 00:18:00');
INSERT INTO ma_transactions (trans_pk, account_number, is_increment_operation, transactioon_summ, transaction_date)
VALUES (2, 999999999999, TRUE, 105.00, '2016-04-10 00:18:20');
INSERT INTO ma_transactions (trans_pk, account_number, is_increment_operation, transactioon_summ, transaction_date)
VALUES (3, 999999999998, TRUE, 125.00, '2016-04-10 00:18:20');
INSERT INTO ma_transactions (trans_pk, account_number, is_increment_operation, transactioon_summ, transaction_date)
VALUES (4, 999999999999, TRUE, 125.00, '2016-04-12 00:18:20');
INSERT INTO ma_transactions (trans_pk, account_number, is_increment_operation, transactioon_summ, transaction_date)
VALUES (5, 999999999998, FALSE , 125.00, '2016-04-16 00:18:20');
--
-- Data for Name: ds_device; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_device (device_pk, googleid, lastchange) VALUES ('d6190a7e8aea17ad',
                                                                'APA91bGNX7mBHNhJlBjo64tQvs92M2dHhr21P1yXfoqLrqyJD8rUkOjgaUAyga_LvvWZRV0uY3YNQ3q323UcGzH3VnKz-MOLtuTbRmtMcfcpPNar_vXiYEqpKaEZrcjJcAKseV2y2tCK',
                                                                NULL);
INSERT INTO ma_device (device_pk, googleid, lastchange) VALUES ('f628c95c83390329',
                                                                'APA91bEZrxvXVRpTw0yrxFYAo4tvK3LKNo5TYXOItuZzf7lOd-iVtyCnhHe71y5vDseqm_sSKh0lnxxXON0JXZ2AEsOA5BVla_zuchzZ5vxONa2pGo98CZCAUFzWapXoAJvA-FdLch38',
                                                                1457698042164);

--
-- Data for Name: ds_user_device; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_user_device (user_id, device_id) VALUES (1, 'd6190a7e8aea17ad');
INSERT INTO ma_user_device (user_id, device_id) VALUES (2, 'f628c95c83390329');

--
-- Data for Name: ds_reason; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_reason (reason_pk, parent_id, description, name) VALUES (1, NULL, 'По вине клиента', 'По вине клиента');
INSERT INTO ma_reason (reason_pk, parent_id, description, name) VALUES (2, NULL, 'По вине агента', 'По вине агента');
INSERT INTO ma_reason (reason_pk, parent_id, description, name) VALUES (3, 1, 'По вине клиента child', 'По вине клиента');

INSERT INTO ma_template_types(temp_type_pk, temp_type_desc, parent_temp_tp_pk) VALUES (1,'test',NULL );
INSERT INTO ma_template_types(temp_type_pk, temp_type_desc, parent_temp_tp_pk) VALUES (2,'Test type',NULL );
INSERT INTO ma_template_types(temp_type_pk, temp_type_desc, parent_temp_tp_pk) VALUES (3,'Test type 3',NULL );


INSERT INTO ma_tmp_types_roles(temp_type_pk, usr_rol_pk) VALUES (1,1);
--
-- Data for Name: ds_template; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_template (templ_pk, name, description,tmp_tmp_type_id) VALUES (1, 'Кредитная карта', 'Кредитная карта "Кредитка"',1);
INSERT INTO ma_template (templ_pk, name, description,tmp_tmp_type_id) VALUES (2, 'Кредитная карта For Delete', 'Кредитная карта "Кредитка"',2);

--
-- Data for Name: ds_template_attribute; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_template_attribute (templ_attr_pk, template_id, description, value_type, editable, required)
VALUES (1, 1, 'ФИО клиента', 'TEXT', TRUE, TRUE);
INSERT INTO ma_template_attribute (templ_attr_pk, template_id, description, value_type, editable, required)
VALUES (2, 1, 'Адрес клиента', 'ADDRESS', TRUE, TRUE);
INSERT INTO ma_template_attribute (templ_attr_pk, template_id, description, value_type, editable, required)
VALUES (3, 1, 'Телефон клиента', 'PHONE_NUMBER', NULL, TRUE);
INSERT INTO ma_template_attribute (templ_attr_pk, template_id, description, value_type, editable, required)
VALUES (5, 1, 'Основные тарифы продуктов', 'TEXT', NULL, TRUE);

--
-- Data for Name: ds_template_tasks; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_template_tasks (temp_tk_pk, template_id, description, priority, required) VALUES (1, 1, 'Паспорт', 1, TRUE);
INSERT INTO ma_template_tasks (temp_tk_pk, template_id, description, priority, required) VALUES (2, 1, 'ИНН', 2, TRUE);
INSERT INTO ma_template_tasks (temp_tk_pk, template_id, description, priority, required) VALUES (3, 1, 'Опросник ФЛ', 3, TRUE);

--
-- Data for Name: ds_task_controls; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (1, 1, 'Паспорт разворот 1', 'PHOTO', NULL, 1, TRUE);
INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (2, 1, 'Паспорт разворот 2', 'PHOTO', NULL, 2, TRUE);
INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (3, 2, 'ИНН', 'PHOTO', NULL, 3, TRUE);
INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (4, 2, 'Комментарий', 'TEXT', NULL, 4, TRUE);
INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (5, 3, 'Опросник ФЛ 1', 'PHOTO', NULL, 5, TRUE);
INSERT INTO ma_template_task_controls (temp_tk_contr_pk, template_task_id, description, value_type, validation_rule, priority, required)
VALUES (6, 3, 'Опросник ФЛ 2', 'PHOTO', NULL, 6, TRUE);

--
-- Data for Name: ds_assignment; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_assignment (assign_pk, template_id, user_id, datestart, dateend, deadline, description, latitude, longitude, lastchange, reason_id, status, version)
VALUES (1, 1, 1, '2016-04-04 17:00:00', '2016-04-04 18:00:00', '2016-04-10 00:18:00', 'Кредитная карта Кредитка Test ',
           50.4710999999999999, 30.4995000000000012, 1459778490265, NULL, 'ACCEPT', 1);

--
-- Data for Name: ds_assignment_attribute; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_assignment_attribute (asign_attr_pk, assignment_id, description, value_type, editable, attr_value, lastchange, required, visible)
VALUES (1, 1, 'ФИО клиента', 'TEXT', TRUE, 'Петров Павел Иванович', 1459766266362, TRUE,TRUE );
INSERT INTO ma_assignment_attribute (asign_attr_pk, assignment_id, description, value_type, editable, attr_value, lastchange, required,visible)
VALUES (4, 1, 'Адрес клиента', 'ADDRESS', TRUE, 'г. Киев, Фрунзе 39', 1459766266379, TRUE,FALSE );
INSERT INTO ma_assignment_attribute (asign_attr_pk, assignment_id, description, value_type, editable, attr_value, lastchange, required)
VALUES (2, 1, 'Телефон клиента', 'PHONE_NUMBER', TRUE, '+380505555555', 1459766266387, TRUE);
INSERT INTO ma_assignment_attribute (asign_attr_pk, assignment_id, description, value_type, editable, attr_value, lastchange, required)
VALUES (3, 1, 'Основные тарифы продуктов', 'TEXT', TRUE, 'Выпуск карты - 0грн.', 1459766266395, TRUE);

--
-- Data for Name: ds_assignment_tasks; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_assignment_tasks (asign_tas_pk, assignment_id, description, lastchange, priority, required, version)
VALUES (1, 1, 'Паспорт', 1459766266407, 1, TRUE, 1);
INSERT INTO ma_assignment_tasks (asign_tas_pk, assignment_id, description, lastchange, priority, required, version)
VALUES (2, 1, 'ИНН', 1459766266472, 2, TRUE, 2);
INSERT INTO ma_assignment_tasks (asign_tas_pk, assignment_id, description, lastchange, priority, required, version)
VALUES (3, 1, 'Опросник ФЛ', 1459766266498, 3, TRUE, 3);

--
-- Data for Name: ds_assignment_task_controls; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (1, 1, 'Паспорт разворот 1', 'PHOTO', NULL, 1459766266419, TRUE, 'photo1.jpg', 1);
INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (2, 1, 'Паспорт разворот 2', 'PHOTO', NULL, 1459766266431, TRUE, 'photo2.jpg', 2);
INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (3, 2, 'ИНН', 'PHOTO', NULL, 1459766266442, TRUE, 'photo3.jpg', 3);
INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (4, 2, 'Комментарий', 'TEXT', NULL, 1459766266458, TRUE, 'test Комментарий', 4);
INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (5, 3, 'Опросник ФЛ 1', 'PHOTO', NULL, 1459766266466, TRUE, 'photo4.jpg', 5);
INSERT INTO ma_assignment_task_controls (asign_tc_pk, assignment_task_id, description, value_type, validation_rule, lastchange, required, control_value, priority)
VALUES (6, 3, 'Опросник ФЛ 2', 'PHOTO', NULL, 1459766266485, TRUE, 'photo5.jpg', 6);

--
-- Data for Name: ds_call; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_call (call_pk, assignment_id, device_id, user_id, call_date, call_duration, call_number, call_type, contact_name, lastchange)
VALUES (1, 1, 'd6190a7e8aea17ad', 1, '2016-02-12 11:14:08.919', 100, '0674452233', 'OUTGOING', 'OUTGOING', NULL);
INSERT INTO ma_call (call_pk, assignment_id, device_id, user_id, call_date, call_duration, call_number, call_type, contact_name, lastchange)
VALUES (2, NULL, 'd6190a7e8aea17ad', 1, '2016-02-12 11:14:08.919', 100, '0674452233', 'OUTGOING', 'OUTGOING', NULL);
--
-- Data for Name: ds_location; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_location (loc_pk, device_id, user_id, location_date, latitude, longitude)
VALUES (1, 'd6190a7e8aea17ad', 1, '2016-03-28 11:22:08.782', 50.4712084, 30.4994083);

--
-- Data for Name: ds_activity; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_activity (activity_pk, device_id, user_id, app_date, app_duration, app_name, version)
VALUES (1, 'd6190a7e8aea17ad', 1, '2016-02-16 17:01:11.157', 3103174, 'com.distancesales', 1);

--
-- Data for Name: ds_settings; Type: TABLE DATA; Schema: public; Owner: magent
--

INSERT INTO ma_settings (sett_pk, user_id, is_upload_apps, is_upload_calls, is_upload_location, upload_stats_start_time, upload_stats_stop_time)
VALUES (1, 1, TRUE, TRUE, TRUE, 123131, 546464);


