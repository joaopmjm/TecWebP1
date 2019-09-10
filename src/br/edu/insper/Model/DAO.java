package br.edu.insper.Model;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DAO {
	private Connection connection = null;

	public DAO() {
		// TODO Auto-generated catch block
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/groot", "joaopmjm", "220197");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<User> getUsers() throws SQLException{
		List<User> users = new ArrayList<User>();
		
		PreparedStatement stmt;
		stmt = connection.prepareStatement("SELECT * FROM user");
		ResultSet rs = stmt.executeQuery();
		try {
			User user = new User();
			user.setNome(rs.getString("nome"));
			user.setSenha(rs.getString("senha"));
			users.add(user);
			rs.close();
			stmt.close();
		}catch (Exception e) {
			e.fillInStackTrace();
		}
		return users;
	}
	public List<Post> getPosts() throws SQLException{
		List<Post> posts = new ArrayList<Post>();
		
		PreparedStatement stmt;
		stmt = connection.prepareStatement("SELECT user.nome, pergunta.texto, pergunta.id, pergunta.idautor FROM pergunta, user WHERE user.id = pergunta.idautor");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Post post = new Post();
				User user = new User(); 
				post.setId(rs.getInt("id"));
				post.setTexto(rs.getString("texto"));
				user.setNome(rs.getString("nome"));
				user.setId(rs.getInt("idautor"));
				post.setAutor(user);
				posts.add(post);
			}
		}catch (Exception e) {
			e.fillInStackTrace();
		}
		rs.close();	
		stmt.close();
		return posts;
	}
	public List<Post> getPosts(String i,String filtro) throws SQLException{
		List<Post> posts = new ArrayList<Post>();
		
		PreparedStatement stmt;
		String sql = "";
		if (i == "ordenar") {
			sql = "SELECT user.nome, pergunta.texto, pergunta.id, pergunta.idautor FROM pergunta, user WHERE user.id = pergunta.idautor ORDER BY user.id";
		}else if(i == "filtrar") {
			sql = "SELECT user.nome, pergunta.texto, pergunta.id, pergunta.idautor FROM pergunta, user WHERE user.id = pergunta.idautor AND user.nome='" + filtro + "'";
			System.out.println(sql);
		}
		stmt = stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Post post = new Post();
				User user = new User(); 
				post.setId(rs.getInt("id"));
				post.setTexto(rs.getString("texto"));
				user.setNome(rs.getString("nome"));
				user.setId(rs.getInt("idautor"));
				post.setAutor(user);
				posts.add(post);
			}
		}catch (Exception e) {
			e.fillInStackTrace();
		}
		rs.close();	
		stmt.close();
		return posts;
	}
	
	public Post getPost(int id) throws SQLException {
		Post pergunta = new Post();
		String sql = "SELECT texto, id FROM pergunta WHERE id=?";
		PreparedStatement stmt;
		stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		try {
			while(rs.next()) {
				User user = new User();
				pergunta.setTexto(rs.getString("texto"));
				pergunta.setId(rs.getInt("id"));
				user.setId(rs.getInt("idautor"));
				pergunta.setAutor(user);
			}
		}catch (Exception e){
			e.fillInStackTrace();
		}
		return pergunta;
	}
	public User getUser(String id) throws SQLException {
		User usuario = new User();
		String sql = "SELECT nome, id FROM user WHERE id=?";
		PreparedStatement stmt;
		stmt = connection.prepareStatement(sql);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		try {
			while(rs.next()) {
				System.out.println(rs.getString("nome"));
				usuario.setNome(rs.getString("nome"));
				usuario.setId(rs.getInt("id"));
			}
		}catch (Exception e){
			e.fillInStackTrace();
		}
		return usuario;
	}
	public User getUser(String nome, String senha) throws SQLException {
		User usuario = new User();
		String sql = "SELECT * FROM user WHERE nome=? AND senha=?";
		PreparedStatement stmt;
		stmt = connection.prepareStatement(sql);
		stmt.setString(1, nome);
		stmt.setString(2, senha);
		ResultSet rs = stmt.executeQuery();
		try {
			while(rs.next()) {
				usuario.setNome(rs.getString("nome"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setId(rs.getInt("id"));
			}
		}catch (Exception e){
			e.fillInStackTrace();
		}
		return usuario;
	}
	
	public void adicionaPost(Post post) {
		String sql = "INSERT INTO pergunta " + "(texto, idautor) values(?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, post.getTexto());
			stmt.setInt(2, post.getAutor().getId());
			stmt.execute();
			stmt.close();
		}catch (Exception e) {
			e.fillInStackTrace();
		}
	}
	public void adicionaUsuario(User user) {
		String sql = "INSERT INTO user " + "(nome, senha) values(?,?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getNome());
			stmt.setString(2, user.getSenha());
			stmt.execute();
			stmt.close();
			
		}catch (Exception e) {
			e.fillInStackTrace();
		}
	}
	public boolean checkUser(User user) throws SQLException {
		boolean exist = false;
		PreparedStatement stmt;
		stmt = connection.prepareStatement("SELECT * FROM user WHERE nome=? AND  senha=?");
		stmt.setString(1, user.getNome());
		stmt.setString(2, user.getSenha());
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next()) {
				exist = true;
			}
		}catch (Exception e) {
			e.fillInStackTrace();
		}
		return exist;
	}
	
	public void alterarPergunta(Post post, String novo) throws SQLException {
		PreparedStatement stmt;
		stmt = connection.prepareStatement("UPDATE pergunta SET texto=? WHERE id=?");
		stmt.setString(1, novo);
		stmt.setInt(2, post.getId());
		stmt.execute();
		stmt.close();
	}
	
	public void deletarPergunta(Post post) throws SQLException {
		PreparedStatement stmt;
		stmt = connection.prepareStatement("DELETE FROM pergunta WHERE id=?");
		stmt.setInt(1, post.getId());
		stmt.execute();
		stmt.close();
	}
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
