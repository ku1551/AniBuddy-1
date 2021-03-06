package web.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import web.dao.face.UserDao;
import web.dbutil.JDBCTemplate;
import web.dto.UserFile;
import web.dto.UserTB;

public class UserDaoImpl implements UserDao{

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	@Override
	public int selectCntUserByUseridUserpw(UserTB user) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE user_id = ?";
		sql += " and user_pw = ?";
		
		// 결과 저장 변수
		int cnt = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserPw());
			
			rs = ps.executeQuery();
			
			// 조회 결과 처리
			while(rs.next()) {
				cnt = rs.getInt(1);
//				cnt++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	
	@Override
	public UserTB selectUserByUserid(UserTB user) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql = "SELECT * FROM user_tb";
		sql += " WHERE user_id = ?";
		
		// 조회 결과를 저장할 객체
		UserTB result = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				result = new UserTB();
				result.setUserNo(rs.getInt("user_no"));
				result.setUserId(rs.getString("user_id"));
				result.setUserPw(rs.getString("user_pw"));
				result.setUserName(rs.getString("user_name"));
				result.setBirth(rs.getDate("birth"));
				result.setGender(rs.getString("gender").charAt(0));
				result.setNick(rs.getString("nick"));
				result.setEmail(rs.getString("email"));
				result.setTel(rs.getString("tel"));
				result.setFirstAddr(rs.getString("first_addr"));
				result.setSecondAddr(rs.getString("second_addr"));
				result.setAnimal(rs.getString("animal"));
				result.setIsExpert(rs.getString("is_expert"));
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	
	@Override
	public void insert(UserTB user) {
		
//		System.out.println("UserDaoImpl insert() user: "+ user);
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// 쿼리 작성
		String sql = "";
		sql += "INSERT INTO user_tb( user_no, user_id, user_pw, user_name"
				+ ", birth, gender, nick, email, tel"
				+ ", first_addr, second_addr, animal, is_expert)";
		sql += " VALUES( user_seq.nextval, ?, ?, ?"
				+ ", ?, ?, ?, ?, ?"
				+ ", ?, ?, ?, ?)";
		
		try {
			
			// DB 작업
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUserPw());
			ps.setString(3, user.getUserName());
			
			// 생일은 다르게 해주어야함!
			java.sql.Date sqlBirth = new java.sql.Date(user.getBirth().getTime());
			ps.setDate(4, sqlBirth);
			
			ps.setString(5, String.valueOf(user.getGender()));
			ps.setString(6, user.getNick());
			ps.setString(7, user.getEmail());
			ps.setString(8, user.getTel());
			ps.setString(9, user.getFirstAddr());
			ps.setString(10, user.getSecondAddr());
			ps.setString(11, user.getAnimal());
			ps.setString(12, String.valueOf(user.getIsExpert()));
			
			ps.executeUpdate();
			
//			System.out.println("UserDaoImpl - 회원가입 완료!!!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
	}
	
	@Override
	public UserTB selectUserByUserNick(UserTB user) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE nick = ?";
		
		// 결과를 전송할 객체
		UserTB result = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNick());
			
			rs = ps.executeQuery();

			while(rs.next()) {
				result = new UserTB();
				result.setUserId(rs.getString("user_id"));
				result.setUserPw(rs.getString("user_pw"));
				result.setUserName(rs.getString("user_name"));
				result.setNick(rs.getString("nick"));

			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	
	@Override
	public int selectUserByUserEmail(String userEmail) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE email = ?";
		
		// 결과 저장할 변수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userEmail);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cnt++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}

	@Override
	public UserTB selectUserByNameEmail(UserTB user) {
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();

		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE user_name = ?";
		sql += " AND email = ?";
		
		// 결과를 저장할 변수
		UserTB result = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getEmail());
			
			rs = ps.executeQuery();

			while(rs.next()) {
				result = new UserTB();
				result.setUserNo(rs.getInt("user_no"));
				result.setUserId(rs.getString("user_id"));
				result.setUserPw(rs.getString("user_pw"));
				result.setUserName(rs.getString("user_name"));
				result.setBirth(rs.getDate("birth"));
				result.setGender(rs.getString("gender").charAt(0));
				result.setNick(rs.getString("nick"));
				result.setEmail(rs.getString("email"));
				result.setTel(rs.getString("tel"));
				result.setFirstAddr(rs.getString("first_addr"));
				result.setSecondAddr(rs.getString("second_addr"));
				result.setAnimal(rs.getString("animal"));
				result.setIsExpert(rs.getString("is_expert"));
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		return result;
	}
	
	@Override
	public UserTB selectUserByUserIdEmailName(UserTB user) {
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();

		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE user_name = ?";
		sql += " AND email = ?";
		sql += " AND user_id = ?";
		
		// 결과를 저장할 변수
		UserTB result = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getUserId());

			rs = ps.executeQuery();

			while(rs.next()) {
				result = new UserTB();
				result.setUserNo(rs.getInt("user_no"));
				result.setUserId(rs.getString("user_id"));
				result.setUserPw(rs.getString("user_pw"));
				result.setUserName(rs.getString("user_name"));
				result.setBirth(rs.getDate("birth"));
				result.setGender(rs.getString("gender").charAt(0));
				result.setNick(rs.getString("nick"));
				result.setEmail(rs.getString("email"));
				result.setTel(rs.getString("tel"));
				result.setFirstAddr(rs.getString("first_addr"));
				result.setSecondAddr(rs.getString("second_addr"));
				result.setAnimal(rs.getString("animal"));
				result.setIsExpert(rs.getString("is_expert"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return result;
	}
	
	@Override
	public int updateUserPw(UserTB user) {
		
		// DB 연결
		conn = JDBCTemplate.getConnection();
		
		// Sql 작성
		String sql = "";
		sql += "UPDATE user_tb";
		sql += " SET";
		sql += " 	user_pw = ?";
		sql += " WHERE";
		sql += "     user_no = ?";
		
		// 결과 저장 변수
		int cnt = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserPw());
			ps.setInt(2, user.getUserNo());
			
			cnt = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		return cnt;
	}
	
	@Override
	public UserTB selectUserByUserno(UserTB user) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();

		// SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE user_no = ?";

		// 결과를 저장할 변수
		UserTB result = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUserNo());

			rs = ps.executeQuery();

			while(rs.next()) {
				result = new UserTB();
				result.setUserNo(rs.getInt("user_no"));
				result.setUserId(rs.getString("user_id"));
				result.setUserName(rs.getString("user_name"));
				result.setBirth(rs.getDate("birth"));
				result.setGender(rs.getString("gender").charAt(0));
				result.setNick(rs.getString("nick"));
				result.setEmail(rs.getString("email"));
				result.setTel(rs.getString("tel"));
				result.setFirstAddr(rs.getString("first_addr"));
				result.setSecondAddr(rs.getString("second_addr"));
				result.setAnimal(rs.getString("animal"));
				result.setIsExpert(rs.getString("is_expert"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}

		return result;
	}

	@Override
	public void update(UserTB user) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// 다음 게시글 번호 조회 쿼리
		String sql = "";
		sql += "UPDATE user_tb";
		sql += " SET";
		sql += "    user_name = ?";
		sql += "    , nick = ?";
		sql += "    , gender = ?";
		sql += "    , birth = ?";
		sql += "    , email = ?";
		sql += "    , first_addr = ?";
		sql += "    , second_addr = ?";
		sql += " WHERE user_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getNick());
			ps.setString(3, String.valueOf(user.getGender()));
			
			// 생일은 다르게 해주어야함!
			java.sql.Date sqlBirth = new java.sql.Date(user.getBirth().getTime());
			ps.setDate(4, sqlBirth);
			
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getFirstAddr());
			ps.setString(7, user.getSecondAddr());
			ps.setInt(8, user.getUserNo());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
	}

	@Override
	public void insertFile(UserFile userFile) {

		// DB 연결
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "INSERT INTO user_file ( fileno, user_no, origin_name, store_name, filesize)";
		sql += " VALUES (USERFILE_SEQ.nextval, ?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userFile.getUserNo());
			ps.setString(2, userFile.getOriginName());
			ps.setString(3, userFile.getStoreName());
			ps.setInt(4, userFile.getFilesize());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
	}
	
	
	@Override
	public UserFile selectUserFile(UserTB user) {

		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		//SQL 작성
		String sql = "";
		sql += "SELECT * FROM user_file";
		sql += " WHERE user_no = ?";
		
		// 결과 저장할 UserFile 객체
		UserFile userFile = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUserNo());
			
			rs = ps.executeQuery();
			
			// 조회 결과 처리
			while( rs.next() ) {
				userFile = new UserFile();
				
				userFile.setFileno(rs.getInt("fileno"));
				userFile.setUserNo(rs.getInt("user_no"));
				userFile.setOriginName(rs.getString("origin_name"));
				userFile.setStoreName(rs.getString("store_name"));
				userFile.setFilesize(rs.getInt("filesize"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(ps);
		}
		
		
		return userFile;
	}
	
	@Override
	public void updateUserTel(UserTB user) {
		// DB 연결
		conn = JDBCTemplate.getConnection();

		// Sql 작성
		String sql = "";
		sql += "UPDATE user_tb";
		sql += " SET";
		sql += "    tel = ?";
		sql += " WHERE";
		sql += "     user_no = ?";

		try {
			ps = conn.prepareStatement(sql);
			if(user.getTel().equals("null")) {
				ps.setString(1, "");
			} else {
				ps.setString(1, user.getTel());
			}
			ps.setInt(2, user.getUserNo());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}

	}
	
	@Override
	public void updateAnimal(UserTB user) {
		// DB 연결
		conn = JDBCTemplate.getConnection();

		// Sql 작성
		String sql = "";
		sql += "UPDATE user_tb";
		sql += " SET";
		sql += "   	animal = ?";
		sql += " WHERE";
		sql += "     user_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			if(user.getAnimal().equals("null")) {
				ps.setString(1, "");
			} else {
				ps.setString(1, user.getAnimal());
			}
			ps.setInt(2, user.getUserNo());

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(ps);
		}
	}
	
	@Override
	public int selectCntChkPw(UserTB deleteUser) {
		
		//DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// Sql 작성
		String sql = "";
		sql += "SELECT * FROM user_tb";
		sql += " WHERE";
		sql += "    user_no = ?";
		sql += "    and user_pw = ?";
		
		
		// 결과 반환 변수
		int result = -1;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteUser.getUserNo());
			ps.setString(2, deleteUser.getUserPw());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public void deleteUser(UserTB deleteUser) {
		
		// DB 연결 객체
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "DELETE user_tb";
		sql += " WHERE user_no = ?";
		
		// user 객체와 연결된 테이블에 있는 행들도 모두 삭제
		// 일단 user_file 테이블만 on cascade delete 연결 안됨...
		deleteUserFile(deleteUser);

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteUser.getUserNo());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void deleteUserFile(UserTB deleteUser) {

		// DB 연결
		conn = JDBCTemplate.getConnection();
		
		// SQL 작성
		String sql = "";
		sql += "DELETE user_file";
		sql += " WHERE user_no = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteUser.getUserNo());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
		
		
	}
	
}
