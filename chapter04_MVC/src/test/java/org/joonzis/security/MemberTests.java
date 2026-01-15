package org.joonzis.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
({
   "file:src/main/webapp/WEB-INF/spring/root-context.xml",
   "file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class MemberTests {
   @Autowired
   private DataSource ds;
   @Autowired
   private PasswordEncoder pwencoder;
   
//   @Test
//   public void testInsertMember() {
//      String sql = "insert into tbl_member(userid, userpw, username) values(?,?,?)";
//      
//      for(int i=0; i<100; i++) {
//         Connection con = null;
//         PreparedStatement ps =null;
//         
//         try {
//            con = ds.getConnection();
//            ps = con.prepareStatement(sql);
//            
//            // 비밀번호 인코딩
//            ps.setString(2, pwencoder.encode("pw" + i)); // pw의  i(index번호)
//            
//            // id& 이름
//            
//            if(i<80) {
//               ps.setString(1, "user" + i);
//               ps.setString(3, "일반사용자" + i);
//            }else if(i<90) {
//               ps.setString(1, "manager" + i);
//               ps.setString(3, "운영자" + i);
//            }else {
//               ps.setString(1, "admin" + i);
//               ps.setString(3, "관리자" + i);
//            }
//            ps.executeUpdate();
//         } catch (Exception e) {
//            e.printStackTrace();
//         }finally {
//            try {
//               if(ps !=null) ps.close();
//               if(con !=null) con.close();
//            } catch (Exception e2) {
//               e2.printStackTrace();
//            }
//         }
//      }
//   }
   @Test
   public void testInsertMemberAuth() {
	   //권한 테이블( tbl_member_auth )에 권한 추가
	   //user* 계정들 > "ROLE_USER"
	   //manager* 계정들> "ROLE_MEMBER"
	   //admin* 계정들> "ROLE_ADMIN"
	   String sql = "INSERT INTO tbl_member_auth VALUES(?,?)";
		for(int i = 0; i < 100; i++) {
			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = ds.getConnection();
				ps = con.prepareStatement(sql);
				
				//id & 권한
				if(i < 80) {
					ps.setString(1, "user"+i);
					ps.setString(2, "ROLE_USER");
				} else if(i < 90) {
					ps.setString(1, "manager"+i);
					ps.setString(2, "ROLE_MEMBER");					
				} else {
					ps.setString(1, "admin"+i);
					ps.setString(2, "ROLE_ADMIN");
				}
				
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ps != null) ps.close();
					if(con != null) con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					}
				}
			}
		}
   }
