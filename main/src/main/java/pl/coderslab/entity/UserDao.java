package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    private static final String READ_USER_QUERY = "SELECT * FROM users where id = ?";

    private static final String UPDATE_USER_QUERY = "UPDATE users SET username =?, email =?, password =? where id =?";

    private static final String DELETE_USER_QUERY = "DELETE FROM users where id = ?";

    private static final String FIND_ALL_USER_QUERY = "SELECT * FROM users";


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_USER_QUERY);) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("ID: " + id + ", email: " + email + ", username: " + username + ", password: " + password);
            } else {
                System.out.println("Brak w bazie użytkownika o wskazanym ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } return null;
    }
    public void update(User user) {
        try(Connection conn = DbUtil.getConnection()){
        PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,hashPassword(user.getPassword()));
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

        public void delete(int userId){
        try(Connection conn = DbUtil.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_QUERY);){
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
            } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    public User[] findAll(){
        try (Connection conn = DbUtil.getConnection();){
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_USER_QUERY);
            ResultSet rs = preparedStatement.executeQuery();
            User [] users = new User[0];
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            } return users;
        }catch (SQLException exception){
            exception.printStackTrace();
        } return null;

    }
}
