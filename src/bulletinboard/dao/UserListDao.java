package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.UserList;
import bulletinboard.exception.SQLRuntimeException;

public class UserListDao {

	public List <UserList> getUserList(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			sql.append("SELECT * FROM users ");


			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List <UserList> ret =toUserList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}

	private List <UserList> toUserList(ResultSet rs) throws SQLException {

		List<UserList> ret = new ArrayList<UserList>();
		try {
			while (rs.next()){
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id = rs.getInt("branch_id");
				int department_id = rs.getInt("department_id");
				int is_stopped = rs.getInt("is_stopped");
				UserList userList = new UserList();

				userList.setId(id);
				userList.setLogin_id(login_id);
				userList.setPassword(password);
				userList.setName(name);
				userList.setBranch_id(branch_id);
				userList.setDepartment_id(department_id);
				userList.setIs_stopped(is_stopped);

				ret.add(userList);
			}
			return ret;
		} finally {
			close(rs);
		}
	}




}