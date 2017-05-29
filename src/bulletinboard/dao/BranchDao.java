package bulletinboard.dao;

import static bulletinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinboard.beans.Branch;
import bulletinboard.exception.SQLRuntimeException;

public class BranchDao {

	public List <Branch> toBranchList (ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {

			while (rs.next()) {
				int id =rs.getInt("id");
				String name = rs.getString("name");

				Branch user = new Branch();
				user.setId(id);
				user.setName(name);
				ret.add(user);

			}
			return ret;
		} finally {
			close(rs);
		}
	}
	public List <Branch> getBranches(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder ();
			sql.append("SELECT * FROM branches ");


			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List <Branch> ret =toBranchList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}
}
