package bulletinboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.Branch;
import bulletinboard.beans.Department;
import bulletinboard.beans.User;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;

@WebServlet (urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {

		List<Branch> branches = new BranchService().getBranchList();
		List<Department> departments = new DepartmentService().getDepartmentList();

		request.setAttribute("branches",branches);
		request.setAttribute("departments",departments);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		//値保持
		String nameSerch = request.getParameter("name");
		String loginSerch = request.getParameter("login_id");
		String passwordSerch = request.getParameter("password");
		String branchSerch = request.getParameter("branch_id");
		String departmentSerch = request.getParameter("department_id");
		request.setAttribute("serchName",nameSerch);
		request.setAttribute("serchLogin",loginSerch);
		request.setAttribute("serchPassword",passwordSerch);
		request.setAttribute("serchDepartment",departmentSerch);
		request.setAttribute("serchBranch",branchSerch);


		List<Branch> branches = new BranchService().getBranchList();
		List<Department> departments = new DepartmentService().getDepartmentList();
		request.setAttribute("branches",branches);
		request.setAttribute("departments",departments);

		if (isValid (request, messages) == true) {

			User user = new User();

			user.setName(request.getParameter("name"));
			user.setLogin_id(request.getParameter("login_id"));
			user.setPassword(request.getParameter("password"));
			user.setBranch_id(request.getParameter("branch_id"));
			user.setDepartment_id(request.getParameter("department_id"));

			new UserService().register(user);

			response.sendRedirect("management");

		} else {
			session.setAttribute("errorMessage", messages);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid (HttpServletRequest request, List<String>messages){

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		int b = Integer.parseInt(request.getParameter("branch_id"));
		int d = Integer.parseInt(request.getParameter("department_id"));
		int nameSize =(request.getParameter("name").length());
		User loginUserId = new UserService().getUser(login_id);


		//名前の処理
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}else if(nameSize > 11){
			messages.add("名前は１０文字以下です。");

		}
		//ログインIDの処理
		if (StringUtils.isEmpty(login_id) == true) {
			messages.add("ログインIDを入力してください");

		}else if(! login_id.matches("[0-9a-zA-Z]{6,20}")){
			messages.add("ログインIDが不正です。使える文字は英数字でかつ６文字以上２０文字以下です。");
		}else if(loginUserId!=null){
			messages.add("このログインIDは使用できません。");
		}

		//パスワードの処理
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}else if (! password.matches("^[a-zA-Z0-9 -/:-@]{6,255}+$")){
			messages.add("パスワードが不正です。使える文字は記号を含む半角文字でかつ６桁以上です。");
		}else if (! password.equals(password2)){
			messages.add("パスワードが一致しません。");
		}

		//支店と部署の組み合わせ確認
		if(b==1 && d>2){
			System.out.println("店と所属の組み合わせおかしい");

			messages.add("支店と部署が不正です。");

		}else if(b>=2&&d<3){
			messages.add("支店と部署が不正です。");

		}

		//TODO　アカウントがすでに利用されていないか、メールアドレスがすでに登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
