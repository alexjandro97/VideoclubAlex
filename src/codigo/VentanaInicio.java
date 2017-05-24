package codigo;

import java.awt.Image;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
* total_horas_perdidas_en_este_codigo = 6
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
    
    private void login(){
        String user = userText.getText();
        String pass = String.valueOf(password.getPassword());
      try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.usuarios WHERE DNI='"+user+"'&& DNI='"+pass+"'");
            resultado.last();
            
            int logCorrecto = resultado.getRow();
            
            if(logCorrecto == 1){
                //MenuPrincipal.nombreApellidosUser.setText(user);
                frame.setVisible(true);
                //hago una consulta para sacar el nombre y apellido del usuario segun el DNI (clave)
                ResultSet nombre = estado.executeQuery("SELECT Nombre, Apellido FROM videoclub.usuarios WHERE DNI='"+user+"'");
                while(nombre.next()){
                    nombres[0] = nombre.getString("Nombre");
                    nombres[1] = nombre.getString("Apellido");
                }
                MenuPrincipal.nombreApellidosUser.setText(nombres[0] + " " + nombres[1]);
                //declaro la foto del usuario para meterla en el jLabel
                ImageIcon fotoUsuario = new ImageIcon(
                                          getClass().getResource("/fotosUsuarios/"+user + ".jpg")
                                            );
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
        this.setLocation(450, 225);
        ventanaError.setSize(510, 300);
        ventanaError.setLocation(400, 225);
        conexion();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaError = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
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

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login Videoclub Malasaña");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("User:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Password:");

        login.setText("Acceder");
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                loginMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userText)
                            .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGap(39, 39, 39)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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
