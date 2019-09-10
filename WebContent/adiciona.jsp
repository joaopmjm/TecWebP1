<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Adicionar pergunta</title>
</head>
<body>
<%@ page import="br.edu.insper.Model.*" %>
<%
	DAO dao = new DAO();
	String idpergunta = request.getParameter("idpergunta");
	String idautor = request.getParameter("idautor");
	String iduser = request.getParameter("iduser");
	
	if (!idpergunta.equals("0")){
		if (idautor.equals(iduser)){
			Post pergunta = dao.getPost(Integer.parseInt(idpergunta));
			System.out.println(pergunta.getTexto());
			%>
			<form method="post" action="Modifica">
				<input type="text" name="perguntaModificada" value="<%=pergunta.getTexto() %>">
				<input type="hidden" name="idperguntaModificada" value=<%=pergunta.getId() %>>
				<input type="hidden" name="idautor" value=<%=iduser %>>
				<input type="submit" value="Modificar">
			</form>
		<% } %>
	<% }else{ %>
		<form method="post" action="Adicionar">
			<input type="text" name="pergunta">
			<input type="hidden" name="idautor" value=<%=iduser %>>
			<input type="submit" value="Adicionar">
		</form>
	<% }
	%>
</body>
</html>