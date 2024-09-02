package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
		try {
			TodoListService service = new TodoListServiceImpl();
			
			// 서비스 호출 후 결과 반환 받기
			int result = service.todoDelete(todoNo);
			
			HttpSession session = req.getSession();
			
			String message = null;
			if(result > 0) 	message = "할 일이 삭제 되었습니다";
			else			message = "todo가 존재하지 않습니다";
			
			session.setAttribute("message", message);
			
			resp.sendRedirect("/");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	
	
	}
}