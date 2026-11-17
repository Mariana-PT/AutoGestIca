package autogest_ica;

import Estructuras.Arreglo_Bidimensional;
import Estructuras.Arreglo_Unidimensional;
import Estructuras.Lista_Enlazada_Simple;
import Estructuras.Lista_Doblemente_Enlazada;
import Datos.ClienteDAO;
import Estructuras.ArbolBinarioServicios;
import Estructuras.ColaPrioridadAtencion;
import Estructuras.PilaHistorialAcciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Interfaz_Principal extends JFrame {

    private JTabbedPane tabbedPane;
    private Arreglo_Unidimensional repuestos = new Arreglo_Unidimensional(5);
    private Arreglo_Bidimensional bahias = new Arreglo_Bidimensional();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Lista_Enlazada_Simple listaOrdenes = new Lista_Enlazada_Simple();
    private Lista_Doblemente_Enlazada listaClientesDoble = new Lista_Doblemente_Enlazada();
    private PilaHistorialAcciones pilaAcciones = new PilaHistorialAcciones();
    private ColaPrioridadAtencion colaAtencion = new ColaPrioridadAtencion();
    private ArbolBinarioServicios arbolFallas = new ArbolBinarioServicios();

    public Interfaz_Principal() {
        setTitle("AutoGest - Ica | Sistema de Gestion Automotriz");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clientes (BD)", crearPanelClientes());
        tabbedPane.addTab("Repuestos Rapidos (Array 1D)", crearPanelRepuestos());
        tabbedPane.addTab("Bahias de Trabajo (Matriz 2D)", crearPanelBahias());
        tabbedPane.addTab("Órdenes (Lista Simple)", crearPanelListaSimple());
        tabbedPane.addTab("Clientes RAM (Lista Doble)", crearPanelListaDoble());
        tabbedPane.addTab("Historial (Pila)", crearPanelPila());
        tabbedPane.addTab("Recepción (Cola Prioridad)", crearPanelCola());
        tabbedPane.addTab("Diagnóstico (Árbol Binario)", crearPanelArbol());
        add(tabbedPane);
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtDni = new JTextField();
        JTextField txtNombres = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtDireccion = new JTextField();
        JButton btnGuardar = new JButton("Registrar Cliente en BD");

        panel.add(new JLabel("DNI:"));
        panel.add(txtDni);
        panel.add(new JLabel("Nombres y Apellidos:"));
        panel.add(txtNombres);
        panel.add(new JLabel("Telefono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Direccion:"));
        panel.add(txtDireccion);
        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        btnGuardar.addActionListener((ActionEvent e) -> {
            if (!txtDni.getText().trim().matches("\\d{8}") || txtNombres.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El DNI debe tener 8 digitos y los nombres son obligatorios.", "Datos invalidos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean exito = clienteDAO.registrarCliente(
                    txtDni.getText().trim(),
                    txtNombres.getText().trim(),
                    txtTelefono.getText(),
                    txtCorreo.getText(),
                    txtDireccion.getText()
            );
            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente en la Base de Datos");
                txtDni.setText("");
                txtNombres.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtDireccion.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el cliente (¿DNI duplicado o sin conexion a la BD?). Revise la consola.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel crearPanelRepuestos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new FlowLayout());
        JTextField txtRepuesto = new JTextField(15);
        JButton btnAgregar = new JButton("Añadir al Arreglo");
        panelSuperior.add(new JLabel("Nombre Repuesto:"));
        panelSuperior.add(txtRepuesto);
        panelSuperior.add(btnAgregar);

        JTextArea txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaLog);

        btnAgregar.addActionListener(e -> {
            if (txtRepuesto.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre del repuesto.");
                return;
            }
            boolean insertado = repuestos.insertar(txtRepuesto.getText());
            if (insertado) {
                txtAreaLog.setText(repuestos.mostrar());
                txtRepuesto.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "El arreglo unidimensional esta lleno (Capacidad: 5).");
            }
        });

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBahias() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea txtMatrizLog = new JTextArea("Matriz de Bahias (5 días x 4 bahías):\n\n" + bahias.mostrarMatriz());
        txtMatrizLog.setEditable(false);

        JButton btnSimular = new JButton("Simular Ocupacion de Bahia (Día 1, Bahia 2)");
        btnSimular.addActionListener(e -> {
            bahias.ocuparBahia(0, 1);
            txtMatrizLog.setText("-> Bahia 2 del Dia 1 marcada como OCUPADA (1).\n\n" + bahias.mostrarMatriz());
        });

        panel.add(btnSimular, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtMatrizLog), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelListaSimple() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelControles = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField txtCodigo = new JTextField();
        JTextField txtPlaca = new JTextField();

        panelControles.add(new JLabel("Codigo de Orden (Int):"));
        panelControles.add(txtCodigo);
        panelControles.add(new JLabel("Placa de Vehiculo:"));
        panelControles.add(txtPlaca);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnInsertar = new JButton("Insertar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnOrdenar = new JButton("Ordenar");
        JButton btnMostrar = new JButton("Mostrar Todos");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnOrdenar);
        panelBotones.add(btnMostrar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(panelControles, BorderLayout.CENTER);
        norte.add(panelBotones, BorderLayout.SOUTH);

        JTextArea txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaLog);

        btnInsertar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigo.getText());
                String placa = txtPlaca.getText();
                listaOrdenes.insertar(cod, placa);
                txtAreaLog.append("Orden insertada -> Codigo: " + cod + " | Placa: " + placa + "\n");
                txtCodigo.setText("");
                txtPlaca.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El codigo debe ser un numero entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigo.getText());
                boolean encontrado = listaOrdenes.buscar(cod);
                if (encontrado) {
                    txtAreaLog.append("Busqueda: La orden con codigo " + cod + " SI existe.\n");
                } else {
                    txtAreaLog.append("Busqueda: La orden con codigo " + cod + " NO fue encontrada.\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un codigo valido para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigo.getText());
                boolean eliminado = listaOrdenes.eliminar(cod);
                if (eliminado) {
                    txtAreaLog.append("Eliminacion exitosa de la orden codigo: " + cod + "\n");
                } else {
                    txtAreaLog.append("No se pudo eliminar: Orden no encontrada.\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un codigo valido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnOrdenar.addActionListener(e -> {
            listaOrdenes.ordenarPorCodigo();
            txtAreaLog.append("-> Lista de ordenes ordenada por codigo (Metodo Burbuja).\n");
        });

        btnMostrar.addActionListener(e -> {
            txtAreaLog.append("--- ESTADO ACTUAL DE LA LISTA SIMPLE ---\n");
            txtAreaLog.append(listaOrdenes.mostrar());
        });

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelListaDoble() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelControles = new JPanel(new GridLayout(2, 2, 8, 8));
        JTextField txtDniDoble = new JTextField();
        JTextField txtNombreDoble = new JTextField();

        panelControles.add(new JLabel("DNI del Cliente:"));
        panelControles.add(txtDniDoble);
        panelControles.add(new JLabel("Nombres:"));
        panelControles.add(txtNombreDoble);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnInsertarInicio = new JButton("Insertar al Inicio");
        JButton btnMostrarDoble = new JButton("Mostrar Adelante");

        panelBotones.add(btnInsertarInicio);
        panelBotones.add(btnMostrarDoble);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(panelControles, BorderLayout.CENTER);
        norte.add(panelBotones, BorderLayout.SOUTH);

        JTextArea txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaLog);

        btnInsertarInicio.addActionListener(e -> {
            String dni = txtDniDoble.getText();
            String nombre = txtNombreDoble.getText();
            if (!dni.isEmpty() && !nombre.isEmpty()) {
                listaClientesDoble.insertarInicio(dni, nombre);
                txtAreaLog.append("Cliente insertado en Lista Doble -> DNI: " + dni + " | Nombre: " + nombre + "\n");
                txtDniDoble.setText("");
                txtNombreDoble.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Complete los campos de DNI y Nombre.");
            }
        });

        btnMostrarDoble.addActionListener(e -> {
            txtAreaLog.append("--- CONTENIDO DE LA LISTA DOBLE (CLIENTES) ---\n");
            txtAreaLog.append(listaClientesDoble.mostrarAdelante());
        });

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelPila() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel norte = new JPanel(new FlowLayout());
    JTextField txtAccion = new JTextField(15);
    JButton btnPush = new JButton("Registrar / Push Accion");
    JButton btnPop = new JButton("Deshacer / Pop");
    norte.add(new JLabel("Detalle de Operacion:"));
    norte.add(txtAccion);
    norte.add(btnPush);
    norte.add(btnPop);

    JTextArea txtLog = new JTextArea();
    txtLog.setEditable(false);

    btnPush.addActionListener(e -> {
        if (!txtAccion.getText().isEmpty()) {
            pilaAcciones.push(txtAccion.getText());
            txtLog.setText("--- PILA DE ACCIONES (LIFO) ---\n" + pilaAcciones.mostrarPila());
            txtAccion.setText("");
        }
    });

    btnPop.addActionListener(e -> {
        String removida = pilaAcciones.pop();
        if (removida != null) {
            JOptionPane.showMessageDialog(this, "Accion desapilada: " + removida);
        } else {
            JOptionPane.showMessageDialog(this, "La pila esta vacía.");
        }
        txtLog.setText("--- PILA DE ACCIONES (LIFO) ---\n" + pilaAcciones.mostrarPila());
    });

    panel.add(norte, BorderLayout.NORTH);
    panel.add(new JScrollPane(txtLog), BorderLayout.CENTER);
    return panel;
}

