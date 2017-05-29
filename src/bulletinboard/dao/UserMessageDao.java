package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.UserMessage;
import bulletinboard.exception.SQLRuntimeException;

public class UserMessageDao {

	public List <UserMessage> getUserMessages(Connection connection, int num, String start , String end, String category) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			System.out.println("ユーザーメッセージだおげっとめっせーじすなう");
			System.out.println("SELECT * FROM user_messages テスト１ ");
			System.out.println("WHERE insert_date BETWEEN  '"+start+"' AND  '"+end+"'" + category );
			System.out.println(" ORDER BY insert_date DESC limit ");

			sql.append("SELECT * FROM user_messages  ");
			sql.append("WHERE insert_date BETWEEN  ? AND  ? ");
			if(category != "" && category != null){
				sql.append(" and category = ? ");
				sql.append(" GROUP BY category ");
			}
			sql.append("ORDER BY insert_date DESC limit " + num);
			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, start);
			ps.setString(2, end);
			if(category != "" && category != null){
				ps.setString(3, category);
			}
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			List <UserMessage> ret =toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}
	public List <UserMessage> getUserMessages(Connection connection) {

		PreparedStatement ps = null;
		System.out.println("userMessageだおのげっとゆーざーめっせーじす引数なし");
		try {
			StringBuilder sql = new StringBuilder ();


			sql.append("SELECT * FROM user_messages  ");

			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps.toString()+"PSだお");

			ResultSet rs = ps.executeQuery();
			List <UserMessage> ret =toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	private List <UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()){
				String title = rs.getString("title");
				String body = rs.getString("body");
				int id = rs.getInt("id");
				String category = rs.getString("category");
				int user_id = rs.getInt("user_id");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();
				message.setTitle(title);
				message.setBody(body);
				message.setId(id);
				message.setCategory(category);
				message.setUser_Id(user_id);
				message.setName(name);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
