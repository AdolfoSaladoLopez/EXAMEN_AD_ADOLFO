package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Usuario;

public class UsuarioDAO {

    private Connection connection;

    /* Completar consultas */
    static final String INSERT_QUERY = "INSERT INTO usuario(id, nombre, apellidos, dni) VALUES(NULL, ?, ?, ?)";
    static final String LIST_QUERY = "SELECT * FROM usuario";
    static final String GET_BY_DNI = "SELECT * FROM usuario WHERE dni = ?";

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/examen", "root", "12345");

            if (connection != null) {
                System.out.println("Base de datos conectada con éxito");
            } else {
                System.out.println("Base de datos no conectada");

            }

        } catch (Exception ex) {
            System.out.println("Error al conectar a la base de datos");
            System.out.println("ex");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error al cerrar la base de datos");
        }
    }

    public void save(Usuario user) {

        /**
         * Completar código para guardar un usuario
         * Este método no debe generar el id del usuario, ya que la base
         * de datos es la que lo genera.
         * Una vez obtenido el id del usuario tras la consulta sql,
         * hay que modificarlo en el objeto que se pasa como parámetro
         * de forma que pase a tener el id correcto.
         */

        String nombre = user.getNombre();
        String apellidos = user.getApellidos();
        String dni = user.getDni();
        int comprobarAdd;

        try (var ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, dni);

            comprobarAdd = ps.executeUpdate();

            if (comprobarAdd != 0) {

                var rs = ps.getGeneratedKeys();
                rs.next();
                user.setId(rs.getLong(1));

                System.out.println("Método save completado");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Usuario> list() {

        var salida = new ArrayList<Usuario>(0);

        /* Completar código para devolver un arraylist con todos los usuarios */

        try (var st = connection.createStatement()) {
            var rs = st.executeQuery(LIST_QUERY);

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellidos(rs.getString(3));
                usuario.setDni(rs.getString(4));

                salida.add(usuario);
            }

            System.out.println("Método list  completado");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return salida;
    }

    public Usuario getByDNI(String dni) {

        Usuario salida = new Usuario();

        /**
         * Completar código para devolver el usuario que tenga ese dni.
         * Si no existe, se debe devolver null
         */

        try (var ps = connection.prepareStatement(GET_BY_DNI)) {
            ps.setString(1, dni);

            var rs = ps.executeQuery();

            rs.next();

            salida.setId(rs.getLong(1));
            salida.setNombre(rs.getString(2));
            salida.setApellidos(rs.getString(3));
            salida.setDni(rs.getString(4));

            System.out.println("Método getByDNI completado");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salida;
    }
}
