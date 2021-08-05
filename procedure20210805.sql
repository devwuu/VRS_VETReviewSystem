--------------------------------------------------------
--  파일이 생성됨 - 목요일-8월-05-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure P_ADMIN_LOGIN
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_ADMIN_LOGIN" (p_email member.email%TYPE,
                                         p_pw member.pw%TYPE,
                                         cur_admin OUT SYS_REFCURSOR,
                                          reg_mem out NUMBER,
                                          del_mem out  NUMBER)
IS
    v_pw member.pw%TYPE;
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    SELECT pw
    INTO v_pw
    FROM member
    WHERE email = p_email;

    IF v_pw = p_pw THEN
        OPEN cur_admin
        FOR
         SELECT m.email, pw, nickname,(SELECT c.codename
                                FROM code c
                                 WHERE g.codevalue = c.codevalue
                                  AND c.codename = '관리자') codename
        FROM member m, grade g
        WHERE g.email = p_email
        AND isdel = 'N'
        AND m.email = p_email;


        SELECT COUNT(*)
        INTO reg_mem
        FROM member
        WHERE isdel is not null;

        SELECT COUNT(*)
        INTO del_mem
        FROM member
        WHERE isdel = 'Y';

    END IF;

    EXCEPTION
        WHEN OTHERS THEN
        
               -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
              p_name := $$PLSQL_UNIT;
              p_errorlog :=  SQLERRM;
             
             rollback;

              INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
              
              commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_BOOKMARK_PROC
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_BOOKMARK_PROC" ( v_review_no review.review_no%TYPE,
                                              v_review_url bookmark.url_content%TYPE,
                                              v_email member.email%TYPE,
                                              v_stat OUT NUMBER)
IS
    v_cnt NUMBER;
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    SELECT COUNT(*)
    INTO v_cnt
    FROM bookmark
    WHERE email = v_email
    AND reviewno = v_review_no;

    IF v_cnt >0 THEN
        DELETE FROM bookmark WHERE email = v_email AND reviewno = v_review_no;

        v_stat := 2;

    ELSIF v_cnt = 0 THEN
        INSERT INTO bookmark (bookmarkno, email, reviewno, url_content) 
        VALUES (bookmarkNo.nextval, v_email, v_review_no, v_review_url);

        v_stat := 1;

    END IF;

    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=   SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_HOSPITAL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_HOSPITAL" (v_hosNo hospital.hospitalno%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    DELETE FROM review WHERE hospitalno = v_hosno;
    DELETE FROM hospitaltag WHERE hospitalno = v_hosno;
    DELETE FROM hospital WHERE hospitalno = v_hosno;

    commit;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=   SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_MEMBER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_MEMBER" (v_email member.email%TYPE,
                                         v_pw member.pw%TYPE,
                                         v_pw_stat out NUMBER)
