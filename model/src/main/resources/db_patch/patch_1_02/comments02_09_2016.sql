
-- COMMENT ON TABLE ma_accounts
COMMENT ON TABLE ma_accounts IS 'Счета агентов';
COMMENT ON COLUMN ma_accounts.acount_number IS 'Номер счета';
COMMENT ON COLUMN ma_accounts.account_balance IS 'Баланс';
COMMENT ON COLUMN ma_accounts.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';

-- COMMENT ON TABLE ma_activity
COMMENT ON TABLE ma_activity IS 'Статистика запускаемых приложений на мобильном устройстве';
COMMENT ON COLUMN ma_activity.activity_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_activity.app_date IS 'Дата записи данных о приложении';
COMMENT ON COLUMN ma_activity.app_duration IS 'Продолжительность использования в мс.';
COMMENT ON COLUMN ma_activity.app_name IS 'Имя приложения';
COMMENT ON COLUMN ma_activity.device_id IS 'Ссылка на мобильное устройство. (device_id->ma_device(device_pk))';
COMMENT ON COLUMN ma_activity.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';
COMMENT ON COLUMN ma_activity.version IS 'Версия записи';

-- COMMENT ON TABLE ma_assignment
COMMENT ON TABLE ma_assignment IS 'Поручения агентов';
COMMENT ON COLUMN ma_assignment.assign_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_assignment.dateend IS 'Дата/время окончания оформления поручения';
COMMENT ON COLUMN ma_assignment.datestart IS 'Дата/время начала оформления поручения';
COMMENT ON COLUMN ma_assignment.deadline IS 'Конечная дата/время актуальности поручения';
COMMENT ON COLUMN ma_assignment.description IS 'Описание поручения';
COMMENT ON COLUMN ma_assignment.lastchange IS 'Ключ для синхронизации изменений';
COMMENT ON COLUMN ma_assignment.latitude IS 'Широта (координаты создания поручения)';
COMMENT ON COLUMN ma_assignment.longitude IS 'Долгота (координаты создания поручения)';
COMMENT ON COLUMN ma_assignment.reason_id IS 'Ссылка на причину отказа. (reason_id->ma_reason(reason_pk))';
COMMENT ON COLUMN ma_assignment.status IS 'Статус поручения';
COMMENT ON COLUMN ma_assignment.template_id IS 'Ссылка на шаблон. (template_id->ma_template(templ_pk))';
COMMENT ON COLUMN ma_assignment.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';
COMMENT ON COLUMN ma_assignment.version IS 'Версия записи';

-- COMMENT ON TABLE ma_assignment_attribute
COMMENT ON TABLE ma_assignment_attribute IS 'Атрибуты поручения (подчиненная для ma_assignment)';
COMMENT ON COLUMN ma_assignment_attribute.asign_attr_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_assignment_attribute.assignment_id IS 'Ссылка на поручение. (assignment_id->ma_assignment(assign_pk))';
COMMENT ON COLUMN ma_assignment_attribute.description IS 'Описание атрибута поручения';
COMMENT ON COLUMN ma_assignment_attribute.editable IS 'Флаг доступности атрибута на изменение';
COMMENT ON COLUMN ma_assignment_attribute.lastchange IS 'Ключ для синхронизации изменений';
COMMENT ON COLUMN ma_assignment_attribute.attr_name IS 'Имя атрибута';
COMMENT ON COLUMN ma_assignment_attribute.required IS 'Флаг обязательности';
COMMENT ON COLUMN ma_assignment_attribute.attr_value IS 'Значение атрибута';
COMMENT ON COLUMN ma_assignment_attribute.value_type IS 'Тип значения атрибута';
COMMENT ON COLUMN ma_assignment_attribute.visible IS 'Флаг видимости';

-- COMMENT ON TABLE ma_assignment_task_controls
COMMENT ON TABLE ma_assignment_task_controls IS 'Действия по поручению (подчиненная для ma_assignment_tasks)';
COMMENT ON COLUMN ma_assignment_task_controls.asign_tc_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_assignment_task_controls.assignment_task_id IS 'Ссылка на задачу. (assignment_task_id->ma_assignment_tasks(asign_tas_pk))';
COMMENT ON COLUMN ma_assignment_task_controls.description IS 'Описание действия';
COMMENT ON COLUMN ma_assignment_task_controls.lastchange IS 'Ключ для синхронизации изменений';
COMMENT ON COLUMN ma_assignment_task_controls.priority IS 'Приоритет для сортировки при отображении в форме';
COMMENT ON COLUMN ma_assignment_task_controls.required IS 'Флаг обязательности';
COMMENT ON COLUMN ma_assignment_task_controls.validation_rule IS 'Строка шаблона валидации';
COMMENT ON COLUMN ma_assignment_task_controls.control_value IS 'Значение по факту исполнения';
COMMENT ON COLUMN ma_assignment_task_controls.value_type IS 'Тип значения (перечисление)';

