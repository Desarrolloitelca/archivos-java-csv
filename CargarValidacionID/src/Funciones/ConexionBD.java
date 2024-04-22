// ConexionBD.java
package Funciones;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private Connection conexion;

    public ConexionBD() throws SQLException {
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser("sa");
            ds.setPassword("%It3lca$2020");
            ds.setServerName("10.218.1.121");
            ds.setPortNumber(1433);
            ds.setDatabaseName("Telefonia");
            conexion = ds.getConnection();

        } catch (SQLServerException exc) {
            System.out.println(exc);
        }
    }
 public Connection getConexion() {
        return conexion;
    }

   public static void main(String[] args) {
        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.getConexion();
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos.");
                
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}