IS
    v_pw_check member.pw%TYPE;
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    SELECT pw 
    INTO v_pw_check
    FROM member
    WHERE email = v_email;

    IF v_pw_check = v_pw THEN
        v_pw_stat := 1;
        UPDATE member SET isdel = 'Y', deldate = current_timestamp WHERE email = v_email;
        COMMIT;
    END IF;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=   SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_NOTICE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_NOTICE" (v_notice_no notice.noticeno%TYPE,
                                         cur_noticefileup OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_noticefileup FOR
    SELECT filenamesave
    FROM noticefileup
    WHERE noticeno = v_notice_no;

    DELETE FROM noticefileup WHERE noticeno = v_notice_no;
    DELETE FROM notice WHERE noticeno = v_notice_no;

    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=   SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_REVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_REVIEW" (v_review_no review.review_no%TYPE,
                                         cur_fileup OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_fileup FOR
    SELECT filenamesave
    FROM fileup
    WHERE reviewno = v_review_no;

    DELETE FROM fileup WHERE reviewno = v_review_no;
    DELETE FROM reply WHERE reviewno = v_review_no;
    DELETE FROM bookmark WHERE reviewno = v_review_no;
    DELETE FROM review WHERE review_no = v_review_no;

    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=   SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_SNSFILEUP
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_SNSFILEUP" (v_snsreno snsreview.snsreno%TYPE,
                                            cur_snsfileup OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_snsfileup FOR
    SELECT filenamesave
    FROM snsfileup
    WHERE snsreno = v_snsreno;

    DELETE FROM snsfileup WHERE snsreno = v_snsreno;

    COMMIT;

    EXCEPTION 
        WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_DEL_SNSREVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DEL_SNSREVIEW" (v_snsNo snsreview.snsreno%TYPE,
                                           cur_fileup OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    DELETE FROM snsreview WHERE snsreno = v_snsno;

    OPEN cur_fileup FOR
    SELECT filenamesave
    FROM snsfileup
    WHERE snsreno = v_snsno;

    DELETE FROM snsfileup WHERE snsreno = v_snsno;

    COMMIT;

    EXCEPTION 
        WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_DELETE_INTEREST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DELETE_INTEREST" (v_email interest.email%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    DELETE FROM interest WHERE email = v_email;

    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
              -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_DELFILE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DELFILE" (v_review_no review.review_no%TYPE,
                                      cur_filenamesave OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    OPEN cur_filenamesave FOR
    SELECT filenamesave 
    FROM fileup
    WHERE reviewno = v_review_no;

    DELETE FROM fileup WHERE reviewno = v_review_no;
    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_DELFILE_NOTICE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_DELFILE_NOTICE" (v_notice_no notice.noticeno%TYPE,
                                            cur_filenamesave OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    OPEN cur_filenamesave FOR
    SELECT filenamesave 
    FROM noticefileup
    WHERE noticeno = v_notice_no;

    DELETE FROM noticefileup WHERE noticeno = v_notice_no;
    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;

          rollback;

          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_BOARDLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_BOARDLIST" (v_hospitalNo NUMBER,
                                            v_pagenum NUMBER,
                                            cur_review OUT SYS_REFCURSOR,
                                            v_totlepage OUT NUMBER,
                                            cur_reviewmag OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_review FOR
    SELECT * 
    FROM (SELECT /*+ INDEX_DESC (r SYS_C0011486) */
          rownum num,
          review_no,
          email,
          title,
          content,
          count,
          to_char(wdate, 'YY-MM-DD') wdate,
          nvl(to_char(mdate, 'YY-MM-DD'),'-')  mdate
          FROM review r
          WHERE hospitalno = v_hospitalNo
          AND rownum <= v_pagenum*10)
    WHERE num > (v_pagenum-1)*10;
    
    SELECT CEIL(COUNT(*)/10)
    INTO v_totlepage
    FROM review r
    WHERE hospitalno = v_hospitalNo;
    
    OPEN cur_reviewmag FOR
    SELECT  review_no,
        COUNT(CASE WHEN ( SELECT codename
                        FROM code c
                        WHERE c.category = '관리'
                        AND c.codevalue = m.magcode) = '추천'
    
               THEN 1 END) recommend,
        COUNT(CASE WHEN(SELECT codename
                        FROM code c
                        WHERE c.category = '관리'
                        AND c.codevalue = m.magcode) ='신고'
               THEN 0 END) report
    FROM review r, reviewmag m
    WHERE r.review_no = m.to_review(+)
    GROUP BY review_no;
        

    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog :=  SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_BOOKMARKLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_BOOKMARKLIST" (v_email member.email%TYPE,
                                               cur_bookmark_list OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

OPEN  cur_bookmark_list FOR
    SELECT r.email,
           title,
           content,
           TO_CHAR(r.wdate,'YY-MM-DD') wdate,
           r.count,
           url_content
    FROM bookmark b, review r
    WHERE b.email = v_email
    AND b.reviewno = r.review_no
    ORDER BY b.wdate desc;