-- COMMENT ON TABLE ma_assignment_tasks
COMMENT ON TABLE ma_assignment_tasks IS 'Задача по поручению (подчиненная для ma_assignment)';
COMMENT ON COLUMN ma_assignment_tasks.asign_tas_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_assignment_tasks.assignment_id IS 'Ссылка на поручение. (assignment_id->ma_assignment(assign_pk))';
COMMENT ON COLUMN ma_assignment_tasks.description IS 'Описание задачи';
COMMENT ON COLUMN ma_assignment_tasks.lastchange IS 'Ключ для синхронизации изменений';
COMMENT ON COLUMN ma_assignment_tasks.priority IS 'Приоритет для сортировки при отображении в форме';
COMMENT ON COLUMN ma_assignment_tasks.required IS 'Флаг обязательности';
COMMENT ON COLUMN ma_assignment_tasks.version IS 'Версия записи';

-- COMMENT ON TABLE ma_call
COMMENT ON TABLE ma_call IS 'Статистика звонков на мобильном устройстве';
COMMENT ON COLUMN ma_call.call_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_call.assignment_id IS 'Ссылка на поручение, если номер связан с номером телефона в поручении. (assignment_id->ma_assignment(assign_pk))';
COMMENT ON COLUMN ma_call.call_date IS 'Дата/время звонка';
COMMENT ON COLUMN ma_call.call_duration IS 'Продолжительность звонка';
COMMENT ON COLUMN ma_call.call_number IS 'Номер телефона';
COMMENT ON COLUMN ma_call.call_type IS 'Тип (входящий/исходящий)';
COMMENT ON COLUMN ma_call.contact_name IS 'Имя контакта';
COMMENT ON COLUMN ma_call.device_id IS 'Ссылка на мобильное устройство. (device_id->ma_device(device_pk))';
COMMENT ON COLUMN ma_call.lastchange IS 'Ключ для синхронизации изменений';
COMMENT ON COLUMN ma_call.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';

-- COMMENT ON TABLE ma_device
COMMENT ON TABLE ma_device IS 'Мобильные устройства агентов';
COMMENT ON COLUMN ma_device.device_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_device.googleid IS 'Уникальный ключ GMS';
COMMENT ON COLUMN ma_device.lastchange IS 'Ключ для синхронизации изменений';

-- COMMENT ON TABLE ma_location
COMMENT ON TABLE ma_location IS 'Статистика геолокации агентов';
COMMENT ON COLUMN ma_location.loc_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_location.location_date IS 'Дата получения координат';
COMMENT ON COLUMN ma_location.device_id IS 'Ссылка на мобильное устройство. (device_id->ma_device(device_pk))';
COMMENT ON COLUMN ma_location.latitude IS 'Широта';
COMMENT ON COLUMN ma_location.longitude IS 'Долгота';
COMMENT ON COLUMN ma_location.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';

-- COMMENT ON TABLE ma_onboards
COMMENT ON TABLE ma_onboards IS 'Экраны приветствия';
COMMENT ON COLUMN ma_onboards.onboard_id_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_onboards.onboard_contents IS 'Текст экрана';
COMMENT ON COLUMN ma_onboards.onboard_desc IS 'Заголовок экрана';
COMMENT ON COLUMN ma_onboards.onboard_filename IS 'URL изображения экрана';
COMMENT ON COLUMN ma_onboards.onboard_img IS 'Имя файла изображения экрана';

-- COMMENT ON TABLE ma_reason
COMMENT ON TABLE ma_reason IS 'Причины отказа от выполнения поручения';
COMMENT ON COLUMN ma_reason.reason_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_reason.description IS 'Описание причины отказа';
COMMENT ON COLUMN ma_reason.name IS 'Имя причины отказа';
COMMENT ON COLUMN ma_reason.parent_id IS 'Ссылка на родителя. (parent_id->ma_reason(reason_pk))';

-- COMMENT ON TABLE ma_reason
COMMENT ON TABLE ma_settings IS 'Настройки приложения';
COMMENT ON COLUMN ma_settings.sett_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_settings.is_upload_apps IS 'Флаг загрузки активности приложений';
COMMENT ON COLUMN ma_settings.is_upload_calls IS 'Флаг загрузки звонков';
COMMENT ON COLUMN ma_settings.is_upload_location IS 'Флаг загрузки геолокации';
COMMENT ON COLUMN ma_settings.upload_stats_start_time IS 'Время начала выгрузки статистики';
COMMENT ON COLUMN ma_settings.upload_stats_stop_time IS 'Время конца выгрузки статистики';
COMMENT ON COLUMN ma_settings.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';

