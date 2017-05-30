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
import bulletinboard.beans.UserList;
import bulletinboard.exception.NoRowsUpdatedRuntimeException;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;

@WebServlet(urlPatterns = { "/settings" })

public class SettingServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response )
	throws ServletException, IOException {

		List<Branch> branches = new BranchService().getBranchList();
		List<Department> departments = new DepartmentService().getDepartmentList();
		List<UserList> user = new UserService().getUserList();
		request.setAttribute("branches",branches);
		request.setAttribute("departments",departments);

		HttpSession session = request.getSession();


		System.out.println(request.getParameter("id")+"←id");

		//idがデータ内になければエラーを出して戻す

		if(request.getParameter("id").matches("\\D+")){

			List<String> errorMessage =  new ArrayList<String>();
			errorMessage.add("存在しないアカウントです");
			session.setAttribute("errorMessage", errorMessage);

			request.setAttribute("userList", user);

			request.getRequestDispatcher("management.jsp").forward(request, response);
			return ;
		}


		int userId = Integer.parseInt(request.getParameter("id"));
		User editUser = new UserService().getUser(userId);
		if(editUser==null){
			List<String> errorMessage =  new ArrayList<String>();
			errorMessage.add("存在しないアカウントです");
			session.setAttribute("errorMessage", errorMessage);

			request.setAttribute("userList", user);

			response.sendRedirect("management");
			return ;

		}
		request.setAttribute("userList", editUser);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,IOException {

		List<String>messages = new ArrayList<String>();

		HttpSession session = request.getSession();


		request.setAttribute("id", request.getParameter("id"));


		User editUser = getEditUser(request,response);
		List<Branch> branches = new BranchService().getBranchList();
		List<Department> departments = new DepartmentService().getDepartmentList();
		//値保持
				String nameSerch = request.getParameter("name");
				String loginSerch = request.getParameter("login_id");
				int branchSerch = Integer.parseInt(request.getParameter("branch_id"));
				int departmentSerch = Integer.parseInt(request.getParameter("department_id"));
				//String branches = request.getParameter("branches");


				request.setAttribute("serchName",nameSerch);
				request.setAttribute("serchLogin",loginSerch);
				request.setAttribute("serchDepartment",departmentSerch);
				request.setAttribute("serchBranch",branchSerch);
				request.setAttribute("branches", branches);
				request.setAttribute("departments", departments);



		session.setAttribute("editUser", editUser);

		if (isValid(request,messages) == true) {

			try {

				new UserService().update(editUser);

			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessage", messages);
				System.out.println("ddd");
				response.sendRedirect("settings");
				return;
			}

			session.setAttribute("edit", editUser);
			session.removeAttribute("editUser");
			System.out.println("OK2");
			request.setAttribute("branches",branches);
			request.setAttribute("departments",departments);
			request.setAttribute("userList", editUser);
			//request.getRequestDispatcher("settings.jsp").forward(request, response);
			response.sendRedirect("management");
			return;
		} else {
			System.out.println("no2");
			request.setAttribute("userList", editUser);
			request.setAttribute("branches",branches);
			request.setAttribute("departments",departments);
			request.setAttribute("errorMessage", messages);
			System.out.println("ssss");
			request.getRequestDispatcher("settings.jsp").forward(request, response);
			return;

		}

	}
	private User getEditUser(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException {


		List<String> messages = new ArrayList<String>();


		int userId = Integer.parseInt(request.getParameter("id"));
		User editUser = new UserService().getUser(userId);

		if (isValid (request, messages) == true) {
			editUser.setName(request.getParameter("name"));
			editUser.setLogin_id(request.getParameter("login_id"));
			if(StringUtils.isEmpty(request.getParameter("password")) == false){
				editUser.setPassword(request.getParameter("password"));
			}

			editUser.setBranch_id(request.getParameter("branch_id"));
			editUser.setDepartment_id(request.getParameter("department_id"));

		}
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String>messages) {

		String name = request.getParameter("name");
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		int b = Integer.parseInt(request.getParameter("branch_id"));
		int d = Integer.parseInt(request.getParameter("department_id"));
		int nameSize =(request.getParameter("name").length());

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
		}

		//パスワードの処理
		if(password.isEmpty()==false){
			if (! password.matches("^[a-zA-Z0-9 -/:-@]{6,255}+$")){
				messages.add("パスワードが不正です。使える文字は記号を含む半角文字でかつ６桁以上です。");
			}else if (! password.equals(password2)){
				messages.add("パスワードが一致しません。");
			}
		}

		//支店と部署の組み合わせ確認
		if(b==1 && d>2){
			System.out.println("店と所属の組み合わせおかしい");

			messages.add("支店と部署が不正です。");

		}else if(b>=2&&d<3){
			messages.add("支店と部署が不正です。");

		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
