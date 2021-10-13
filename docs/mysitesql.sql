-- scheme user
desc user;

-- insert
insert into user values(null, '둘리', 'dooly@gmail.com', '1234', 'male', now());

-- select01
select no, name from user where email='wlstjr1123@gmail.com' and password = '1234';

-- select02
select * from user;

-- update
update user
	set password = '1234'
		and name = '홍길동'
        and gender = 'female'
where no = 1;