private JPanel crearPanelCola() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel norte = new JPanel(new GridLayout(3, 2, 5, 5));
    JTextField txtPlaca = new JTextField();
    JTextField txtServicio = new JTextField();
    JComboBox<String> cmbPrioridad = new JComboBox<>(new String[]{"1 - Alta (Emergencia)", "2 - Normal (Mantenimiento)"});
    
    norte.add(new JLabel("Placa Vehiculo:")); norte.add(txtPlaca);
    norte.add(new JLabel("Descripcion Servicio:")); norte.add(txtServicio);
    norte.add(new JLabel("Nivel Prioridad:")); norte.add(cmbPrioridad);

    JPanel panelBotones = new JPanel(new FlowLayout());
    JButton btnEncolar = new JButton("Registrar en Cola");
    JButton btnAtender = new JButton("Atender Siguiente (Dequeue)");
    panelBotones.add(btnEncolar);
    panelBotones.add(btnAtender);

    JPanel topContainer = new JPanel(new BorderLayout());
    topContainer.add(norte, BorderLayout.CENTER);
    topContainer.add(panelBotones, BorderLayout.SOUTH);

    JTextArea txtLog = new JTextArea();
    txtLog.setEditable(false);

    btnEncolar.addActionListener(e -> {
        if (!txtPlaca.getText().isEmpty()) {
            int prio = cmbPrioridad.getSelectedIndex() == 0 ? 1 : 2;
            colaAtencion.encolar(txtPlaca.getText(), txtServicio.getText(), prio);
            txtLog.setText("--- COLA DE ATENCION CON PRIORIDAD ---\n" + colaAtencion.mostrarCola());
            txtPlaca.setText(""); txtServicio.setText("");
        }
    });

    btnAtender.addActionListener(e -> {
        String atendido = colaAtencion.desencolar();
        if (atendido != null) {
            JOptionPane.showMessageDialog(this, "Atendiendo a -> " + atendido);
        } else {
            JOptionPane.showMessageDialog(this, "La cola de atencion esta vacia.");
        }
        txtLog.setText("--- COLA DE ATENCION CON PRIORIDAD ---\n" + colaAtencion.mostrarCola());
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(new JScrollPane(txtLog), BorderLayout.CENTER);
    return panel;
}

private JPanel crearPanelArbol() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel norte = new JPanel(new GridLayout(2, 2, 5, 5));
    JTextField txtCodigoFalla = new JTextField();
    JTextField txtDescFalla = new JTextField();
    norte.add(new JLabel("Codigo de Falla (Int):")); norte.add(txtCodigoFalla);
    norte.add(new JLabel("Descripcion del Sistema/Falla:")); norte.add(txtDescFalla);

    JPanel panelBotones = new JPanel(new FlowLayout());
    JButton btnInsertarArbol = new JButton("Insertar en Arbol");
    JButton btnRecorridos = new JButton("Mostrar Recorridos (Pre, In, Post)");
    panelBotones.add(btnInsertarArbol);
    panelBotones.add(btnRecorridos);

    JPanel topContainer = new JPanel(new BorderLayout());
    topContainer.add(norte, BorderLayout.CENTER);
    topContainer.add(panelBotones, BorderLayout.SOUTH);

    JTextArea txtLog = new JTextArea();
    txtLog.setEditable(false);

    btnInsertarArbol.addActionListener(e -> {
        try {
            int cod = Integer.parseInt(txtCodigoFalla.getText());
            arbolFallas.insertar(cod, txtDescFalla.getText());
            txtLog.append("Falla insertada en el arbol jerarquico -> Codigo: " + cod + "\n");
            txtCodigoFalla.setText(""); txtDescFalla.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un código numérico válido.");
        }
    });

    btnRecorridos.addActionListener(e -> {
        txtLog.setText("=== RECORRIDOS DEL ARBOL DE DIAGNOSTICO ===\n");
        txtLog.append("1. Preorden  : " + arbolFallas.obtenerPreorden() + "\n");
        txtLog.append("2. Inorden   : " + arbolFallas.obtenerInorden() + "\n");
        txtLog.append("3. Postorden : " + arbolFallas.obtenerPostorden() + "\n");
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(new JScrollPane(txtLog), BorderLayout.CENTER);
    return panel;
}
    
}
