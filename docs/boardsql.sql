desc board;

-- insert
insert into board value (null, '안녕', '안녕하세요', '0', now(), (select ifnull(max(group_no), 0)+1 from board a), 0, 0, 1);

-- answer insert
insert into board value (null, '그래', '안녕', '0', now(), 54, 1, 1, 1);

-- select 01
select * from board;

-- select list
select b.no, b.title, b.contents, b.hit, b.reg_date, b.group_no, b.order_no, b.depth, b.user_no, u.name
	from board b join `user` u on b.user_no = u.no
	order by group_no desc,  order_no asc
    limit 0, 10;
    
select b.no, b.title, b.contents, b.hit, b.reg_date, b.group_no, b.order_no, b.depth, b.user_no
	from board b
    where b.no = 54;
    