EXCEPTION
WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog :=  SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_CODE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_CODE" (v_category code.category%TYPE,
                                        code_cur OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN code_cur
    FOR SELECT codename, codevalue
    FROM code
    WHERE category = v_category;

    EXCEPTION
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_DELMEMLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_DELMEMLIST" (cur_mem OUT SYS_REFCURSOR,
                                            cur_mem_mag OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
   OPEN cur_mem FOR
    SELECT 
        m.email,
        nickname,
        TO_CHAR(m.wdate, 'YYYY-MM-DD') wdate,
        isdel,
        nvl(TO_CHAR(deldate, 'YYYY-MM-DD'),' ') deldate,
        nvl((SELECT codename
             FROM code c
             WHERE  c.category = '회원등급'
             AND c.codevalue = g.codevalue), '일반회원') gradename
        FROM member m, grade g
       where isdel='Y'
        AND m.email=g.email(+)
        ORDER BY 
        m.deldate,
        m.wdate desc;
        
        OPEN cur_mem_mag FOR
        SELECT email,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue) ='추천' then 1  end) as recommend,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue)='신고' then 1  end) as  report
                
        FROM membermag ma, member m
        
        WHERE m.email = ma.to_mem(+)
        GROUP BY email;
        
        

          EXCEPTION 
          WHEN OTHERS THEN
          
              -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
              p_name := $$PLSQL_UNIT;
              p_errorlog := SQLERRM;

              rollback;

              INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
              commit;

           
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_HOSPITAL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_HOSPITAL" (cur_hospital OUT SYS_REFCURSOR,
                                            cur_code OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    
    OPEN cur_code FOR
    SELECT *
    FROM code
    WHERE category = '병원태그';


    OPEN cur_hospital FOR
    SELECT h.hospitalno,
           hospitalname,
           hospitaltel,
           post,
           hospitaladd1,
           hospitaladd2,
           hospitaladd3,
           TO_CHAR(h.wdate, 'YY-MM-DD') wdate,
           nvl(codename,'-') codename,
           codevalue

    FROM hospital h, 
        (SELECT  hospitalno,
                 codename,
                 c.codevalue
        FROM hospitaltag t, code c
        WHERE category = '병원태그' 
        AND c.codevalue = t.codevalue) j

    WHERE h.hospitalno = j.hospitalno(+)

    ORDER BY h.wdate DESC;
    
    
    EXCEPTION
        WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
    

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_HOSPITAL_USER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_HOSPITAL_USER" (v_location in VARCHAR2,
                                            cur_hospital OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    OPEN cur_hospital FOR
    SELECT h.hospitalno,
           hospitalname,
           hospitaltel,
           post,
           hospitaladd1,
           hospitaladd2,
           hospitaladd3,
           TO_CHAR(h.wdate, 'YY-MM-DD') wdate,
           nvl(codename,'-') codename,
           codevalue

    FROM hospital h, 
        (SELECT  hospitalno,
                 codename,
                 c.codevalue
        FROM hospitaltag t, code c
        WHERE category = '병원태그' 
        AND c.codevalue = t.codevalue) j

    WHERE hospitaladd1 LIKE '%'||v_location||'%'
        AND  h.hospitalno = j.hospitalno(+)

    ORDER BY h.wdate DESC;
    
    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog :=  SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
    
    

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_MEMLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_MEMLIST" ( cur_mem OUT SYS_REFCURSOR,
                                            cur_mem_mag OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN


   OPEN cur_mem FOR
     SELECT 
            m.email,
            nickname,
            TO_CHAR(m.wdate, 'YYYY-MM-DD') wdate,
            isdel,
            nvl(TO_CHAR(deldate, 'YYYY-MM-DD'),' ') deldate,
            nvl((SELECT codename
                 FROM code c
                 WHERE  c.category = '회원등급'
                 AND c.codevalue = g.codevalue), '일반회원') gradename
            FROM member m, grade g
            where m.email=g.email(+)
            AND isdel is not null
            ORDER BY m.wdate desc;
            
        
       OPEN cur_mem_mag FOR
        SELECT email,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue) ='추천' then 1  end) as recommend,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue)='신고' then 1  end) as  report
                
        FROM membermag ma, member m
        
        WHERE m.email = ma.to_mem(+)
        GROUP BY email;
         
    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog :=  SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_NOTICE_CONTENT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_NOTICE_CONTENT" (v_notice_no notice.noticeno%TYPE,
                                                 cur_notice_content OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
   OPEN   cur_notice_content FOR

    SELECT n.noticeno,
              email,
              title,
              content,
              to_char(n.wdate, 'YY-MM-DD') n_wdate,
              count,
              nvl(to_char(n.mdate, 'YY-MM-DD'),'-')  n_mdate,
              noticefileno,
              filename,
              filenamesave,
              filesize,
              filetype,
              filepath,
            to_char(f.wdate, 'YY-MM-DD') f_wdate,
            (SELECT codename 
            FROM code c, grade g
            WHERE g.email = n.email
            AND c.category = '회원등급' 
            AND c.codevalue = g.codevalue) gradeName
   FROM notice n, noticefileup f
   WHERE n.noticeno = v_notice_no
         AND n.noticeno = f.noticeno(+);

   UPDATE notice SET count = count +1 WHERE noticeno =  v_notice_no;
   COMMIT;

   EXCEPTION
   WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog :=  SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_NOTICELIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_NOTICELIST" (cur_notice OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_notice FOR
    SELECT noticeno,
          n.email,
          title,
          content,
          count,
          to_char(n.wdate, 'YY-MM-DD') wdate,
          nvl(to_char(mdate, 'YY-MM-DD'),'-')  mdate,
          (SELECT codename
           FROM code c
           WHERE c.category='회원등급'
            AND c.codevalue=g.codevalue) gradename

    FROM notice n, grade g
    WHERE n.email = g.email
    ORDER BY n.wdate desc;

    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_NOTICELIST_TOP5
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_NOTICELIST_TOP5" (cur_notice OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_notice FOR
   SELECT *
    FROM (SELECT noticeno,
          n.email,
          title,
          content,
          count,
          to_char(n.wdate, 'YY-MM-DD') wdate,
          nvl(to_char(mdate, 'YY-MM-DD'),'-')  mdate,
          (SELECT codename
           FROM code c
           WHERE c.category='회원등급'
            AND c.codevalue=g.codevalue) gradename 

           FROM notice n, grade g
           WHERE n.email = g.email
           ORDER BY n.wdate desc)
    WHERE rownum <=5;

    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_REVIEW_CONTENT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_REVIEW_CONTENT" (v_review_no review.review_no%TYPE,
                                                 cur_review_content OUT SYS_REFCURSOR,
                                                 v_bookmark OUT NUMBER,
                                                 v_email bookmark.email%TYPE,
                                                 v_recommend OUT NUMBER,
                                                 v_report OUT NUMBER)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
   OPEN cur_review_content FOR

  SELECT rp.r_review_no,
          hospitalno,
          rp.r_email,
          title,
          rp.r_content,
          rp.r_wdate r_wdate,
          count,
          rp.r_mdate r_mdate,
          fileno,
          filename,
          filenamesave,
          filesize,
          filetype,
          filepath,
          to_char(f.wdate, 'YY-MM-DD') f_wdate,
          replyno,
          rp.p_email,
          rp.p_content,
          rp.p_wdate p_wdate
              
   FROM fileup f, 
   
        (SELECT r.review_no r_review_no,
                 hospitalno,
                 r.email r_email,
                  title,
                  r.content r_content,
                  to_char(r.wdate, 'YY-MM-DD') r_wdate,
                  count,
                  nvl(to_char(r.mdate, 'YY-MM-DD'),'-')  r_mdate,
                  replyno,
                  p.email p_email,
                  p.content p_content,
                  to_char(p.wdate, 'YY-MM-DD') p_wdate
         FROM review r, reply p
         WHERE review_no = v_review_no
         AND r.review_no = p.reviewno(+)
         ORDER BY p.wdate DESC) rp
         
   WHERE  rp.r_review_no = f.reviewno(+);
   


   UPDATE review SET count = count +1 WHERE review_no =  v_review_no;
   COMMIT;


   SELECT COUNT(*)
   INTO v_bookmark
   FROM bookmark
   WHERE email =  v_email
   AND reviewno = v_review_no;
   
       
    SELECT  COUNT(CASE WHEN ( SELECT codename
                            FROM code c
                            WHERE c.category = '관리'
                            AND c.codevalue = m.magcode) = '추천'
        
                   THEN 1 END) recommend,
            COUNT(CASE WHEN(SELECT codename
                            FROM code c
                            WHERE c.category = '관리'
                            AND c.codevalue = m.magcode) ='신고'
                   THEN 0 END) report
    INTO v_recommend, v_report
    FROM review r, reviewmag m
    WHERE r.review_no =  v_review_no
    AND r.review_no = m.to_review(+)
    GROUP BY review_no;
   
   
   
   

   EXCEPTION
   WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_SEARCH_DEL_MEMLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_SEARCH_DEL_MEMLIST" ( v_condition VARCHAR2,
                                               cur_mem OUT SYS_REFCURSOR,
                                               cur_mem_mag OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
   OPEN cur_mem FOR
    SELECT 
        m.email,
        nickname,
        TO_CHAR(m.wdate, 'YYYY-MM-DD') wdate,
        isdel,
        nvl(TO_CHAR(deldate, 'YYYY-MM-DD'),' ') deldate,
        nvl((SELECT codename
             FROM code c
             WHERE  c.category = '회원등급'
             AND c.codevalue = g.codevalue), '일반회원') gradename
        FROM member m, grade g
       where isdel='Y'
        AND m.email=g.email(+)
        AND deldate >= add_months(to_date(v_condition, 'YYYY-MM-DD'), +1) 
        ORDER BY 
        m.deldate,
        m.wdate desc;
        
        
    OPEN cur_mem_mag FOR
        SELECT email,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue) ='추천' then 1  end) as recommend,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue)='신고' then 1  end) as  report
                
        FROM membermag ma, member m
        
        WHERE m.email = ma.to_mem(+)
        GROUP BY email;


          EXCEPTION 
          WHEN OTHERS THEN
              -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
              p_name := $$PLSQL_UNIT;
              p_errorlog := SQLERRM;
        
              rollback;
        
              INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
              commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_SEARCH_MEMLIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_SEARCH_MEMLIST" (v_grade code.codevalue%TYPE,
                                                 v_condition member.email%TYPE,
                                                 cur_mem OUT SYS_REFCURSOR,
                                                cur_mem_mag OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    OPEN cur_mem FOR
    SELECT 
        m.email,
        nickname,
        TO_CHAR(m.wdate, 'YYYY-MM-DD') wdate,
        isdel,
        nvl(TO_CHAR(deldate, 'YYYY-MM-DD'),' ') deldate,
        nvl((SELECT codename
             FROM code c
             WHERE  c.category = '회원등급'
             AND c.codevalue = g.codevalue), '일반회원') gradename
        FROM member m 
        FULL OUTER JOIN grade g
        ON m.email=g.email
        WHERE isdel is not null AND
        (

           (  v_grade = 0 AND
             m.email like '%'||v_condition||'%') 

        OR
           ( v_grade != 0 AND
                (v_condition IS NOT NULL AND
                     g.codevalue = v_grade AND
                     m.email like '%'||v_condition||'%') OR
                (v_condition IS NULL AND
                     g.codevalue = v_grade)
            )

        )
        ORDER BY m.wdate desc;

     OPEN cur_mem_mag FOR
        SELECT email,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue) ='추천' then 1  end) as recommend,
                count(case when (SELECT codename
                                    FROM code c
                                    WHERE c.category = '관리'
                                    AND ma.magcode = c.codevalue)='신고' then 1  end) as  report
                
        FROM membermag ma, member m
        
        WHERE m.email = ma.to_mem(+)
        GROUP BY email;


     EXCEPTION 
      WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_GET_SNS_LIST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GET_SNS_LIST" (v_hospitalNo NUMBER,
                                            cur_snslist OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN cur_snslist for
      SELECT sr.snsreno,
            hospitalno,
           snscontent,
           email,
           TO_CHAR(sr.wdate, 'YY-MM-DD') wdate,
           filename,
           filenamesave,
           filesize,
           filetype,
           filepath
    FROM snsReview sr,
         snsfileup sf
    WHERE hospitalno = v_hospitalNo
        AND sr.snsreno = sf.snsreno(+)
    ORDER by sr.wdate desc;
    
    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;
    
    
