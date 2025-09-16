package tp6;

import javax.swing.*;
import java.awt.*;

public class AgregarProductoDialog extends JDialog {
    private Producto producto = null;
    public AgregarProductoDialog(JFrame parent) {
        super(parent,"Agregar Producto", true);
        setSize(350,300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6,2,4,4));
        JTextField txtCodigo = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();
        JComboBox<String> cbRubro = new JComboBox<>(new String[]{"Comestible","Limpieza","Perfumería"});
        add(new JLabel("Código:")); add(txtCodigo);
        add(new JLabel("Descripción:")); add(txtDesc);
        add(new JLabel("Precio:")); add(txtPrecio);
        add(new JLabel("Stock:")); add(txtStock);
        add(new JLabel("Rubro:")); add(cbRubro);
        JButton btnOk = new JButton("Agregar");
        add(new JLabel()); add(btnOk);
        btnOk.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String desc = txtDesc.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                String rubro = (String)cbRubro.getSelectedItem();
                producto = new Producto(codigo, desc, precio, stock, rubro);
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Datos inválidos.");
            }
        });
    }
    public Producto getProducto() { return producto; }
}
