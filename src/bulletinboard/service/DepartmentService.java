package bulletinboard.service;
import static bulletinboard.utils.CloseableUtil.*;
import static bulletinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinboard.beans.Department;
import bulletinboard.dao.DepartmentDao;

public class DepartmentService {

	public List<Department> getDepartmentList() {

		Connection connection = null;
		try {
			connection = getConnection();

			DepartmentDao departmentDao = new DepartmentDao();
			List<Department> departmentName  = departmentDao.getDepartment(connection);

			commit(connection);

			return departmentName;
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
