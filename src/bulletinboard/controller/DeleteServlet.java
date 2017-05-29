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

import bulletinboard.beans.User;
import bulletinboard.service.UserService;

@WebServlet(urlPatterns = { "/delete" })
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {

		List<String>messages = new ArrayList<String>();

		int deletedId = Integer.parseInt(request.getParameter("delete"));

		if (isValid(request,messages) == true) {
			new UserService().delete(deletedId);


		}

		request.setAttribute("errorMessage", messages);
		System.out.println("OK");


		/*if(loginCheck.getId()==deletedId){
			session.invalidate(); //セッションの無効化

			response.sendRedirect("login");*/


		response.sendRedirect("management");
	}


	private boolean isValid(HttpServletRequest request, List<String>messages) {


		HttpSession session = request.getSession();

		int deletedId = Integer.parseInt(request.getParameter("delete"));
		User loginCheck =  (User) session.getAttribute("loginUser");

		//ログインユーザーが自分を消そうとしたときにエラー表示して止める
		if(deletedId==loginCheck.getId()){
			messages.add("自分を削除ことはできません。他のユーザーで削除してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
