package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletinboard.beans.Messages;
import bulletinboard.exception.SQLRuntimeException;

public class MessageDao {

	public void insert (Connection connection, Messages messages) {

		PreparedStatement ps =null;
		System.out.println("MD1");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages (");
			sql.append("title");
			sql.append(", body");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", user_id");
			sql.append(") VALUE (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", ?");
			sql.append(")");

		ps = connection.prepareStatement(sql.toString());

		ps.setString(1, messages.getTitle());

		ps.setString(2, messages.getBody());

		ps.setString(3, messages.getCategory());
		ps.setInt(4, messages.getUser_id());
		ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close (ps);
		}
	}
}
