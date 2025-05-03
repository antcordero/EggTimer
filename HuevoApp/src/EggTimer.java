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

    //atributos específicos para el tiempo
    private Timer timer;
    private int segundosQueQuedan = 0;

    //atributo para el sonido de alarma
    private Clip clip;

    public EggTimer() {
        setTitle("Egg Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        //setLocationRelativeTo(null);
        //setSize(400, 250);
        setBounds(550, 200, 450, 250);

        //Acciones de los Botones
        //Botones de opciones
        huevoDuroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(600);  //10 minutos
            }
        });
        medioCocidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(360);  //6 minutos
            }
        });
        pasadoPorAguaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerTiempo(180);  //3 minutos
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
     * @param seconds
     */
    private void establecerTiempo(int seconds) {
        segundosQueQuedan = seconds;
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
                        //función para el sonido en bucle
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