-- COMMENT ON TABLE ma_sms_pass
COMMENT ON TABLE ma_sms_pass IS 'ОТП пароли';
COMMENT ON COLUMN ma_sms_pass.sms_pwd_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_sms_pass.sms_password IS 'OTP пароль';
COMMENT ON COLUMN ma_sms_pass.endperiod IS 'Время окончания действия пароля';
COMMENT ON COLUMN ma_sms_pass.sms_user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';

-- COMMENT ON TABLE ma_template
COMMENT ON TABLE ma_template IS 'Шаблоны поручений агентов';
COMMENT ON COLUMN ma_template.templ_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_template.description IS 'Описание шаблона';
COMMENT ON COLUMN ma_template.name IS 'Имя шаблона';
COMMENT ON COLUMN ma_template.tmp_tmp_type_id IS 'Ссылка на тип шаблона. (tmp_tmp_type_id->ma_template_types(temp_type_pk))';

-- COMMENT ON TABLE ma_template_attribute
COMMENT ON TABLE ma_template_attribute IS 'Атрибуты шаблона поручения (подчиненная для ma_template)';
COMMENT ON COLUMN ma_template_attribute.templ_attr_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_template_attribute.template_id IS 'Ссылка на шаблон. (template_id->ma_template(templ_pk))';
COMMENT ON COLUMN ma_template_attribute.description IS 'Описание атрибута';
COMMENT ON COLUMN ma_template_attribute.editable IS 'Флаг доступности атрибута на изменение';
COMMENT ON COLUMN ma_template_attribute.name IS 'Имя атрибута';
COMMENT ON COLUMN ma_template_attribute.required IS 'Флаг обязательности';
COMMENT ON COLUMN ma_template_attribute.value IS 'Значение атрибута';
COMMENT ON COLUMN ma_template_attribute.value_type IS 'Тип значения атрибута';
COMMENT ON COLUMN ma_template_attribute.visible IS 'Флаг видимости';

-- COMMENT ON TABLE ma_template_task_controls
COMMENT ON TABLE ma_template_task_controls IS 'Действия по шаблону поручения (подчиненная для ma_template_tasks)';
COMMENT ON COLUMN ma_template_task_controls.temp_tk_contr_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_template_task_controls.template_task_id IS 'Ссылка на задачу. (template_task_id->ma_template_tasks(temp_tk_pk))';
COMMENT ON COLUMN ma_template_task_controls.description IS 'Описание действия';
COMMENT ON COLUMN ma_template_task_controls.priority IS 'Приоритет для сортировки при отображении в форме';
COMMENT ON COLUMN ma_template_task_controls.required IS 'Флаг обязательности';
COMMENT ON COLUMN ma_template_task_controls.validation_rule IS 'Строка шаблона валидации';
COMMENT ON COLUMN ma_template_task_controls.value_type IS 'Тип значения (перечисление)';

-- COMMENT ON TABLE ma_template_tasks
COMMENT ON TABLE ma_template_tasks IS 'Задача по поручению (подчиненная для ma_template)';
COMMENT ON COLUMN ma_template_tasks.temp_tk_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_template_tasks.template_id IS 'Ссылка на шаблон. (template_id->ma_template(templ_pk))';
COMMENT ON COLUMN ma_template_tasks.description IS 'Описание задачи';
COMMENT ON COLUMN ma_template_tasks.priority IS 'Приоритет для сортировки при отображении в форме';
COMMENT ON COLUMN ma_template_tasks.required IS 'Флаг обязательности';

-- COMMENT ON TABLE ma_template_types
COMMENT ON TABLE ma_template_types IS 'Типы поручений (продукты)';
COMMENT ON COLUMN ma_template_types.temp_type_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_template_types.temp_type_desc IS 'Наименование типа поручения (продукта)';
COMMENT ON COLUMN ma_template_types.parent_temp_tp_pk IS 'Ссылка на родителя. (parent_temp_tp_pk->ma_template_types(temp_type_pk))';

