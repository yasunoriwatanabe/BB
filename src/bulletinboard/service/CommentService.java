package bulletinboard.service;


import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinboard.beans.Comments;
import bulletinboard.dao.CommentDao;

public class CommentService {
	public void register (Comments comment) {

		Connection connection = null;

		try {

			connection = getConnection();

			CommentDao commentDao = new CommentDao();

			commentDao.insert(connection, comment);
			System.out.println(comment);

			commit(connection);
		} catch (RuntimeException e) {
			rollback (connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close (connection);
		}
	}



}
