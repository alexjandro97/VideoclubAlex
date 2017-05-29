package codigo;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author alexj
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    Connection conexion;//para almacenar la conexion
    Statement estado;//almacenar el estado de la conexion
    ResultSet resultado;//resultado de la consulta
    static VentanaInicio login = new VentanaInicio();
    private Dimension dim;
    String[] datosPeliculas = new String[8];
    ArrayList<String[]> datosPeliculasList = new ArrayList();
    String[] datosUsuario = new String[5];
    ArrayList<String[]> datosUsuarioList = new ArrayList();
    int anchoPeli = 95;
    int altoPeli = 160;
    int contador = 0;
    
    private void usuarioMenu() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.usuarios");
            resultado.first();
                while (resultado.next()) {
                    datosUsuario[0] = resultado.getString("DNI");
                    datosUsuario[1] = resultado.getString("Nombre");
                    datosUsuario[2] = resultado.getString("Apellido");
                    datosUsuario[3] = resultado.getString("Penalizacion");
                    datosUsuario[4] = resultado.getString("email");
                    datosUsuarioList.add(datosUsuario);
                }
            
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BBDD");
        }
    }

    private void pelicula() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.peliculas");
            resultado.first();
            while (resultado.next()) {
                datosPeliculas[0] = resultado.getString("id_pelicula");
                datosPeliculas[7] = resultado.getString("titulo");
                datosPeliculas[1] = resultado.getString("año");
                datosPeliculas[2] = resultado.getString("pais");
                datosPeliculas[3] = resultado.getString("genero");
                datosPeliculas[4] = resultado.getString("imdb");
                datosPeliculas[5] = resultado.getString("clasificacion_imdb");
                datosPeliculas[6] = resultado.getString("resumen");
                datosPeliculasList.add(datosPeliculas);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BBDD");
        }
    }
    
    public void slider(){
        Image derecha = (new ImageIcon(new ImageIcon(getClass().getResource("/img/derecha.png")).getImage()
                .getScaledInstance(56, 80, Image.SCALE_DEFAULT))).getImage();
        
        ImageIcon derechaFinal = new ImageIcon(derecha);
        
        movDre.setIcon(derechaFinal);
        movDre.updateUI();
        
        Image izquierda = (new ImageIcon(new ImageIcon(getClass().getResource("/img/izquierda.png")).getImage()
                .getScaledInstance(56, 80, Image.SCALE_DEFAULT))).getImage();
        
        ImageIcon izquierdaFinal = new ImageIcon(izquierda);
        
        movIzq.setIcon(izquierdaFinal);
        movIzq.updateUI();
        
       imprimirCaratula(0);
    }
    
    public void imprimirDatosPeli(int posPeli){
        String nombreImagen2;
        nombreImagen2 = datosPeliculasList.get(posPeli)[0];
        for(int x = nombreImagen2.length(); x < 6; x++){
            nombreImagen2 = "0" + nombreImagen2;
        }
        Image fotoPeli = (new ImageIcon(new ImageIcon(getClass().getResource("/caratulas/"+nombreImagen2+".jpg")).getImage()
                .getScaledInstance(180, 220, Image.SCALE_DEFAULT))).getImage();
        
        ImageIcon caratula = new ImageIcon(fotoPeli);
        
        imagenPeli.setIcon(caratula);
        imagenPeli.updateUI();
        
        tituloPeli.setText("Titulo: " + datosPeliculasList.get(posPeli)[7]);
        anioPublicacion.setText("Año Publicación: " + datosPeliculasList.get(posPeli)[1]);
        genero.setText("Genero: " + datosPeliculasList.get(posPeli)[3]);
        paisPeli.setText("Pais: " + datosPeliculasList.get(posPeli)[2]);
        imdbPeli.setText("IMDB: " + datosPeliculasList.get(posPeli)[4]);
        calificacionPeli.setText("Calificación: " + datosPeliculasList.get(posPeli)[5]);
        descripcionPeli.setText(datosPeliculasList.get(posPeli)[6]);        
    }
    
    public void pulsarLabel(String posPeli){
        imprimirDatosPeli(Integer.valueOf(posPeli)-2);
    }
    
    public void imprimirCaratula(int posicion) {
       String nombreImagen;
       JLabel pelisLabel = new JLabel();
       int posX = posicion;
       
       for(int i = 0; i < 7; i++){
           pelisLabel = new JLabel();
           
           if(contador < datosPeliculasList.size()){
               nombreImagen = datosPeliculasList.get(contador)[0];
               for(int j = nombreImagen.length(); j < 6; i++){
                   nombreImagen = "0" + nombreImagen;
               }
               
               Image fotoPeli = (new ImageIcon(new ImageIcon(getClass().getResource("/caratulas/"+nombreImagen+".jpg")).getImage()
                .getScaledInstance(anchoPeli, altoPeli, Image.SCALE_DEFAULT))).getImage();
               ImageIcon caratula = new ImageIcon(fotoPeli);
               
               pelisLabel.setBounds(posX, 1, anchoPeli, altoPeli);
               pelisLabel.setText(datosPeliculasList.get(contador)[0]);
               pelisLabel.setVisible(true);
               pelisLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                   public void mousePressed(java.awt.event.MouseEvent evt){
                       pulsarLabel(datosPeliculasList.get(contador)[0]);
                   }
               });
               pelisPanel.add(pelisLabel);
               pelisPanel.updateUI();
               posX += anchoPeli +15;
           }
           contador++;
       }
    }
 
    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        this.setTitle("Inicio - Videoclub Malasaña");
        setIconImage(new ImageIcon(getClass().getResource("/img/claqueta1.png")).getImage());
        dim=super.getToolkit().getScreenSize();
        super.setSize(dim);
        this.setLocation(0, 0);
        peliculas.setSize(1024, 450);
        peliculas.setLocation(150, 90);
        peliculas.setIconImage(new ImageIcon(getClass().getResource("/img/claqueta1.png")).getImage());
        peliculas.setTitle("Videoclub Malasaña");
        usuarioDialog.setSize(600, 500);
        usuarioDialog.setLocation(400, 90);
        usuarioDialog.setTitle("Perfil - Videoclub Malasaña");
        alquilarPeliculas.setSize(1024, 450);
        alquilarPeliculas.setTitle("Alquilar - Videoclub Malasaña");
        alquilarPeliculas.setLocation(150, 90);
        pelicula();
        usuarioMenu();
        slider();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        peliculas = new javax.swing.JDialog();
        imagenPeli = new javax.swing.JLabel();
        tituloPeli = new javax.swing.JLabel();
        anioPublicacion = new javax.swing.JLabel();
        genero = new javax.swing.JLabel();
        paisPeli = new javax.swing.JLabel();
        imdbPeli = new javax.swing.JLabel();
        calificacionPeli = new javax.swing.JLabel();
        descripcionPeli = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usuarioDialog = new javax.swing.JDialog();
        dniLabel = new javax.swing.JLabel();
        nombreLabel = new javax.swing.JLabel();
        apellidoLabel = new javax.swing.JLabel();
        penalizacionLabel = new javax.swing.JLabel();
        emaillabel = new javax.swing.JLabel();
        fotoUserLabel = new javax.swing.JLabel();
        alquilarPeliculas = new javax.swing.JDialog();
        nombreApellidosUser = new javax.swing.JLabel();
        fotoUser = new javax.swing.JLabel();
        navegador = new javax.swing.JTabbedPane();
        Cine = new javax.swing.JPanel();
        pelisPanel = new javax.swing.JPanel();
        movIzq = new javax.swing.JLabel();
        movDre = new javax.swing.JLabel();
        Buscador = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        userItem = new javax.swing.JMenuItem();
        sessionItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        tituloPeli.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N

        descripcionPeli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descripcionPeli.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        descripcionPeli.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Resumen: ");

        javax.swing.GroupLayout peliculasLayout = new javax.swing.GroupLayout(peliculas.getContentPane());
        peliculas.getContentPane().setLayout(peliculasLayout);
        peliculasLayout.setHorizontalGroup(
            peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peliculasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descripcionPeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(peliculasLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(peliculasLayout.createSequentialGroup()
                        .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(peliculasLayout.createSequentialGroup()
                                .addComponent(imdbPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(genero, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calificacionPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(peliculasLayout.createSequentialGroup()
                                .addComponent(anioPublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(paisPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tituloPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                        .addComponent(imagenPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        peliculasLayout.setVerticalGroup(
            peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peliculasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagenPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(peliculasLayout.createSequentialGroup()
                        .addComponent(tituloPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(anioPublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(paisPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genero, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imdbPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(calificacionPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(descripcionPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout usuarioDialogLayout = new javax.swing.GroupLayout(usuarioDialog.getContentPane());
        usuarioDialog.getContentPane().setLayout(usuarioDialogLayout);
        usuarioDialogLayout.setHorizontalGroup(
            usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(usuarioDialogLayout.createSequentialGroup()
                        .addGroup(usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emaillabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(usuarioDialogLayout.createSequentialGroup()
                                .addGroup(usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(apellidoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dniLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 149, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(usuarioDialogLayout.createSequentialGroup()
                        .addComponent(penalizacionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(fotoUserLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        usuarioDialogLayout.setVerticalGroup(
            usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioDialogLayout.createSequentialGroup()
                .addGroup(usuarioDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(usuarioDialogLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(nombreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apellidoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(emaillabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(penalizacionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dniLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(usuarioDialogLayout.createSequentialGroup()
                        .addContainerGap(38, Short.MAX_VALUE)
                        .addComponent(fotoUserLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(189, 189, 189))
        );

        javax.swing.GroupLayout alquilarPeliculasLayout = new javax.swing.GroupLayout(alquilarPeliculas.getContentPane());
        alquilarPeliculas.getContentPane().setLayout(alquilarPeliculasLayout);
        alquilarPeliculasLayout.setHorizontalGroup(
            alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        alquilarPeliculasLayout.setVerticalGroup(
            alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nombreApellidosUser.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N

        javax.swing.GroupLayout pelisPanelLayout = new javax.swing.GroupLayout(pelisPanel);
        pelisPanel.setLayout(pelisPanelLayout);
        pelisPanelLayout.setHorizontalGroup(
            pelisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 858, Short.MAX_VALUE)
        );
        pelisPanelLayout.setVerticalGroup(
            pelisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout CineLayout = new javax.swing.GroupLayout(Cine);
        Cine.setLayout(CineLayout);
        CineLayout.setHorizontalGroup(
            CineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(movIzq, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pelisPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movDre, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        CineLayout.setVerticalGroup(
            CineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CineLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(movDre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(movIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pelisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        navegador.addTab("Cine", Cine);

        jButton1.setText("Buscar");

        javax.swing.GroupLayout BuscadorLayout = new javax.swing.GroupLayout(Buscador);
        Buscador.setLayout(BuscadorLayout);
        BuscadorLayout.setHorizontalGroup(
            BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(779, Short.MAX_VALUE))
        );
        BuscadorLayout.setVerticalGroup(
            BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(357, Short.MAX_VALUE))
        );

        navegador.addTab("Buscador", Buscador);

        jMenu1.setText("Sesión");

        userItem.setText("Usuario");
        userItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userItemMousePressed(evt);
            }
        });
        jMenu1.add(userItem);

        sessionItem.setText("Cerrar Sesión");
        sessionItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sessionItemMousePressed(evt);
            }
        });
        jMenu1.add(sessionItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Alquilar");

        jMenuItem1.setText("Alquilar Película");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombreApellidosUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fotoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(navegador))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(nombreApellidosUser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fotoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(navegador)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sessionItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sessionItemMousePressed
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_sessionItemMousePressed

    private void userItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userItemMousePressed
        usuarioDialog.setVisible(true);
        String user = datosUsuario[0];
        dniLabel.setText("DNI:  " + datosUsuario[0]);
        nombreLabel.setText("Nombre:  " + datosUsuario[1]);
        apellidoLabel.setText("Apellido:  " + datosUsuario[2]);
        penalizacionLabel.setText("Penalización:  " + datosUsuario[3]);
        emaillabel.setText("E-mail:  " + datosUsuario[4]);
        fotoUserLabel.setIcon(fotoUser.getIcon());
    }//GEN-LAST:event_userItemMousePressed

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
        alquilarPeliculas.setVisible(true);
    }//GEN-LAST:event_jMenuItem1MousePressed

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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Buscador;
    private javax.swing.JPanel Cine;
    private javax.swing.JDialog alquilarPeliculas;
    public static javax.swing.JLabel anioPublicacion;
    private javax.swing.JLabel apellidoLabel;
    public static javax.swing.JLabel calificacionPeli;
    public static javax.swing.JLabel descripcionPeli;
    private javax.swing.JLabel dniLabel;
    private javax.swing.JLabel emaillabel;
    public static javax.swing.JLabel fotoUser;
    private javax.swing.JLabel fotoUserLabel;
    public static javax.swing.JLabel genero;
    public static javax.swing.JLabel imagenPeli;
    public static javax.swing.JLabel imdbPeli;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    public static javax.swing.JLabel movDre;
    public static javax.swing.JLabel movIzq;
    private javax.swing.JTabbedPane navegador;
    public static javax.swing.JLabel nombreApellidosUser;
    private javax.swing.JLabel nombreLabel;
    public static javax.swing.JLabel paisPeli;
    private javax.swing.JDialog peliculas;
    private javax.swing.JPanel pelisPanel;
    private javax.swing.JLabel penalizacionLabel;
    private javax.swing.JMenuItem sessionItem;
    public static javax.swing.JLabel tituloPeli;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JMenuItem userItem;
    private javax.swing.JDialog usuarioDialog;
    // End of variables declaration//GEN-END:variables
}
