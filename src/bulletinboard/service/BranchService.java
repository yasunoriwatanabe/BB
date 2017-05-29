package bulletinboard.service;
import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinboard.beans.Branch;
import bulletinboard.dao.BranchDao;

public class BranchService {

	public List<Branch> getBranchList() {

		Connection connection = null;
		try {
			connection = getConnection();

			BranchDao branchDao = new BranchDao();
			List<Branch> branchName  = branchDao.getBranches(connection);

			commit(connection);

			return branchName;
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

}