-- COMMENT ON TABLE ma_temporary_user
COMMENT ON TABLE ma_temporary_user IS 'Таблица временных пользователей (первоначальная регистрация)';
COMMENT ON COLUMN ma_temporary_user.temp_usr_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_temporary_user.tmp_usr_device_id IS 'Уникальный номер мобильного устройства';
COMMENT ON COLUMN ma_temporary_user.tmp_e_mail IS 'e-mail';
COMMENT ON COLUMN ma_temporary_user.tmp_confirm_expiry IS 'Окончание действия OTP пароля';
COMMENT ON COLUMN ma_temporary_user.tmp_first_name IS 'Имя';
COMMENT ON COLUMN ma_temporary_user.tmp_otp IS 'OTP пароль';
COMMENT ON COLUMN ma_temporary_user.tmp_pwd IS 'Пароль';
COMMENT ON COLUMN ma_temporary_user.tmp_last_name IS 'Фамилия';
COMMENT ON COLUMN ma_temporary_user.login IS 'Логин (номер телефона)';

-- COMMENT ON TABLE ma_time_config
COMMENT ON TABLE ma_time_config IS 'Конфигурации временных интервалов';
COMMENT ON COLUMN ma_time_config.tconf_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_time_config.tconf_name IS 'Имя интервала';
COMMENT ON COLUMN ma_time_config.tconf_interval_minutes IS 'Интервал ввиде строки HH:mm 00:05';

-- COMMENT ON TABLE ma_tmp_types_roles
COMMENT ON TABLE ma_tmp_types_roles IS 'Список продуктов для роли агента';
COMMENT ON COLUMN ma_tmp_types_roles.temp_type_pk IS 'Ссылка на продукт (temp_type_pk->ma_template_types(temp_type_pk))' ;
COMMENT ON COLUMN ma_tmp_types_roles.usr_rol_pk IS 'Ссылка на роль (usr_rol_pk->ma_user_roles(usr_rol_pk))';

-- COMMENT ON TABLE ma_transactions
COMMENT ON TABLE ma_transactions IS 'Транзакции по счету агента';
COMMENT ON COLUMN ma_transactions.trans_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_transactions.account_number IS 'Ссылка на номер счета (account_number->ma_accounts(account_number))';
COMMENT ON COLUMN ma_transactions.is_increment_operation IS 'Знак опреации (плюс/минус)';
COMMENT ON COLUMN ma_transactions.transactioon_summ IS 'Сумма операции';
COMMENT ON COLUMN ma_transactions.transaction_date IS 'Дата операции';

-- COMMENT ON TABLE ma_user
COMMENT ON TABLE ma_user IS 'Таблица основных пользователей';
COMMENT ON COLUMN ma_user.usr_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_user.e_mail IS 'e-mail';
COMMENT ON COLUMN ma_user.enabled IS 'Флаг актульности пользователя';
COMMENT ON COLUMN ma_user.first_name IS 'Имя';
COMMENT ON COLUMN ma_user.last_name IS 'Фамилия';
COMMENT ON COLUMN ma_user.login IS 'Логин (номер телефона)';
COMMENT ON COLUMN ma_user.u_role IS 'Ссылка на роль (u_role->ma_user_roles(usr_rol_pk))';

-- COMMENT ON TABLE ma_user_device
COMMENT ON TABLE ma_user_device IS 'Связующая таблица пользователь-устройство';
COMMENT ON COLUMN ma_user_device.user_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';
COMMENT ON COLUMN ma_user_device.device_id IS 'Ссылка на мобильное устройство. (device_id->ma_device(device_pk))';

-- COMMENT ON TABLE ma_user_personal
COMMENT ON TABLE ma_user_personal IS 'Обработка блокировки пользователей';
COMMENT ON COLUMN ma_user_personal.user_pers_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_user_personal.usr_pers_att_counter IS 'Счетчик попыток востановления пароля';
COMMENT ON COLUMN ma_user_personal.usr_pers_block_expires IS 'Дата начала блокировки пользователя(вычисление идет начало блокировки + поле из ma_time_config)';
COMMENT ON COLUMN ma_user_personal.usr_pers_for_pwd_expire IS 'Дата начала действия для окончания действия забития пароля (вычисление идет начало блокировки + поле из ma_time_config)';
COMMENT ON COLUMN ma_user_personal.usr_pers_is_blocked IS 'Флаг блокировки пользователя';
COMMENT ON COLUMN ma_user_personal.usr_pers_pwd IS 'Пароль пользователя';
COMMENT ON COLUMN ma_user_personal.ma_usr_id IS 'Ссылка на пользователя. (user_id->ma_user(usr_pk))';
COMMENT ON COLUMN ma_user_personal.usr_pers_wrong_enters IS 'Счетчик неудачных попыток ввода пароля';

-- COMMENT ON TABLE ma_user_roles
COMMENT ON TABLE ma_user_roles IS 'Роли пользователей';
COMMENT ON COLUMN ma_user_roles.usr_rol_pk IS 'Основной ключ';
COMMENT ON COLUMN ma_user_roles.role IS 'Имя роли';