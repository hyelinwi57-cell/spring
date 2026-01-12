create table tbl_sample1(col1 varchar2(500));
create table tbl_sample2(col2 varchar2(50));

select * from tbl_sample1;
delete tbl_sample1;
select * from tbl_sample2;

--tbl_board 테이블 수정
alter table tbl_board add(replycnt number default 0);


--기존의 댓글이 존재했다면, replucnt에 반영
update tbl_board set replycnt=
(select count(rno) from tbl_reply
where tbl_reply.bno = tbl_board.bno);
commit;
select * from tbl_board where replycnt>0;

select * from tbl_board;

--updateReplyCnt
UPDATE TBL_BOARD
SET
    replycnt = 1 + 1
WHERE bno = 15;
