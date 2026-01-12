INSERT INTO tbl_board(bno, title, writer, content)
SELECT seq_board.NEXTVAL, title, writer, content 
FROM tbl_board;
commit;

SELECT bno, writer, title, content, regdate, updatedate
from
(select ROWNUM rn, a.*  
from
(SELECT bno, writer, title, content, regdate, updatedate
from tbl_board
order by bno desc) a 
where rownum <= 2*2)    
where rn > (2-1)*2;  