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

import bulletinboard.beans.Comments;
import bulletinboard.service.CommentService;



@WebServlet(urlPatterns = { "/newComment" })
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,HttpServletResponse response)
			throws IOException,ServletException {
				request.getRequestDispatcher("home.jsp").forward(request, response);
	}
	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		String bodySerch = request.getParameter("body");
		request.setAttribute("serchBody",bodySerch);
		if (isValid(request, messages) == true) {
			Comments comment = new Comments();


			if(request.getParameter("body").length()>500){

				return;
			}
			comment.setBody(request.getParameter("body"));
			comment.setMessage_id(request.getParameter("message_id"));
			comment.setUser_id(request.getParameter("user_id"));

			new CommentService().register(comment);

			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessage", messages);
			response.sendRedirect("./");
			return;
		}
	}
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String body = request.getParameter("body");
		System.out.println(request.getParameter("body")+"←テストだお");

		if (StringUtils.isEmpty(body) == true) {
			messages.add("メッセージを入力してください");
		}
		if (500 < body.length()) {
			messages.add("500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
