

package CargarArchivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Funciones.ConexionBD;
import Funciones.LeerArchivo;
import Funciones.Validacion;
import com.opencsv.exceptions.CsvException;
import java.io.File;



public class CargarArchivo {

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Aplicaciones\\Documents\\CargarValidacionID\\archivos";
        Validacion val = new Validacion(ruta);
    
    }
}
