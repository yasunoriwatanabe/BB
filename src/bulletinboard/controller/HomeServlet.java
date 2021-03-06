package bulletinboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinboard.beans.UserComment;
import bulletinboard.beans.UserList;
import bulletinboard.beans.UserMessage;
import bulletinboard.service.MessageService;
import bulletinboard.service.UserService;


@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request ,HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("～～～～～～ここから～～～～～～");
		System.out.println("ホームサーブレットdoGetなう");
		List<UserList> user = new UserService().getUserList();
		//List<UserMessage> message = new UserService().getMessage();
		request.setAttribute("userList", user);
		//request.setAttribute("messageList", message);

		String start = request.getParameter("start_day");
		if(start == null){
			start = ("2017-05-01");
		}else if(start.isEmpty()){
			start = ("2017-05-01");
		}
		String end  = request.getParameter("end_day");
		if(end == null){
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


			end = sdf.format(cal.getTime());
			end +=  (" 23:59:59");
		}else if(end.isEmpty()){
			 Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			end = sdf.format(cal.getTime());
			end +=  (" 23:59:59");
		}
		//String category = request.getParameter("category");

		String category = request.getParameter("category");


		new MessageService().day(start,end,category);


		System.out.println(end+"←end");
		System.out.println(start+"←start");
		System.out.println(category+"←category");
		new MessageService();
		List<UserMessage> messagesCategory = MessageService.getCategory();
		request.setAttribute("categoryList", messagesCategory);

		List<UserMessage> messages = new UserService().getMessage(start,end,category);
		request.setAttribute("messages", messages);
		List<UserComment> comments = new UserService().getComment();
		request.setAttribute("comments", comments);


        new MessageService().day(start,end,category);
        System.out.println("～～～～～～ここまで～～～～～～～");
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}
	@Override
	protected  void doPost (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {
		System.out.println("ホームサーブレットdoPostなう");

        String start = request.getParameter("start_day");
        String end   = request.getParameter("end_day");
        String category = request.getParameter("category");
        new MessageService().day(start,end,category);
        request.getRequestDispatcher("/home.jsp").forward(request, response);

    }
}