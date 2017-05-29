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

import bulletinboard.beans.Branch;
import bulletinboard.beans.Department;
import bulletinboard.beans.User;
import bulletinboard.beans.UserList;
import bulletinboard.service.BranchService;
import bulletinboard.service.DepartmentService;
import bulletinboard.service.UserService;

@WebServlet(urlPatterns = { "/management" })

public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException{
		System.out.println("a");

		HttpSession session = request.getSession();

		User loginUser =  (User) session.getAttribute("loginUser");


		String department_id = loginUser.getDepartment_id();


		int i = Integer.parseInt(department_id);
		if(i!=1){
			System.out.println("権限がない");
			response.sendRedirect("./");
			return;

		}else{
			System.out.println("権限があるので管理画面を開く");
		}

		List<UserList> userList = new UserService().getUserList();
		List<Branch> branches = new BranchService().getBranchList();

		List<Department> departments = new DepartmentService().getDepartmentList();
		request.setAttribute("userList", userList);
		request.setAttribute("branches",branches);
		request.setAttribute("departments",departments);

		System.out.println(branches.get(1).getName());
		System.out.println(branches.get(1).getId());

		request.getRequestDispatcher("management.jsp").forward(request, response);

	}
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		List<UserList> userList = new UserService().getUserList();
		List<Branch> branches = new BranchService().getBranchList();

		List<Department> departments = new DepartmentService().getDepartmentList();
		System.out.println("100");

		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("stopped", request.getParameter("stopped"));

		System.out.println("101");

		User editUser = getEditUser(request,response);


		//ログインユーザーが自分を停止させようとしたときに止める
		if (isValid (request, messages) == true) {
			session.setAttribute("editUser", editUser);
			new UserService().stopped(editUser);
			response.sendRedirect("management");
			return;


		}else{
			request.setAttribute("errorMessage", messages);
			System.out.println(request.getAttribute("errorMessage"));
			//response.sendRedirect("management");
			request.setAttribute("userList", userList);
			request.setAttribute("branches",branches);
			request.setAttribute("departments",departments);
			request.getRequestDispatcher("management.jsp").forward(request,response);
			return;

		}



		/*停止処理をしたときにログインユーザーが停止担った場合
		if(editUser.getIs_stopped()==1){

			User loginCheck =  (User) session.getAttribute("loginUser");

				if(loginCheck.getId()==editUser.getId()){
					session.invalidate(); //セッションの無効化

					response.sendRedirect("login");
					return ;
				}

		}*/


	}
	private boolean isValid(HttpServletRequest request, List<String>messages) {

		int stopUserId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		System.out.println(loginUser.getId()+"loginUser.getId()");
		System.out.println(stopUserId+"stopUserId");
		if(loginUser.getId()==stopUserId){
			System.out.println("ttttt");
			messages.add("自分を停止させることはできません。他のユーザーで停止してください。");


		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private User getEditUser(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException {

		int userId = Integer.parseInt(request.getParameter("id"));
		User editUser = new UserService().getUser(userId);
		editUser.setIs_stopped(Integer.parseInt(request.getParameter("stopped")));


		//editUser.setIs_stopped(request.getParameter("is_stopped"));
		return editUser;
	}




}
