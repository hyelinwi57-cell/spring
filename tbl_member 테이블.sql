create table tbl_member(
      userid varchar2(50) not null primary key,
      userpw varchar2(100) not null,
      username varchar2(100) not null,
      regdate date default sysdate, 
      updatedate date default sysdate,
      enabled char(1) default '1' --활성계정, 비활성계정
);
 
create table tbl_member_auth ( --권한 테이블
     userid varchar2(50) not null,
     auth varchar2(50) not null,
     constraint fk_member_auth foreign key(userid) references tbl_member(userid)
);

------
select mem.*, auth.auth
from tbl_member mem left join tbl_member_auth auth
on mem.userid = auth.userid
where mem.userid = 'user0';

select * from tbl_member;



