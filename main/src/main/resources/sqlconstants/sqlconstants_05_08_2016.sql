INSERT INTO public.ma_user_roles (usr_rol_pk, role) VALUES (1, 'ADMIN');
INSERT INTO public.ma_user_roles (usr_rol_pk, role) VALUES (2, 'BACK_OFFICE_EMPLOYEE');
INSERT INTO public.ma_user_roles (usr_rol_pk, role) VALUES (3, 'REMOTE_SELLER_STAFFER');
INSERT INTO public.ma_user_roles (usr_rol_pk, role) VALUES (4, 'SALES_AGENT_FREELANCER');
INSERT INTO public.ma_user_roles (usr_rol_pk, role) VALUES (5, 'SALES_AGENT_FREELANCER_LEAD_GEN');

INSERT INTO ma_template_types(temp_type_pk, temp_type_desc, parent_temp_tp_pk) VALUES (1,'Full registration',NULL );
INSERT INTO ma_tmp_types_roles(temp_type_pk, usr_rol_pk) VALUES (1,5);
INSERT INTO ma_template_types(temp_type_pk, temp_type_desc, parent_temp_tp_pk) VALUES (2,'agent registration',1);
INSERT INTO ma_tmp_types_roles(temp_type_pk, usr_rol_pk) VALUES (2,5);
INSERT INTO public.ma_template (templ_pk, description, name, tmp_tmp_type_id) VALUES (1, 'Full registration', 'Full registration', 2);
INSERT INTO public.ma_template_tasks (temp_tk_pk, description, priority, required, template_id) VALUES (1, 'Social ID', 1, true, 1);
INSERT INTO public.ma_template_tasks (temp_tk_pk, description, priority, required, template_id) VALUES (2, 'Passport', 2, true, 1);

INSERT INTO public.ma_user (usr_pk, e_mail, enabled, first_name, last_name, login, u_role) VALUES (1, null, true, 'user1', 'Userov1', 'admin', 1);
INSERT INTO public.ma_user_personal (user_pers_pk, usr_pers_pwd, ma_usr_id, usr_pers_block_expires, usr_pers_is_blocked, usr_pers_wrong_enters, usr_pers_att_counter, usr_pers_for_pwd_expire) VALUES (1, '63874c0f0b6ba858cc832af759ca4954c2c84d4f', 1, null, false, 2, 0, null);