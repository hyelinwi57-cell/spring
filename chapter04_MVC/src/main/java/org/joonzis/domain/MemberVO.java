package org.joonzis.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberVO {
	private String userId, userPw, userName;
	private Date regDate, updateDate;
	private boolean enabled;
	private List<AuthVO> authList;
}
