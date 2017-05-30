package bulletinboard.service;

import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinboard.beans.User;
import bulletinboard.beans.UserComment;
import bulletinboard.beans.UserList;
import bulletinboard.beans.UserMessage;
import bulletinboard.dao.UserCommentDao;
import bulletinboard.dao.UserDao;
import bulletinboard.dao.UserListDao;
import bulletinboard.dao.UserMessageDao;
import bulletinboard.utils.CipherUtil;

public class UserService {

	public void register(User user) {
		Connection connection = null;
		try{
			connection = getConnection();
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);
			//setDefaultIcon(user);

			UserDao userDao = new UserDao();
			userDao.insert(connection,user);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}



	public User getUser(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, userId);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		}finally{
			close (connection);
		}
	}

	public User getUser(String loginId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, loginId);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		}finally{
			close (connection);
		}
	}






	public List<UserList> getUserList(){
		Connection connection = null;
		try {
			connection = getConnection();

			UserListDao userListDao = new UserListDao();
			List<UserList> ret = userListDao.getUserList(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e){
			rollback (connection);
			throw e;
		} catch (Error e) {
			rollback (connection);
			throw e;
 		} finally{
 			close (connection);
 		}

	}

	public void update (User user) {
		Connection connection = null;
		try {
			connection = getConnection();
			System.out.println("ok3");
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);
			System.out.println("ok3.1");
			UserDao userDao = new UserDao();
			userDao.update(connection,user);
			System.out.println("ok3.2");
			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void stopped (User user) {
		Connection connection = null;
		try {
			System.out.println("200");
			connection = getConnection();
			System.out.println("ok3.1");
			UserDao userDao = new UserDao();
			userDao.update(connection,user);
			System.out.println("ok3.2");
			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void delete (int deletedId) {
		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.delete(connection, deletedId);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void messageDelete (int messageId) {
		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.messageDelete(connection, messageId);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
	public void messageDeleteBranch (int messageId,int branchId) {
		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.messageDeleteBranch(connection, messageId,branchId);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void commentDelete (int commentId) {
		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.commentDelete(connection, commentId);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	private static final int LIMIT_NUM = 1000;

	public List<UserMessage> getMessage(String start, String end,String category){
		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret = userMessageDao.getUserMessages(connection, LIMIT_NUM, start, end,category);


			commit(connection);

			return ret;
		} catch (RuntimeException e){
			rollback (connection);
			throw e;
		} catch (Error e) {
			rollback (connection);
			throw e;
 		} finally{
 			close (connection);
 		}

	}

	public List<UserMessage> getMessage(){
		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret = userMessageDao.getUserMessages(connection);


			commit(connection);

			return ret;
		} catch (RuntimeException e){
			rollback (connection);
			throw e;
		} catch (Error e) {
			rollback (connection);
			throw e;
 		} finally{
 			close (connection);
 		}

	}
	public List<UserComment> getComment() {

		Connection connection  = null;

		try {
			connection = getConnection();

			UserCommentDao commentDao = new UserCommentDao();
			List<UserComment> ret = commentDao.getUserComment(connection, LIMIT_NUM);

			commit(connection);

			return ret;

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	/*private void setDefaultIcon(User user) {

		InputStream is = null;
		try {
			long randomNom = System.currentTimeMillis() %5;
			String filePath = "/duke_" + randomNom + ".jpg";
			is = UserService.class.getResourceAsStream(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamUtil.copy(is, baos);
			user.setIcon(baos.toByteArray());
		} finally {
			close(is);
		}
	}*/

}
