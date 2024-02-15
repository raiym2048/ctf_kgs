insert into event_type (title)
values ('event_type1')
;
insert into event_type (title)
values ('event_type2')
;
insert into event_type (title)
values ('event_type3')
;
insert into event_type (title)
values ('event_type4')
;
insert into event_type (title)
values ('event_type5')
;
insert into event_type (title)
values ('event_type6')
;
insert into event_type (title)
values ('event_type7')
;
insert into event_type (title)
values ('event_type8')
;
insert into event_type (title)
values ('event_type9')
;
insert into event_type (title)
values ('event_type10')
;
insert into event_format (title)
values ('event_format1')
;
insert into event_format (title)
values ('event_format2')
;
insert into event_format (title)
values ('event_format3')
;
insert into event_format (title)
values ('event_format4')
;
insert into event_format (title)
values ('event_format5')
;
insert into event_status (title)
values ('event_status1')
;
insert into event_status (title)
values ('event_status2')
;
insert into event_status (title)
values ('event_status3')
;
insert into event_status (title)
values ('event_status4')
;
insert into event_status (title)
values ('event_status5')
;
insert into location (title)
values ('location1')
;
insert into location (title)
values ('location2')
;
insert into location (title)
values ('location3')
;
insert into location (title)
values ('location4')
;
insert into location (title)
values ('location5')
;


insert into category (name)
values ('Web')
;
insert into category (name)
values ('Crypto')
;
insert into category (name)
values ('Forensics')
;
insert into category (name)
values ('PWN')
;
insert into category (name)
values ('PPC')
;
insert into category (name)
values ('Misc')
;
insert into category (name)
values ('Recon')
;
insert into category (name)
values ('Reverse')
;
insert into category (name)
values ('OSINT')
;
insert into category (name)
values ('Joys')
;
insert into category (name)
values ('Stegano')
;
insert into category (name)
values ('Mobile')
;
insert into level (name)
values ('level1')
;
insert into level (name)
values ('level2')
;
insert into level (name)
values ('level3')
;
insert into level (name)
values ('level4')
;
insert into level (name)
values ('level5')
;
insert into task (type, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 1, 'desc', null, 1, 'task1', 500, '22.12.2023', 'submit_flag', 'some creator', 0)
;
insert into task (type, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 2, 'desc', null, 2, 'task1', 500, '22.12.2023', 'submit_flag', 'some creator', 0)
;
insert into task (type, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 3, 'desc', null, 3, 'task1', 500, '22.12.2023', 'submit_flag', 'some creator', 0)
;
insert into task (type, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 4, 'desc', null, 4, 'task1', 500, '22.12.2023', 'submit_flag', 'some creator', 0)
;