END;

/
--------------------------------------------------------
--  DDL for Procedure P_GETINFOR_MEMBER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_GETINFOR_MEMBER" (v_email member.email%TYPE,
                                              mem_cur OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    OPEN mem_cur FOR
    SELECT m.email, nickname, codevalue, (select codename
                                      from code c
                                      where 
                                      c.category = '관심사' AND
                                      c.codevalue = i.codevalue) codename
    FROM member m, interest i
    WHERE m.email = v_email
    AND m.email = i.email(+);
    
    EXCEPTION 
    WHEN OTHERS THEN
      -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
      p_name := $$PLSQL_UNIT;
      p_errorlog := SQLERRM;

      rollback;

      INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
      commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_HOSPITAL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_HOSPITAL" (v_hospitalname hospital.hospitalname%TYPE,
                                              v_hospitaltel hospital.hospitaltel%TYPE,
                                              v_post hospital.post%TYPE,
                                              v_hospitaladd1 hospital.hospitaladd1%TYPE,
                                              v_hospitaladd2 hospital.hospitaladd2%TYPE,
                                              v_hospitaladd3 hospital.hospitaladd3%TYPE,
                                              v_hospitalno OUT hospital.hospitalno%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    INSERT INTO hospital (hospitalno, hospitalname, hospitaltel, post, hospitaladd1, hospitaladd2, hospitaladd3)
    VALUES(hospitalno.nextval, v_hospitalname, v_hospitaltel, v_post, v_hospitaladd1, v_hospitaladd2, v_hospitaladd3);

    v_hospitalno := HOSPITALNO.currval;

     EXCEPTION 
       WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_HOSTAG
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_HOSTAG" (v_hospitalno hospitaltag.hospitalno%TYPE,
                                             v_codevalue hospitaltag.codevalue%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    INSERT INTO hospitaltag (tagno, hospitalno, codevalue) VALUES (TAGNO.nextval, v_hospitalno, v_codevalue);

    EXCEPTION
        WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_INTEREST
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_INTEREST" (v_codeValue interest.codevalue%TYPE,
                                              v_email interest.email%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    INSERT INTO interest (interno, email, codevalue)
    VALUES (interno.nextval, v_email, v_codeValue);

    COMMIT;

     EXCEPTION 
       WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_NOTICE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_NOTICE" (
                                            v_email notice.email%TYPE,
                                            v_title notice.title%TYPE,
                                            v_content notice.content%TYPE,
                                            v_filename noticefileUp.filename%TYPE,
                                            v_filenamesave noticefileUp.filenamesave%TYPE,
                                            v_filesize noticefileUp.filesize%TYPE,
                                            v_filetype noticefileUp.filetype%TYPE,
                                            v_filePath noticefileUp.filepath%TYPE,
                                            v_noticeno out number
                                            )
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    INSERT INTO notice (noticeno, email, title, content)
    VALUES (noticeNo.nextval, v_email, v_title, v_content);

    v_noticeno := noticeNo.currval;

    IF v_filename IS NOT NULL THEN

        INSERT INTO noticefileUp (noticefileNo, filename, filenamesave, filesize, filetype, filepath,  noticeno)
        VALUES (noticefileNo.nextval, v_filename,  v_filenamesave, v_filesize,  v_filetype, v_filePath, v_noticeno );

    END IF;

    commit;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_REVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_REVIEW" (
                                            v_hospitalno NUMBER,
                                            v_email review.email%TYPE,
                                            v_title review.title%TYPE,
                                            v_content review.content%TYPE,
                                            v_filename fileup.filename%TYPE,
                                            v_filenamesave fileup.filenamesave%TYPE,
                                            v_filesize fileup.filesize%TYPE,
                                            v_filetype fileup.filetype%TYPE,
                                            v_filePath fileup.filepath%TYPE,
                                            v_reviewno out number
                                            )
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    INSERT INTO review (review_no, hospitalno, email, title, content)
    VALUES (review_no.nextval, v_hospitalno, v_email, v_title, v_content);

    v_reviewno := review_no.currval;

    IF v_filename IS NOT NULL THEN

        INSERT INTO fileup (fileno, filename, filenamesave, filesize, filetype, filepath, reviewno)
        VALUES (fileno.nextval, v_filename,  v_filenamesave, v_filesize,  v_filetype, v_filePath, v_reviewno );

    END IF;

    commit;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_INSERT_SNSREVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_INSERT_SNSREVIEW" (v_hospitalNo NUMBER,
                                               v_snscontent snsreview.snscontent%TYPE,
                                               v_email snsreview.email%TYPE,
                                               v_filename snsfileup.filename%TYPE,
                                               v_filenamesave snsfileup.filenamesave%TYPE,
                                               v_filesize snsfileup.filesize%TYPE,
                                               v_filetype snsfileup.filetype%TYPE,
                                               v_filepath snsfileup.filepath%TYPE)
