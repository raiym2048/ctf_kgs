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
-- real tasks
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 2, 'Help to Caesar to find his password.', 1, 1, 'HelpToCaesar', 500, NOW(), 'alatoo_ctf{jcaesar2023}', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 8, 'This challenge includes basics of reverse engineering, which wrote in Java programming language', 2, 1, 'ColdWallet', 500, NOW(), 'alatoo_ctf{aiu_ctf_2023_wallet_key_}', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 2, 'This challenge includes basics of steganography and working with metadata.', 3, 1, 'There''s always something', 500, NOW(), 'alatoo_ctf{Steganography_is_MY_L0ve}', 'some creator', 0)
;

-- test tasks

insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 3, 'desc', null, 1, 'test1', 500, NOW(), 'sss', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 4, 'desc', null, 1, 'test2', 500, NOW(), 'sss', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (false, 5, 'desc', null, 1, 'test3', 500, NOW(), 'sss', 'some creator', 0)
;

-- test event tasks

insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (true, 3, 'desc', null, 1, 'test1 eve', 500, NOW(), 'sss', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (true, 4, 'desc', null, 1, 'test2 eve', 500, NOW(), 'sss', 'raiym2048', 0)
;
insert into task (is_private, category_id, description, download_file_id, level_id, name, points, release_date, submit_flag,
                  task_creator, user_solves)
values (true, 5, 'desc', null, 1, 'test3 eve', 500, NOW(), 'sss', 'some creator', 0)
;
