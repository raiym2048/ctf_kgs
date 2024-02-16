insert into event_type (title)
values ('private')
;
insert into event_type (title)
values ('public')
;
insert into event_type (title)
values ('archive')
;
insert into event_format (title)
values ('sherlock')
;
insert into event_format (title)
values ('jeopardy')
;
insert into event_status (title)
values ('ongoing')
;
insert into event_status (title)
values ('upcoming')
;
insert into event_status (title)
values ('past')
;
insert into event_status (title)
values ('joined')
;
insert into location (title)
values ('online')
;
insert into location (title)
values ('offline')
;
insert into location (title)
values ('Bishkek, ankara 1/8')
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
values ('1')
;
insert into level (name)
values ('2')
;
insert into level (name)
values ('3')
;
insert into level (name)
values ('4')
;
insert into level (name)
values ('5')
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 1, 'desc', null, 1, 'task1', 500, NOW(), 'submit_flag', 'some creator', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 2, 'desc', null, 2, 'task2', 500, NOW(), 'submit_flag', 'some creator', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 3, 'desc', null, 3, 'task3', 500, NOW(), 'submit_flag', 'some creator', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 4, 'desc', null, 4, 'task4', 500, NOW(), 'submit_flag', 'some creator', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (true, 4, 'desc', null, 1, 'task5', 500, NOW(), 'submit_flag', 'some creator', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (true, 3, 'desc', null, 1, 'task6', 500, NOW(), 'submit_flag', 'some creator', 0)
;
