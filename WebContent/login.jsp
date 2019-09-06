<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>

<html>

<style>
	#btlogin {
		
	}
</style>

<head>
<meta charset="utf-8">
<title>Palestra Q n A - Login</title>
</head>
<body>
	<table style="margin-left: auto; margin-right: auto; width: 8em">
		<tr>
			<td>
				<form method="post" action="Login" style="padding-right: 10em">
					Nome<br/>
					<input type="text" name="nomeLogin" placeholder="Nome Ex. João"><br/>
					Senha<br/>
					<input type="password" name="senhaLogin" placeholder="Senha"><br/>
					<div >
						<input type="submit" name="login" value="login" id="btlogin">
					</div>
				</form>
			</td>
			<td>
				<form method="post" action="Cadastro">
					Nome<br/>
					<input type="text" name="nomeCadastro" placeholder="Seu nome"><br/>
					Senha<br/>
					<input type="password" name="senhaCadastro" placeholder="Senha"><br/>
					Confirmar Senha<br/>
					<% if (request.getParameter("sc") == "falso"){
						out.println("<p><b>Senhas diferentes</b></p>");
					}
					%>
					<input type="password" name="senhaConfirmaCadastro">
					<input type='submit' value='Cadastrar-se'>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>