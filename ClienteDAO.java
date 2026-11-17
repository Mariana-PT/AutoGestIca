package Datos;

import autogest_ica.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public boolean registrarCliente(String dni, String nombres, String telefono, String correo, String direccion) {
        String sql = "INSERT INTO Cliente (dni, nombres, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion()) {
            if (con == null) {
                return false; // sin conexion: el error ya se imprimio en ConexionDB
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, dni);
                ps.setString(2, nombres);
                ps.setString(3, vacioANull(telefono));
                ps.setString(4, vacioANull(correo));
                ps.setString(5, vacioANull(direccion));
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente en BD: " + e.getMessage());
            return false;
        }
    }

    private String vacioANull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
