-- scheme user
desc user;

-- insert
insert into user values(null, '조진석', 'wlstjr1123@gmail.com', '1234', 'male', now());

-- select01
select no, name from user where email='wlstjr1123@gmail.com' and password = '1234';

-- select02
select * from user;

-- update
update user
	set password = '1234', name = '조진석', gender = 'male'
where no = 1;

