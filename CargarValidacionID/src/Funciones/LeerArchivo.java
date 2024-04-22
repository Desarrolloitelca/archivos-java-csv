package Funciones;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class LeerArchivo {

    static int lineasTotales;

    public LeerArchivo(File archivo) throws SQLException, CsvException {

        lineasTotales = 0;
        ConexionBD cone = new ConexionBD();

        try (CSVReader leer = new CSVReader(new FileReader(archivo), ';');
             Connection conex = cone.getConexion()) {

            // SI EXISTE EL ARCHIVO
            if (archivo.exists()) {
                String[] lineaLeida;

                // MIENTRAS LA LINEA LEIDA NO SEA NULL
               boolean firstLine = true;
                while ((lineaLeida = leer.readNext()) != null) {
                       if (firstLine) {
        // Saltar la primera línea (encabezados)
        firstLine = false;
        continue;
    }
                    lineasTotales++;
                    System.out.println("Datos leídos: " + Arrays.toString(lineaLeida)); // Imprimir los datos leídos
                    System.out.println("Número total de líneas leídas: " + lineasTotales);
                    System.out.println("Longitud de la línea leída: " + lineaLeida.length);
                    // Verificar si la línea es válida
                    if (lineaLeida.length >= 8) {
                        System.out.println("Leyendo línea: " + Arrays.toString(lineaLeida)); // Mensaje de depuración
                        System.out.println("Intentando insertar datos: " + Arrays.toString(lineaLeida));
                        // Preparar consulta SQL
                        String query = ("INSERT INTO Telefonia.dbo.Validacion_ID (dia,hora,duration,ani,extension,pbx_login_id,nombre,agent_id)VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                        System.out.println("Consulta SQL: " + query); // Mensaje de depuración

                        try (PreparedStatement sta = conex.prepareStatement(query)) {
                           System.out.println("Fecha leída del archivo: " + lineaLeida[0]); // Imprimir la fecha leída del archivo
for (int i = 0; i < lineaLeida[0].length(); i++) {
    char c = lineaLeida[0].charAt(i);
    System.out.println("Carácter: " + c + " | Valor ASCII: " + (int) c);
}sta.setDate(1, java.sql.Date.valueOf(lineaLeida[0].replaceFirst("^\\p{C}", ""))); // Dia
                            sta.setTime(2, java.sql.Time.valueOf(lineaLeida[1]));// Hora
                            sta.setInt(3, Integer.parseInt(lineaLeida[2])); // Duration
                            sta.setString(4, lineaLeida[3]); // Ani
                            sta.setInt(5, Integer.parseInt(lineaLeida[4])); // Extension
                            sta.setString(6, lineaLeida[5]); // Pbx_login_id
                            sta.setString(7, lineaLeida[6]); // Nombre_Usuario
                            sta.setInt(8, Integer.parseInt(lineaLeida[7])); // Agent_ID
                            int rowsAffected = sta.executeUpdate();
                          
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("Error en la ejecución de la consulta SQL: " + e.getMessage());
                        }
                    }
                }
            } else {
                System.out.println("NO EXISTE EL ARCHIVO");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Especifica la ruta completa del archivo CSV aquí
        String rutaArchivo = "C:\\Users\\Aplicaciones\\Documents\\CargarValidacionID\\archivos";
        File archivo = new File(rutaArchivo);

        try {
            new LeerArchivo(archivo);
        } catch (SQLException | CsvException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}