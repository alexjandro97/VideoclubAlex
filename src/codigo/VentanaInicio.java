package codigo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/*
* Querido colega programador:
*
* Cuando escribí este código, sólo Dios y yo
* sabiamos cómo funcionaba.
* Ahora, ¡Sólo Dios lo sabe!
*
* Así que si estas intentando 'optimizar' el código
* y fracasas (seguramente), por favor, 
* incremente el contador a continuación
* como advertencia para su siguiente colega:
*
* total_horas_perdidas_en_este_codigo = 43
*/

/**
 *
 * @author alexj
 */
public class VentanaInicio extends javax.swing.JFrame {
    
    Connection conexion;//para almacenar la conexion
    Statement estado;//almacenar el estado de la conexion
    ResultSet resultado;//resultado de la consulta
    //Array para guardar el nombre y apellido del usuario
    static String[] nombres = new String[2];
    static MenuPrincipal frame = new MenuPrincipal();
   
    
    private void login() {
        String user = userText.getText();
        String pass = String.valueOf(password.getPassword());
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.usuarios WHERE DNI='"+user+"'&& DNI='"+pass+"'");
            resultado.last();
            
            int logCorrecto = resultado.getRow();
            
            if(logCorrecto == 1){
                //pasamos al siguiente Frame
                frame.setVisible(true);
                //hago una consulta para sacar el nombre y apellido del usuario segun el DNI (clave)
                ResultSet nombre = estado.executeQuery("SELECT * FROM videoclub.usuarios WHERE DNI='"+user+"'");
                while(nombre.next()){
                    nombres[0] = nombre.getString("Nombre");
                    nombres[1] = nombre.getString("Apellido");
                }
                MenuPrincipal.nombreApellidosUser.setText("Bienvenida al VideoClub Malasaña: " + nombres[0] + " " + nombres[1]);
                
                //declaro la foto del usuario para meterla en el jLabel
                ImageIcon fotoUsuario = new ImageIcon(
                                          getClass().getResource("/fotosUsuarios/"+user+".jpg"));
              
                //meto la foto en el jLabel
                MenuPrincipal.fotoUser.setIcon(fotoUsuario);
               
                this.setVisible(false);
            } else {
                ventanaError.setVisible(true);
            }
        } catch(ClassNotFoundException ex){
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex){
            System.out.println("No se ha podido conectar con la BBDD");
        }
    }
    
    private void conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.usuarios");
        } catch(ClassNotFoundException ex){
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex){
            System.out.println("No se ha podido conectar con la BBDD");
        }
    }
    
    /**
     * Creates new form VentanaInicio
     */
    public VentanaInicio() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/img/claqueta1.png")).getImage());
        this.setLocation(450, 225);
        ventanaError.setSize(510, 300);
        ventanaError.setLocation(400, 225);
        conexion();
        this.setTitle("Videoclub Malasaña");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaError = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        fondoFrame = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Datos incorrectos, intentelo de nuevo.");

        jButton1.setText("Volver al Login");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout ventanaErrorLayout = new javax.swing.GroupLayout(ventanaError.getContentPane());
        ventanaError.getContentPane().setLayout(ventanaErrorLayout);
        ventanaErrorLayout.setHorizontalGroup(
            ventanaErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaErrorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(ventanaErrorLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ventanaErrorLayout.setVerticalGroup(
            ventanaErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaErrorLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondoFrame.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login Videoclub Malasaña");
        fondoFrame.add(jLabel1);
        jLabel1.setBounds(53, 16, 323, 36);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("User:");
        fondoFrame.add(jLabel2);
        jLabel2.setBounds(53, 72, 60, 19);

        userText.setText("5992988");
        fondoFrame.add(userText);
        userText.setBounds(139, 70, 186, 24);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Password:");
        fondoFrame.add(jLabel3);
        jLabel3.setBounds(53, 113, 80, 19);

        login.setText("Acceder");
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                loginMousePressed(evt);
            }
        });
        fondoFrame.add(login);
        login.setBounds(170, 150, 118, 53);

        password.setText("5992988");
        fondoFrame.add(password);
        password.setBounds(139, 112, 186, 22);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoFrame, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoFrame, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMousePressed
        login();
    }//GEN-LAST:event_loginMousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        ventanaError.setVisible(false);
    }//GEN-LAST:event_jButton1MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fondoFrame;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton login;
    public static javax.swing.JPasswordField password;
    public static javax.swing.JTextField userText;
    private javax.swing.JDialog ventanaError;
    // End of variables declaration//GEN-END:variables
}
