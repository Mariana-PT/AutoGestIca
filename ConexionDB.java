package autogest_ica;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Conexion a MySQL. La configuracion se lee, en este orden, de:
 * 1) variables de entorno DB_URL, DB_USER, DB_PASSWORD
 * 2) archivo db.properties (junto al proyecto, ignorado por git)
 * 3) valores por defecto (localhost, root, sin contraseña)
 */
public class ConexionDB {

    private static final String URL_DEFECTO
            = "jdbc:mysql://localhost:3306/db_autigest_ica?useSSL=false&serverTimezone=UTC";

    private static final Properties CONFIG = cargarConfig();

    private static Properties cargarConfig() {
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream("db.properties")) {
            p.load(in);
        } catch (IOException e) {
            // Sin archivo: se usan variables de entorno o valores por defecto
        }
        return p;
    }

    private static String valor(String envVar, String propKey, String defecto) {
        String env = System.getenv(envVar);
        if (env != null && !env.isEmpty()) {
            return env;
        }
        return CONFIG.getProperty(propKey, defecto);
    }

    public static Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    valor("DB_URL", "db.url", URL_DEFECTO),
                    valor("DB_USER", "db.user", "root"),
                    valor("DB_PASSWORD", "db.password", ""));
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encontro el Driver de MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR DE SQL: No se pudo conectar a la BD. Verifica usuario/puerto/servidor: " + e.getMessage());
        }
        return null;
    }
}
