import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class EggTimer extends JFrame {

    private JPanel panel1;
    private JButton huevoDuroButton;
    private JButton medioCocidoButton;
    private JButton pasadoPorAguaButton;
    private JButton GOOButton;
    private JButton STOPButton;
    private JLabel labelTemporizador;
    private JLabel lalbelDuro;
    private JLabel labelCocido;
    private JLabel labelPorAgua;

    //Constantes para los Tiempos
    private static final int TIEMPO_HUEVODURO = 600;
    private static final int TIEMPO_HUEVOCOCIDO = 360;
    private static final int TIEMPO_HUEVOPASADOPORAGUA = 180;

    // Atributos específicos para el tiempo
    private Timer timer;
    private int segundosQueQuedan = 0;

    // Atributo para el sonido de alarma
    private Clip clip;

    public EggTimer() {
        setTitle("Egg Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setBounds(550, 200, 450, 400);

        //Cargar las imágenes para los labels
        ImageIcon huevoDuroIcon = new ImageIcon("imagenes/4_95px.png");
        ImageIcon medioCocidoIcon = new ImageIcon("imagenes/1_150px.png");
        ImageIcon pasadoPorAguaIcon = new ImageIcon("imagenes/3_95px.png");

        //Asignar las imágenes a los JLabel correspondientes
        lalbelDuro.setIcon(huevoDuroIcon);
        labelCocido.setIcon(medioCocidoIcon);
        labelPorAgua.setIcon(pasadoPorAguaIcon);

        //Acciones de los Botones
        huevoDuroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(TIEMPO_HUEVODURO); //10 minutos
            }
        });
        medioCocidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(TIEMPO_HUEVOCOCIDO);  //6 minutos
            }
        });
        pasadoPorAguaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(TIEMPO_HUEVOPASADOPORAGUA);  //3 minutos
            }
        });

        //Botones de comienzo y fin
        GOOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empezarTiempo();
            }
        });
        STOPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                    clip.close();
                }
            }
        });
    }

    //Métodos

    /**
     * Establecer el tiempo según la opción del botón
     * @param segundos
     */
    private void establecerTiempo(int segundos) {
        segundosQueQuedan = segundos;
        actualizarLabel();
    }

    /**
     * Empezar la cuenta regresiva del crono
     */
    private void empezarTiempo() {
        if (timer != null && timer.isRunning()) return;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (segundosQueQuedan > 0) {
                    segundosQueQuedan--;
                    actualizarLabel();
                } else {
                    timer.stop();
                    labelTemporizador.setText("¡Tiempo!");

                    //Sonido de alarma en bucle
                    try {
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sonidos/kitchen-timer-33043.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(audioIn);
                        //Función para el sonido en bucle
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Actualizar label del crono
     */
    private void actualizarLabel() {
        int minutos = segundosQueQuedan / 60;
        int segundos = segundosQueQuedan % 60;

        labelTemporizador.setText(String.format("%02d:%02d", minutos, segundos));
    }
}
