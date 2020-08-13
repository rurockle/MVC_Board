show tables;


CREATE TABLE board(
board_num INT PRIMARY KEY,
board_name VARCHAR(20) NOT NULL,
board_pass VARCHAR(15) NOT NULL,
board_subject VARCHAR(50) NOT NULL,
board_content VARCHAR(2000) NOT NULL,
board_file VARCHAR(50) NOT NULL,
board_re_ref INT NOT NULL,
board_re_lev INT NOT NULL,
board_re_seq INT NOT NULL,
board_readcount INT DEFAULT 0,
board_date DATE);

Alter table board ADD board_ip VARCHAR(25);

board_ip VARCHAR(25));

desc board

DROP table board;

SELECT * FROM BOARD;

SELECT * FROM board ORDER BY board_re_ref DESC, board_re_seq LIMIT 1,10;

SELECT * FROM board ORDER BY board_re_ref DESC, board_re_seq ASC LIMIT 1,10;