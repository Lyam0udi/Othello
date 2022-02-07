package othello;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}