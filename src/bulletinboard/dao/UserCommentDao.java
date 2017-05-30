package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.UserComment;
import bulletinboard.exception.SQLRuntimeException;

public class UserCommentDao {

	public List <UserComment> getUserComment(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			sql.append("SELECT * FROM user_comment ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List <UserComment> ret =toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}

	private List <UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String body = rs.getString("body");
				int message_id = rs.getInt("message_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				int branch_id = rs.getInt("branch_id");

				UserComment comment = new UserComment();

				comment.setId(id);
				comment.setBody(body);
				comment.setName(name);
				comment.setMessage_id(message_id);
				comment.setInsertDate(insertDate);
				comment.setBranch_id(branch_id);
				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
