package Funciones;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.opencsv.exceptions.CsvException;

public class Validacion {
    ConexionBD conexion;
    Connection cones;

    public Validacion(String ruta) {
        try {
            System.out.println("Estableciendo conexión a la base de datos...");
            conexion = new ConexionBD();
            cones = conexion.getConexion();

            crearDirectorios(ruta);
            File directory = new File(ruta);
            String[] archivos = directory.list();

           if (archivos != null && archivos.length > 1) {
    for (String archivo1 : archivos) {
        if (archivo1.endsWith(".csv")) {
            System.out.println(archivo1);
            if (existeRegistro(archivo1, ruta)) {
                System.out.println("El archivo " + archivo1 + " ya fue procesado anteriormente.");
            } else {
                System.out.println("Ejecutar manejo archivo");
                File archivo = new File(ruta + "/" + archivo1);
                LeerArchivo leer = new LeerArchivo(archivo);
                historico(archivo1);
            }
        }
    }

// Movemos los archivos después de procesar todos los archivos en el directorio
    for (String archivo1 : archivos) {
        if (archivo1.endsWith(".csv")) {
            move(ruta, archivo1);
        }
    }
            } else {
                System.out.println("No hay archivos para procesar.");
            }
        } catch (SQLException | IOException | CsvException e) {
            System.out.println("Error al procesar archivos: " + e.getMessage());
        }
    }
    

    private void crearDirectorios(String ruta) throws IOException {
        File procesados = new File(ruta + "/procesados");
        File noProcesados = new File(ruta + "/noProcesados");

        if (!procesados.exists()) {
            Files.createDirectory(Paths.get(ruta + "/procesados"));
        }

        if (!noProcesados.exists()) {
            Files.createDirectory(Paths.get(ruta + "/noProcesados"));
        }
    }

   private boolean existeRegistro(String archivo, String ruta) {
    boolean existe = false;
    try {
        String query = "SELECT ARCHIVO FROM HistoricoValidacionID1 where ARCHIVO='" + archivo + "' ";
        Statement sta = cones.createStatement();
        ResultSet rs = sta.executeQuery(query);
        String historics = null;
        while (rs.next()) {
            historics = rs.getString(1);
        }

        if (archivo.equals(historics)) {
            existe = true;
            System.out.println("Existe mover");
            move(ruta, archivo);
        }
    } catch (SQLException | IOException e) {
        System.out.println("Error al verificar el registro: " + e.getMessage());
    }
    return existe;
}


    private void historico(String Archivo) throws SQLException {
        System.out.println("Registrando archivo en el historial...");
        Statement st = cones.createStatement();
        String query = "insert into HistoricoValidacionID1 (ARCHIVO,FECHA_CARGUE,ESTADO) values('" + Archivo + "', GETDATE(),'procesado')";
        st.executeUpdate(query);
    }

    
//metodo 
private void move(String ruta, String Archivo) throws IOException {
        System.out.println("Moviendo archivo a la carpeta de archivos  procesados...");
        if ((Files.exists(Paths.get(ruta + "/procesados/" + Archivo.replace(".csv", "") + "_procesado.csv")))) {
            Path temp = Files.move(Paths.get(ruta + "/" + Archivo),
            Paths.get(ruta + "/noProcesados/" + Archivo.replace(".csv", "") + "_Noprocesado.csv"));
            System.out.println("Archivo ya existe");
            Files.delete(Paths.get(ruta + "/" + Archivo));
        } else {
            System.out.println("Moviendo archivo a la carpeta de archivos procesados...");
            Path temp = Files.move(Paths.get(ruta + "/" + Archivo),
                    Paths.get(ruta + "/procesados/" + Archivo.replace(".csv", "") + "_procesado.csv"));
                    System.out.println("Archivo existente almacenado");
            if (temp != null) {
                System.out.println("Archivo renombrado y movido exitosamente");
            } else {
                System.out.println("No se pudo mover el archivo");
            }
        }
    }

   public static void main(String[] args) {
    try {
        // ruta donde están los archivos CSV
        String ruta = "C:\\Users\\Aplicaciones\\Documents\\NetBeansProjects\\ValidacionIDExcel\\archivos";
        new Validacion(ruta);
    }  catch (Exception e) {
        System.out.println("Otro error: " + e.getMessage());
    }
}
}
