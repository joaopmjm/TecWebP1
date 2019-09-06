<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Q n A - Home Page</title>
</head>
<body>
<%@ page import="java.util.*, br.edu.insper.Model.*" %>
<%	DAO dao = new DAO();
	List<Post> posts = dao.getPosts();
	String iduser = String.valueOf(request.getAttribute("iduser"));
	User user = dao.getUser(iduser);
	System.out.println("ID: "+ iduser);
%>
<h1>Usuário: <%=user.getNome() %></h1>
<table border="1">
	<tr><td>Pergunta</td><td>Autor</td><td>TimeStamp</td></tr>
	<% for (Post post : posts){ %>
		<tr>
			<td><%=post.getTexto() %></td>
			<td><%=post.getAutor().getNome() %></td>
			<td><%=post.getTime() %></td>
			<td><form method="post" action=adiciona.jsp>
				<input type="hidden" name="idautor"    value=<%=post.getAutor().getId()%>>
				<input type="hidden" name="idpergunta" value=<%=post.getId()%>>
				<input type="hidden" name="iduser"     value=<%=iduser %>>
				<%if (String.valueOf(post.getAutor().getId()).equals(iduser)){ %>
					<input type="submit" name="modify" value="Modificar">
				<% }else{ %>
					<input type="submit" name="modify" value="Modificar" disabled="disabled">				
				<% } %>
			</form></td>
			<td><form method="post" action="Deletar">
				<input type="hidden" name="idautor"    value=<%=post.getAutor().getId()%>>
				<input type="hidden" name="idpergunta" value=<%=post.getId()%>>
				<input type="hidden" name="iduser"     value=<%=iduser%>>
				<%if (String.valueOf(post.getAutor().getId()).equals(iduser)){ %>
					<input type="submit" name="delete" value="Deletar">
				<% }else{ %>
					<input type="submit" name="delete" value="Deletar" disabled="disabled">				
				<% } %>
			</form></td>
		</tr>
<% } %>
</table>
<form method="post" action="adiciona.jsp">
	<input type="hidden" name="idautor"    value=0>
	<input type="hidden" name="idpergunta" value=0>
	<input type="hidden" name="iduser" value=<%=iduser%>>
	<input type="submit" name="adicionar"  value="Adicionar">
</form>
</body>
</html>