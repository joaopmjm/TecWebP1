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
	String ordered = (String) request.getAttribute("order");
	String filtered = (String) request.getAttribute("filter");
	System.out.println("ordenar " +ordered + "   filtrar " + filtered);
	List<Post> posts;
	List<User> users = dao.getUsers();
	if (ordered != null) {
		posts = dao.getPosts("ordenar", null);
	}else if (filtered != null){
		posts = dao.getPosts("filtrar", filtered);
		if (posts.isEmpty()){
			posts = dao.getPosts();
			out.println("<h6>Usuario filtrado inexistente</h6>");
		}
	}else{
		posts = dao.getPosts();
	}
	String iduser = String.valueOf(request.getAttribute("iduser"));
	User user = dao.getUser(iduser);
	//System.out.println("ID: "+ iduser);
%>
<h1>Usuário: <%=user.getNome() %></h1>
<form action="login.jsp"><input type="submit" value="Logout">Logout</input></form>

<table>
	<tr>
		<td>Ordenar</td><td>filtrar por usuario</td>
	</tr>
	<tr>
		<td>
			<form method="post" action="Ordenar">
				<input type="hidden" name="iduser" value=<%=iduser %>>
				<input type="submit" value="Ordenar por autor">
			</form>
		</td>
		<td>
			<form method="post" name="drop" action="Filtrar">
				<input type="hidden" name="iduser" value=<%=iduser %>>
				<input type="text" name="user_filtro">
				<input type="submit">
				</form>
		</td>
	</tr>
	
</table>
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