IS
    v_snsreviewno NUMBER;
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    insert into snsReview (snsreno, hospitalno, snscontent, email) VALUES (snsreno.nextval, v_hospitalNo, v_snscontent, v_email);

    v_snsreviewno := snsreno.CURRVAL;

    IF v_filename IS NOT NULL THEN
        INSERT INTO snsfileup (snsfileno, filename, filenamesave, filesize, filetype, filepath, snsreno)
        VALUES (snsfileno.nextval, v_filename, v_filenamesave, v_filesize, v_filetype, v_filepath, v_snsreviewno);
    END IF;

    commit;

    EXCEPTION 
        WHEN OTHERS THEN
         -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_JOIN_MEMBER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_JOIN_MEMBER" (v_email member.email%TYPE,
                                          v_pw member.pw%TYPE)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    INSERT INTO MEMBER (email, pw, nickname) values (v_email, v_pw, v_email);
    INSERT INTO grade (grade_no, email, codevalue) values (grade_no.nextval, v_email, '2');

    COMMIT;

    EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;



END;

/
--------------------------------------------------------
--  DDL for Procedure P_MEMBER_UPDATE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_MEMBER_UPDATE" (v_pw member.pw%TYPE,
                                            v_pwUpdate member.pw%TYPE,
                                            v_email member.email%TYPE,
                                            v_nickName member.nickname%TYPE,
                                            v_stat out NUMBER
                                            )
