package bulletinboard.service;

import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinboard.beans.Messages;
import bulletinboard.beans.UserMessage;
import bulletinboard.dao.MessageDao;
import bulletinboard.dao.UserMessageDao;

public class MessageService {
	public void register (Messages message) {
		System.out.println("メッセージサービスのレジスターなう");

		Connection connection = null;

		try {

			connection = getConnection();

			MessageDao messageDao = new MessageDao();

			messageDao.insert(connection, message);

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

	private static final int LIMIT_NUM = 1000;

	public static List<UserMessage> getMessage(String start,String end,String category) {

		Connection connection  = null;
		System.out.println("メッセージサービスのゲットメッセージなう");

		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection, LIMIT_NUM, start, end,category);

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
	public static List<UserMessage> getMessage() {

		Connection connection  = null;
		System.out.println("メッセージサービスのゲットメッセージなう引数なし");

		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection);

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
	public void day (String start, String end,String category){
		Connection connection = null;
		System.out.println("メッセージサービスのdayなう");
		try {
			System.out.println(start+end+"スタートエンド");


			connection = getConnection();
			UserMessageDao userMessageDao = new UserMessageDao();
			userMessageDao.getUserMessages(connection,LIMIT_NUM ,start,end,category);

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

}
