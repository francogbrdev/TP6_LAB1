package tp6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeSet;

public class GestorProductosGUI extends JFrame {
    private TreeSet<Producto> productos = new TreeSet<>();
    private DefaultTableModel model;
    public GestorProductosGUI() {
        super("TP6 - Gestor de Productos (GUI)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);
        setJMenuBar(createMenu());
        initSample();
        model = new DefaultTableModel(new Object[]{"Código","Descripción","Rubro","Precio","Stock"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable table = new JTable(model);
        refreshTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Agregar producto");
        btnAdd.addActionListener(e -> {
            AgregarProductoDialog dlg = new AgregarProductoDialog(this);
            dlg.setVisible(true);
            Producto p = dlg.getProducto();
            if (p != null) {
                boolean added = productos.add(p);
                if (!added) JOptionPane.showMessageDialog(this,"Ya existe un producto con ese código.");
                refreshTable();
            }
        });
        bottom.add(btnAdd);
        add(bottom, BorderLayout.SOUTH);
    }

    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu admin = new JMenu("Administración");
        JMenuItem mGest = new JMenuItem("Gestión Productos");
        admin.add(mGest);
        JMenu consultas = new JMenu("Consultas");
        JMenuItem porNombre = new JMenuItem("Por Nombre");
        JMenuItem porPrecio = new JMenuItem("Por Precio");
        JMenuItem porRubro = new JMenuItem("Por Rubro");
        consultas.add(porNombre); consultas.add(porPrecio); consultas.add(porRubro);
        mb.add(admin); mb.add(consultas);

        porNombre.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(this,"Nombre a buscar:");
            if (q!=null) {
                StringBuilder sb = new StringBuilder();
                for (Producto p: productos) if (p.getDescripcion().equalsIgnoreCase(q)) sb.append(p).append("\\n");
                JOptionPane.showMessageDialog(this, sb.length()==0? "No encontrado": sb.toString());
            }
        });
        porPrecio.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(this,"Precio máximo:");
            try {
                double max = Double.parseDouble(q);
                StringBuilder sb = new StringBuilder();
                for (Producto p: productos) if (p.getPrecio() <= max) sb.append(p).append("\\n");
                JOptionPane.showMessageDialog(this, sb.length()==0? "No encontrado": sb.toString());
            } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Precio inválido."); }
        });
        porRubro.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(this,"Rubro:");
            if (q!=null) {
                StringBuilder sb = new StringBuilder();
                for (Producto p: productos) if (p.getRubro().equalsIgnoreCase(q)) sb.append(p).append("\\n");
                JOptionPane.showMessageDialog(this, sb.length()==0? "No encontrado": sb.toString());
            }
        });

        return mb;
    }

    private void initSample() {
        productos.add(new Producto(1,"Arroz",1200,50,"Comestible"));
        productos.add(new Producto(2,"Shampoo",3500,20,"Perfumería"));
        productos.add(new Producto(3,"Lavandina",800,100,"Limpieza"));
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Producto p: productos) {
            model.addRow(new Object[]{p.getCodigo(),p.getDescripcion(),p.getRubro(),p.getPrecio(),p.getStock()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorProductosGUI().setVisible(true));
    }
}
