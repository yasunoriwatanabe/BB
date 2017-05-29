package bulletinboard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.service.UserService;

@WebServlet(urlPatterns = { "/commentDelete" })
public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {


		int commentId = Integer.parseInt(request.getParameter("commentDelete"));

			new UserService().commentDelete(commentId);
			System.out.println("OK");


		response.sendRedirect("./");

	}

}