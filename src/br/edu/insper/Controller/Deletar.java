package br.edu.insper.Controller;

import java.io.IOException;
import br.edu.insper.Model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Deletar
 */
@WebServlet("/Deletar")
public class Deletar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deletar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		int idpergunta = Integer.parseInt(request.getParameter("idpergunta"));
		String idautor = (String) request.getParameter("idautor");
		String iduser = (String) request.getParameter("iduser");
		try {
			if (idautor.equals(iduser)) {
				Post post = new Post();
				DAO dao = new DAO();
				System.out.println("Deletando");
				post.setId(idpergunta);
				dao.deletarPergunta(post);
			}
		}catch (Exception e) {
			e.fillInStackTrace();
		}
		request.setAttribute("iduser", iduser);
		RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
		rd.forward(request, response);
	}
}