IS
    pwCheck member.pw%TYPE;
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    SELECT pw 
    INTO pwCheck
    FROM member
    WHERE email = v_email;

    IF v_pw = pwCheck THEN
        v_stat := 1;
        UPDATE member SET nickname = v_nickName, pw = v_pwUpdate
        WHERE email = v_email;
        COMMIT;
    ELSE
        v_stat := 0;
    END IF;

    EXCEPTION 
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_SEARCH_REVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_SEARCH_REVIEW" (v_select VARCHAR2,
                                            v_condition VARCHAR2,
                                            v_pagenum NUMBER,
                                            v_hosNo NUMBER,
                                            v_totalpage OUT NUMBER,
                                            cur_reviewList OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    OPEN cur_reviewlist FOR

        SELECT * 
        FROM ( SELECT /*+ INDEX_DESC (r SYS_C0011486) */
               rownum num,
               review_no,
               email,
               title,
               content,
               count,
               to_char(wdate, 'YY-MM-DD') wdate,
               nvl(to_char(mdate, 'YY-MM-DD'), '-') mdate
               FROM review r
               WHERE    hospitalno =  v_hosNo AND
                        (
                        (v_select = 'email' AND
                        email like '%'||v_condition||'%') OR

                        (v_select = 'title' AND
                        title like '%'||v_condition||'%') OR

                        (v_select = 'content' AND
                        content like '%'||v_condition||'%')
                    ) AND rownum <=10*v_pagenum
              )

        WHERE num > (v_pagenum -1)*10;

        SELECT CEIL(COUNT(*)/10)
        INTO  v_totalpage
        FROM review r
        WHERE    hospitalno =  v_hosNo AND
                (
                (v_select = 'email' AND
                email like '%'||v_condition||'%') OR

                (v_select = 'title' AND
                title like '%'||v_condition||'%') OR

                (v_select = 'content' AND
                content like '%'||v_condition||'%')
             );


 EXCEPTION
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;
END;

