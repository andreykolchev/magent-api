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
