package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.User;
import bulletinboard.exception.NoRowsUpdatedRuntimeException;
import bulletinboard.exception.SQLRuntimeException;

public class UserDao {

	public void insert (Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users (");
			//sql.append("id");
			sql.append("login_id ");
			sql.append(", password ");
			sql.append(", name ");
			sql.append(", branch_id ");
			sql.append(", department_id");
			//sql.append(",icon");
			sql.append(", is_stopped");
			sql.append(") VALUES (");
			//sql.append("NEXT VALUE FOR my_seq "); //id
			sql.append(" ?"); //login_id
			sql.append(", ?"); //password
			sql.append(", ?"); //name
			sql.append(", ?"); //branch_id
			sql.append(", ?"); //department_id
			sql.append(", ?");//is_stopped
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranch_id());
			ps.setString(5, user.getDepartment_id());
			ps.setInt(6, 0);


			ps.executeUpdate();
		}catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}


	public User getUser (Connection connection, String login_id, String password) {

		PreparedStatement ps = null;

		try {
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement (sql);

			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List <User> userList = toUserList (rs);
			if (userList.isEmpty() == true) {

				return null;
			} else if (2 <= userList.size()) {

				throw new IllegalStateException("2 <= userList.size()");
			} else {

				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException (e);
		} finally {
			close(ps);
		}
	}


	/*public List<User> getBranch (Connection connection, int id) {

		PreparedStatement ps = null;

		try {
			String sql = "SELECT * FROM branch_user WHERE id = ? ";

			ps = connection.prepareStatement (sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List <User> userList = toUserList (rs);
			if (userList.isEmpty() == true) {

				return null;
			} else if (2 <= userList.size()) {

				throw new IllegalStateException("2 <= userList.size()");
			} else {

				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException (e);
		} finally {
			close(ps);
		}
	}*/

	public List <User> toUserList (ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {

			while (rs.next()) {
				int id =rs.getInt("id");
				String login_id = rs.getString("login_id");

				String password = rs.getString("password");

				String name = rs.getString("name");

				String branch_id = rs.getString("branch_id");

				String department_id = rs.getString("department_id");

				int is_stopped = rs.getInt("is_stopped");

				User user = new User();
				user.setId(id);
				user.setLogin_id(login_id);
				user.setPassword(password);
				user.setName(name);
				user.setBranch_id(branch_id);
				user.setDepartment_id(department_id);
				user.setIs_stopped(is_stopped);
				ret.add(user);

			}
			return ret;
		} finally {
			close(rs);
		}
	}
	/*public List <User> toBranchList (ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {

			while (rs.next()) {
				int id =rs.getInt("id");
				String name = rs.getString("name");

				User user = new User();
				user.setId(id);
				user.setLogin_id(name);
				ret.add(user);

			}
			return ret;
		} finally {
			close(rs);
		}
	}*/


	public User getUser(Connection connection, int id){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs =ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection, String loginId){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);

			ResultSet rs =ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void update(Connection connection, User user) {

		PreparedStatement ps = null;
		System.out.println("ok4");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", is_stopped = ?");
			sql.append(" WHERE");
			sql.append(" id = ?;");


			ps = connection.prepareStatement(sql.toString());
			System.out.println("ok4.1");
			ps.setString(1, user.getLogin_id());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			System.out.println("4.2");
			ps.setString(4, user.getBranch_id());
			ps.setString(5, user.getDepartment_id());

			ps.setInt(6, user.getIs_stopped());
			ps.setInt(7, user.getId());
			System.out.println("ok4.25");


			System.out.println(ps);


			int count = ps.executeUpdate();
			System.out.println("ok4.4");
			if (count == 0) {
				System.out.println("no99");
				throw new NoRowsUpdatedRuntimeException();
			}
			System.out.println("ok4.5");

		} catch (SQLException e) {
			System.out.println(e);
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	public void stopped(Connection connection, User user) {

		PreparedStatement ps = null;
		System.out.println("ok4");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_stopped = ?");
			sql.append(" WHERE");
			sql.append(" id = ?;");


			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, user.getIs_stopped());
			ps.setInt(2, user.getId());
			System.out.println("ok4.25");


			System.out.println(ps);


			int count = ps.executeUpdate();
			System.out.println("ok4.4");
			if (count == 0) {
				System.out.println("no99");
				throw new NoRowsUpdatedRuntimeException();
			}
			System.out.println("ok4.5");

		} catch (SQLException e) {
			System.out.println(e);
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}


	public void delete(Connection connection,int deletedId) {

		PreparedStatement ps = null;
		System.out.println("ok4");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM users ");
			sql.append(" WHERE");
			sql.append(" id = ?;");


			System.out.println(deletedId+"ok4.25");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1,  deletedId);



			System.out.println(ps);


			int count = ps.executeUpdate();
			System.out.println("ok4.4");
			if (count == 0) {
				System.out.println("no99");
				throw new NoRowsUpdatedRuntimeException();
			}
			System.out.println("ok4.5");

		} catch (SQLException e) {
			System.out.println(e);
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	public void messageDelete(Connection connection,int messageId) {

		PreparedStatement ps = null;
		System.out.println("ok4");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM messages");
			sql.append(" WHERE");
			sql.append(" id = ?;");


			System.out.println(messageId+"ok4.25");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1,  messageId);

			System.out.println(ps);

			int count = ps.executeUpdate();
			System.out.println("ok4.4");
			if (count == 0) {
				System.out.println("no99");
				throw new NoRowsUpdatedRuntimeException();
			}
			System.out.println("ok4.5");


		} catch (SQLException e) {
			System.out.println(e);
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}

	public void commentDelete(Connection connection,int commentId) {

		PreparedStatement ps = null;
		System.out.println("ok4");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments ");
			sql.append(" WHERE");
			sql.append(" id = ?;");


			System.out.println(commentId+"ok4.25");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1,  commentId);



			System.out.println(ps);


			int count = ps.executeUpdate();
			System.out.println("ok4.4");
			if (count == 0) {
				System.out.println("no99");
				throw new NoRowsUpdatedRuntimeException();
			}
			System.out.println("ok4.5");

		} catch (SQLException e) {
			System.out.println(e);
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}


}