/
--------------------------------------------------------
--  DDL for Procedure P_UPDATE_HOSPITAL
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_UPDATE_HOSPITAL" (v_hosno hospital.hospitalno%TYPE,
                                              v_hosname hospital.hospitalname%TYPE,
                                              v_hospitaltel hospital.hospitaltel%TYPE,
                                              v_post hospital.post%TYPE,
                                              v_hospitaladd1 hospital.hospitaladd1%TYPE,
                                              v_hospitaladd2 hospital.hospitaladd2%TYPE,
                                              v_hospitaladd3 hospital.hospitaladd3%TYPE
                                              )
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    UPDATE hospital SET hospitalname = v_hosname, hospitaltel = v_hospitaltel, post = v_post, hospitaladd1 = v_hospitaladd1,
                        hospitaladd2 = v_hospitaladd2, hospitaladd3 = v_hospitaladd3
    WHERE hospitalno = v_hosno;

    EXCEPTION 
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;


END;

/
--------------------------------------------------------
--  DDL for Procedure P_UPDATE_NOTICE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_UPDATE_NOTICE" (
                                            v_noticeno notice.noticeno%TYPE,
                                            v_title notice.title%TYPE,
                                            v_content notice.content%TYPE,
                                            v_filename noticefileup.filename%TYPE,
                                            v_filenamesave noticefileup.filenamesave%TYPE,
                                            v_filesize noticefileup.filesize%TYPE,
                                            v_filetype noticefileup.filetype%TYPE,
                                            v_filePath noticefileup.filepath%TYPE
                                            )
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    UPDATE notice SET title = v_title, content = v_content, mdate = current_timestamp
    WHERE noticeno = v_noticeno;

    IF v_filename IS NOT NULL THEN

        INSERT INTO noticefileup (noticefileno, filename, filenamesave, filesize, filetype, filepath, noticeno)
        VALUES (noticefileno.nextval, v_filename,  v_filenamesave, v_filesize,  v_filetype, v_filePath, v_noticeno );

    END IF;

    commit;

    EXCEPTION 
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_UPDATE_REVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_UPDATE_REVIEW" (
                                            v_reviewno review.review_no%TYPE,
                                            v_title review.title%TYPE,
                                            v_content review.content%TYPE,
                                            v_filename fileup.filename%TYPE,
                                            v_filenamesave fileup.filenamesave%TYPE,
                                            v_filesize fileup.filesize%TYPE,
                                            v_filetype fileup.filetype%TYPE,
                                            v_filePath fileup.filepath%TYPE
                                            )
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN
    UPDATE review SET title = v_title, content = v_content, mdate = current_timestamp
    WHERE review_no = v_reviewno;

    IF v_filename IS NOT NULL THEN

        INSERT INTO fileup (fileno, filename, filenamesave, filesize, filetype, filepath, reviewno)
        VALUES (fileno.nextval, v_filename,  v_filenamesave, v_filesize,  v_filetype, v_filePath, v_reviewno );

    END IF;

    commit;

    EXCEPTION 
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
--------------------------------------------------------
--  DDL for Procedure P_UPDATESNSREVIEW
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BB"."P_UPDATESNSREVIEW" (v_snsreno snsreview.snsreno%TYPE,
                                              v_snscontent snsreview.snscontent%TYPE,
                                              v_filename snsfileup.filename%TYPE,
                                              v_filesavename snsfileup.filenamesave%TYPE,
                                              v_filesize snsfileup.filesize%TYPE,
                                              v_filetype snsfileup.filetype%TYPE,
                                              v_filepath snsfileup.filepath%TYPE,
                                              cur_snsfileup OUT SYS_REFCURSOR)
IS
    p_name VARCHAR(100);
    p_errorlog VARCHAR(255);
BEGIN

    UPDATE snsreview SET snscontent = v_snscontent WHERE snsreno = v_snsreno;

    IF  v_filename IS NOT NULL THEN
        OPEN cur_snsfileup FOR
        SELECT filenamesave
        FROM snsfileup
        WHERE snsreno = v_snsreno;

        DELETE FROM snsfileup WHERE snsreno = v_snsreno;

        INSERT INTO snsfileup (snsfileno, filename, filenamesave, filesize, filetype, filepath, snsreno) 
        VALUES (snsfileno.nextval, v_filename, v_filesavename, v_filesize, v_filetype, v_filepath, v_snsreno);
    END IF;

    COMMIT;

    EXCEPTION 
    WHEN OTHERS THEN
          -- 키워드 직접 사용시 컴파일 에러 발생. 변수에 대입하여 사용한다.
          p_name := $$PLSQL_UNIT;
          p_errorlog := SQLERRM;
    
          rollback;
    
          INSERT INTO errorTable(error_no, errorUser, error_p, error_meg) VALUES(error_no.nextval, USER, p_name , p_errorlog);
          commit;

END;

/
