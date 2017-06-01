package codigo;

import static codigo.VentanaInicio.frame;
import java.awt.Dimension;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

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
    String[] pelisBuscarArray = new String[8];
    ArrayList<String[]> pelisBuscarArrayList = new ArrayList();
    int anchoPeli = 95;
    int altoPeli = 160;
    int contador = 0;
    JLabel label = new JLabel();
    int contadorBuscador = 0;
    
    private void usuarioMenu() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.usuarios");
                while (resultado.next()) {
                    datosUsuario[0] = resultado.getString("DNI");
                    datosUsuario[1] = resultado.getString("Nombre");
                    datosUsuario[2] = resultado.getString("Apellido");
                    datosUsuario[3] = resultado.getString("Penalizacion");
                    datosUsuario[4] = resultado.getString("email");
                    datosUsuarioList.add(datosUsuario);
                }
            resultado.first();
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

            //Realizo la conexcion
            estado = conexion.createStatement();

            //Realizo la consulta
            resultado = estado.executeQuery("SELECT * FROM videoclub.peliculas");
            

            while (resultado.next()) {
                String[] aux = new String[8];
                aux[0] = resultado.getString("id_pelicula");
                aux[1] = resultado.getString("titulo");
                aux[2] = resultado.getString("año");
                aux[3] = resultado.getString("pais");
                aux[4] = resultado.getString("genero");
                aux[5] = resultado.getString("imdb");
                aux[6] = resultado.getString("clasificacion_imdb");
                aux[7] = resultado.getString("resumen");
                datosPeliculasList.add(aux);
            }            
        } catch (ClassNotFoundException ex) {
            System.out.println("NO SE HA ENCONTRADO EL DRIVER");
        } catch (SQLException ex) {

            System.out.println("NO SE HA PODIDO CONECTAR A LA BBDD");
        }
    }
    
    
    public void peliDatos(String numPeli) {
        String nomPeli="";
        int id_pelicula;
        for(id_pelicula = 0; id_pelicula < datosPeliculasList.size(); id_pelicula++){
            if(numPeli.equals(datosPeliculasList.get(id_pelicula)[0])){
                nomPeli=numPeli;
                break;
            }
        }
        if(!nomPeli.equals("")){
                //Genera el nombre de la imagen
            for (int i = nomPeli.length(); i < 6; i++) {
                nomPeli = "0" + nomPeli;
            }

            Image foto = (new ImageIcon(new ImageIcon(getClass().getResource("/caratulas/"+nomPeli+".jpg"))
                    .getImage().getScaledInstance(175, 210, Image.SCALE_DEFAULT))).getImage();
            ImageIcon caratula = new ImageIcon(foto);

            imagenPeli.setIcon(caratula);
            imagenPeli.updateUI();

            tituloPeli.setText("Titulo: " + datosPeliculasList.get(id_pelicula)[1]);
            anioPublicacion.setText("Año: " + datosPeliculasList.get(id_pelicula)[2]);
            paisPeli.setText("País: " + datosPeliculasList.get(id_pelicula)[3]);
            genero.setText("Género: " + datosPeliculasList.get(id_pelicula)[4]);
            imdbPeli.setText("IMDB: " + datosPeliculasList.get(id_pelicula)[5]);
            calificacionPeli.setText("Calificación: " + datosPeliculasList.get(id_pelicula)[6]);
            descripcionpeli.setLineWrap(true);
            descripcionpeli.setText(datosPeliculasList.get(id_pelicula)[7]);
        }
    }

    public ImageIcon adaptaCaratulas(String nomCaratula) {
        int anchoCaratula = 80;
        System.out.println(nomCaratula);
        for (int i = nomCaratula.length(); i < 6; i++) {
            nomCaratula = "0" + nomCaratula;
        }
        Image foto = (new ImageIcon(new ImageIcon(getClass().getResource("/caratulas/" + nomCaratula + ".jpg"))
                .getImage().getScaledInstance(anchoCaratula, 120, Image.SCALE_DEFAULT))).getImage();
        ImageIcon caratula = new ImageIcon(foto);
        return caratula;
    }
    
    public void generaCaratula(int _posX){
        String nomCaratula;
        int posX= _posX;
        int anchoCaratula =80;
        int posVertical=1;
        
        
        while(contador < datosPeliculasList.size()){
            for(int u = 0; u < 14; u++){
                if(contador < datosPeliculasList.size()){
                    label = new JLabel();
                    nomCaratula= datosPeliculasList.get(contador)[0];
                    label.setBounds(posX, posVertical,80,120);
                    label.setIcon(adaptaCaratulas(nomCaratula));
                    label.setText(datosPeliculasList.get(contador)[0]);
                    label.setVisible(true);
                    pelisPanel.add(label);
                    pelisPanel.updateUI();
                    posX+= anchoCaratula+15;
                }
                contador++;
            }
            posVertical+= 140+15;
            posX=_posX;   
        }        
    }
    
    public void pelisBuscador() {
        String peliBuscar = txtBuscar.getText();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");

            //Realizo la conexcion
            estado = conexion.createStatement();

            //Realizo la consulta
            resultado = estado.executeQuery("SELECT * FROM videoclub.peliculas WHERE titulo LIKE '%"+peliBuscar+"%'");
            

            while (resultado.next()) {
                String[] aux = new String[8];
                pelisBuscarArray[0] = resultado.getString("id_pelicula");
                pelisBuscarArray[1] = resultado.getString("titulo");
                pelisBuscarArray[2] = resultado.getString("año");
                pelisBuscarArray[3] = resultado.getString("pais");
                pelisBuscarArray[4] = resultado.getString("genero");
                pelisBuscarArray[5] = resultado.getString("imdb");
                pelisBuscarArray[6] = resultado.getString("clasificacion_imdb");
                pelisBuscarArray[7] = resultado.getString("resumen");
                pelisBuscarArrayList.add(pelisBuscarArray);
            }            
        } catch (ClassNotFoundException ex) {
            System.out.println("NO SE HA ENCONTRADO EL DRIVER");
        } catch (SQLException ex) {

            System.out.println("NO SE HA PODIDO CONECTAR A LA BBDD");
        }
    }
     //..
    /*
    Este es el metodo para imprimir las caratulas de las pelis que he buscado
    */
    public void generaCaratulaBuscador(int _posX){
        String peliBuscar = txtBuscar.getText();
        String nomCaratula;
        int posX = _posX;
        int anchoCaratula = 80;
        int posVertical = 1;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            //Realizo la conexcion
            estado = conexion.createStatement();
            //Realizo la consulta
            resultado = estado.executeQuery("SELECT * FROM videoclub.peliculas WHERE titulo LIKE '%"+peliBuscar+"%'");
            while (resultado.next()) {
                pelisBuscarArray[0] = resultado.getString("id_pelicula");
                pelisBuscarArrayList.add(pelisBuscarArray);
                while (contadorBuscador < pelisBuscarArrayList.size()) {
                    for (int u = 0; u < pelisBuscarArrayList.size(); u++) {
                        if (contadorBuscador < pelisBuscarArrayList.size()) {
                            label = new JLabel();
                            nomCaratula = pelisBuscarArrayList.get(contadorBuscador)[0];
                            label.setBounds(posX, posVertical, 80, 120);
                            label.setIcon(adaptaCaratulas(nomCaratula));
                            label.setText(pelisBuscarArrayList.get(contadorBuscador)[0]);
                            label.setVisible(true);
                            peliPanelBuscador.add(label);
                            peliPanelBuscador.updateUI();
                            posX += anchoCaratula + 15;
                        }
                        contadorBuscador++;
                    }
                    posVertical += 110 + 15;
                    posX = _posX;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("NO SE HA ENCONTRADO EL DRIVER");
        } catch (SQLException ex) {

            System.out.println("NO SE HA PODIDO CONECTAR A LA BBDD");
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
        descripcionpeli.disable();
        peliculas.setIconImage(new ImageIcon(getClass().getResource("/img/claqueta1.png")).getImage());
        peliculas.setTitle("Videoclub Malasaña");
        usuarioDialog.setSize(600, 500);
        usuarioDialog.setLocation(400, 90);
        usuarioDialog.setTitle("Perfil - Videoclub Malasaña");
        alquilarPeliculas.setSize(550, 350);
        alquilarPeliculas.setTitle("Alquilar - Videoclub Malasaña");
        alquilarPeliculas.setLocation(150, 90);
        SpinnerNumberModel spiner = new SpinnerNumberModel();
        spiner.setMaximum(10);
        spiner.setMinimum(1);
        spinnerEjemplar.setModel(spiner);
        spinnerEjemplar.setValue(1);
        pelicula();
        usuarioMenu();
        generaCaratula(0);
        pelisBuscador();
        autocompletar();
        fechaprestamo.setText(fechaActual());
        fechaprestamo.disable();
    }
    
    public void autocompletar() {
        TextAutoCompleter texto = new TextAutoCompleter(textoalquilar);
        TextAutoCompleter texto2 = new TextAutoCompleter(txtBuscar);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT titulo FROM videoclub.peliculas");
            while(resultado.next()){
                texto.addItem(resultado.getString("titulo"));
                texto2.addItem(resultado.getString("titulo"));
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BBDD");
        }
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcionpeli = new javax.swing.JTextArea();
        usuarioDialog = new javax.swing.JDialog();
        dniLabel = new javax.swing.JLabel();
        nombreLabel = new javax.swing.JLabel();
        apellidoLabel = new javax.swing.JLabel();
        penalizacionLabel = new javax.swing.JLabel();
        emaillabel = new javax.swing.JLabel();
        fotoUserLabel = new javax.swing.JLabel();
        alquilarPeliculas = new javax.swing.JDialog();
        textoalquilar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        DNIUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        alquilarPelicula = new javax.swing.JButton();
        spinnerEjemplar = new javax.swing.JSpinner();
        fechaDevolucion = new javax.swing.JTextField();
        fechaprestamo = new javax.swing.JTextField();
        nombreApellidosUser = new javax.swing.JLabel();
        fotoUser = new javax.swing.JLabel();
        navegador = new javax.swing.JTabbedPane();
        Cine = new javax.swing.JPanel();
        pelisPanel = new javax.swing.JPanel();
        Buscador = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        botonBuscador = new javax.swing.JButton();
        peliPanelBuscador = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        userItem = new javax.swing.JMenuItem();
        sessionItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        tituloPeli.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Resumen: ");

        descripcionpeli.setColumns(20);
        descripcionpeli.setRows(5);
        jScrollPane1.setViewportView(descripcionpeli);

        javax.swing.GroupLayout peliculasLayout = new javax.swing.GroupLayout(peliculas.getContentPane());
        peliculas.getContentPane().setLayout(peliculasLayout);
        peliculasLayout.setHorizontalGroup(
            peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peliculasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(peliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
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
                        .addComponent(imagenPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(peliculasLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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

        jLabel1.setText("Números Ejemplar: ");

        jLabel3.setText("Película: ");

        jLabel4.setText("Código Usuario: ");

        jLabel5.setText("Fecha Prestamo:");

        jLabel6.setText("Fecha Devolución:");

        alquilarPelicula.setText("Alquilar");
        alquilarPelicula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                alquilarPeliculaMousePressed(evt);
            }
        });

        spinnerEjemplar.setValue(1);

        javax.swing.GroupLayout alquilarPeliculasLayout = new javax.swing.GroupLayout(alquilarPeliculas.getContentPane());
        alquilarPeliculas.getContentPane().setLayout(alquilarPeliculasLayout);
        alquilarPeliculasLayout.setHorizontalGroup(
            alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addComponent(alquilarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                        .addComponent(textoalquilar, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerEjemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                        .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(DNIUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                        .addGap(168, 168, 168)
                        .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(fechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(483, Short.MAX_VALUE)))
        );
        alquilarPeliculasLayout.setVerticalGroup(
            alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoalquilar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerEjemplar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DNIUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(alquilarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(alquilarPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(alquilarPeliculasLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(320, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nombreApellidosUser.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N

        pelisPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pelisPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pelisPanelLayout = new javax.swing.GroupLayout(pelisPanel);
        pelisPanel.setLayout(pelisPanelLayout);
        pelisPanelLayout.setHorizontalGroup(
            pelisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1050, Short.MAX_VALUE)
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
                .addComponent(pelisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        CineLayout.setVerticalGroup(
            CineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pelisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        navegador.addTab("Cine", Cine);

        botonBuscador.setText("Buscar");
        botonBuscador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                botonBuscadorMousePressed(evt);
            }
        });

        javax.swing.GroupLayout peliPanelBuscadorLayout = new javax.swing.GroupLayout(peliPanelBuscador);
        peliPanelBuscador.setLayout(peliPanelBuscadorLayout);
        peliPanelBuscadorLayout.setHorizontalGroup(
            peliPanelBuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        peliPanelBuscadorLayout.setVerticalGroup(
            peliPanelBuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BuscadorLayout = new javax.swing.GroupLayout(Buscador);
        Buscador.setLayout(BuscadorLayout);
        BuscadorLayout.setHorizontalGroup(
            BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peliPanelBuscador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BuscadorLayout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 773, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BuscadorLayout.setVerticalGroup(
            BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BuscadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(peliPanelBuscador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void pelisPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelisPanelMousePressed
        Point puntoId = new Point();
        puntoId.x = evt.getX();
        puntoId.y = evt.getY();
        
        if(pelisPanel.getComponentAt(puntoId)instanceof JLabel){
            label = (JLabel) pelisPanel.getComponentAt(puntoId);
            labelMousePressed(label);
        }
    }//GEN-LAST:event_pelisPanelMousePressed

    private void botonBuscadorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonBuscadorMousePressed
        generaCaratulaBuscador(0);
    }//GEN-LAST:event_botonBuscadorMousePressed

    private void alquilarPeliculaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_alquilarPeliculaMousePressed

       /*
        Me falta hacer la consulta para convertir el nombre de la pelicula en su ID y pasar el 
        jTextField a formato fecha
        */
        String pelicula = textoalquilar.getText();
        System.out.println(pelicula);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.56.101/videoclub", "root", "admin.1234");
            estado = conexion.createStatement();
            resultado = estado.executeQuery("SELECT * FROM videoclub.peliculas WHERE titulo = '" + pelicula + "'");
            while (resultado.next()) {
                int peliculaId = resultado.getInt("id_pelicula");
                int ejemplares = Integer.parseInt(String.valueOf(spinnerEjemplar.getValue()));
                int usuarioId = Integer.parseInt(DNIUsuario.getText());
                String fechaInicio = fechaprestamo.getText(); 
                String fechaFin = fechaDevolucion.getText();  
                //comprobaciones
                System.out.println("idPeli "+peliculaId);
                System.out.println("Cantidad "+ejemplares);
                System.out.println("usuario"+usuarioId);
                System.out.println("fechaInicio "+fechaInicio);
                System.out.println("fechaFin "+fechaFin);
                
                System.out.println("Prepara los datos para la insecion en el siguiente paso");
                // Indicamos que comience la actualización de la tabla en nuestra base de datos
                estado.executeUpdate("INSERT INTO videoclub.prestamos (id_pelicula, NumeroEjemplar, DNIUsuario, FechaPrestamo, FechaDevolucion)"
                        + " VALUES ('"+peliculaId+"','"+ejemplares+"','"+usuarioId+"','"+fechaInicio+"','"+fechaFin+"')");
                System.out.println("Realizo la insercion con exito <3 <3 <3");
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha encontrado el Driver de la BBDD");
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BBDD");
        }
        alquilarPeliculas.setVisible(false);
    }//GEN-LAST:event_alquilarPeliculaMousePressed

    public void labelMousePressed(JLabel caratula){
        peliDatos(caratula.getText());
        peliculas.setVisible(true);
    }    
    
    public static String fechaActual() {
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        return formatoFecha.format(fecha);
    }
    
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
    public javax.swing.JTextField DNIUsuario;
    private javax.swing.JButton alquilarPelicula;
    private javax.swing.JDialog alquilarPeliculas;
    public static javax.swing.JLabel anioPublicacion;
    private javax.swing.JLabel apellidoLabel;
    private javax.swing.JButton botonBuscador;
    public static javax.swing.JLabel calificacionPeli;
    private javax.swing.JTextArea descripcionpeli;
    private javax.swing.JLabel dniLabel;
    private javax.swing.JLabel emaillabel;
    private javax.swing.JTextField fechaDevolucion;
    public javax.swing.JTextField fechaprestamo;
    public static javax.swing.JLabel fotoUser;
    private javax.swing.JLabel fotoUserLabel;
    public static javax.swing.JLabel genero;
    public static javax.swing.JLabel imagenPeli;
    public static javax.swing.JLabel imdbPeli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane navegador;
    public static javax.swing.JLabel nombreApellidosUser;
    private javax.swing.JLabel nombreLabel;
    public static javax.swing.JLabel paisPeli;
    private javax.swing.JPanel peliPanelBuscador;
    private javax.swing.JDialog peliculas;
    private javax.swing.JPanel pelisPanel;
    private javax.swing.JLabel penalizacionLabel;
    private javax.swing.JMenuItem sessionItem;
    public javax.swing.JSpinner spinnerEjemplar;
    public javax.swing.JTextField textoalquilar;
    public static javax.swing.JLabel tituloPeli;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JMenuItem userItem;
    public javax.swing.JDialog usuarioDialog;
    // End of variables declaration//GEN-END:variables
}
