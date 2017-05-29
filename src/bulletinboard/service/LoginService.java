package bulletinboard.service;

import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinboard.beans.User;
import bulletinboard.dao.UserDao;
import bulletinboard.utils.CipherUtil;

public class LoginService {

	public User login (String login_id,String password){
		System.out.println("c");
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, login_id, encPassword);

			commit(connection);
			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
