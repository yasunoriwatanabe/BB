package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletinboard.beans.Comments;
import bulletinboard.exception.SQLRuntimeException;

public class CommentDao {
	public void insert (Connection connection, Comments comments) {

		PreparedStatement ps =null;
		System.out.println("MD1");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments (");
			sql.append("body");
			sql.append(", message_id");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUE (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

		ps = connection.prepareStatement(sql.toString());
		System.out.println("MD13");
		ps.setString(1, comments.getBody());
		System.out.println("MD14");
		ps.setString(2, comments.getMessage_id());
		ps.setString(3, comments.getUser_id());
		System.out.println("MD15");
		ps.executeUpdate();
		System.out.println(ps);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close (ps);
		}
	}

}
