package othello;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField loginInput;
    private JPasswordField passInput;
    private JButton identifier;
    private JButton inscrire;

    public static void execute() {
        Login frame = new Login();
        frame.setVisible(true);
    }

    public Login() {
        setResizable(false);

        setTitle("OTHELLO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 343);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 204, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setLocationRelativeTo(null);
        contentPane.setLayout(null);

        JLabel lblTitel = new JLabel("Othello");
        lblTitel.setForeground(new Color(0, 0, 0));
        lblTitel.setBounds(5, 19, 424, 62);
        lblTitel.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitel.setFont(new Font("Dialog", Font.PLAIN, 30));
        contentPane.add(lblTitel);

        JPanel pnlLogin = new JPanel();
        pnlLogin.setBackground(new Color(51, 204, 255));
        pnlLogin.setBounds(5, 67, 424, 168);
        contentPane.add(pnlLogin);
        pnlLogin.setLayout(null);

        loginInput = new JTextField() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 17, 17);
            }
        };
        loginInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnsEnable();
            }
        });
        loginInput.setBounds(57, 46, 305, 35);
        loginInput.setFont(new Font("Dialog", Font.PLAIN, 12));
        pnlLogin.add(loginInput);

        passInput = new JPasswordField() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 17, 17);
            }
        };
        passInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnsEnable();
            }
        });
        passInput.setBounds(57, 122, 305, 35);
        pnlLogin.add(passInput);

        JLabel lblLgn = new JLabel("Nom d'utilisateur :");
        lblLgn.setBounds(59, 17, 169, 28);
        lblLgn.setFont(new Font("Dialog", Font.PLAIN, 17));
        pnlLogin.add(lblLgn);

        JLabel lblPwd = new JLabel("Mot de passe :");
        lblPwd.setBounds(58, 93, 170, 28);
        lblPwd.setFont(new Font("Dialog", Font.PLAIN, 17));
        pnlLogin.add(lblPwd);

        JFrame instance = this;
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));

        identifier = new JButton("s'identifier");
        identifier.setEnabled(false);
        identifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dilg = new JDialog();
                User user = new User(loginInput.getText(), passInput.getText());
                if (user.login()) {
                    if (user.getParameters().isHasConfiguration()) {
                        dilg = continuerOuRejouer(user, instance);
                        dilg.setVisible(true);
                    } else {
                        dilg.dispose();
                        instance.dispose();
                        PreparationJeu.execute(user);
                    }
                } else {
                    JOptionPane.showMessageDialog(instance, "Cet utilisateur n'existe pas", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        identifier.setBackground(new Color(255, 204, 51));
        identifier.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        identifier.setFocusable(false);
        identifier.setBounds(220, 247, 90, 28);
        contentPane.add(identifier);
        identifier.setFont(new Font("Dialog", Font.PLAIN, 13));

        inscrire = new JButton("s'inscrire");
        inscrire.setEnabled(false);
        inscrire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user = new User(loginInput.getText(), passInput.getText());
                if (user.create()) {
                    ImageIcon icon = new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/good.png"));
                    JOptionPane.showMessageDialog(instance, "utilisateur créé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE, icon);
                } else {
                    JOptionPane.showMessageDialog(instance, "Ce nom d'utilisateur existe déjà", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        inscrire.setBackground(new Color(255, 204, 51));
        inscrire.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        inscrire.setFocusable(false);
        inscrire.setBounds(118, 247, 90, 28);
        contentPane.add(inscrire);
        inscrire.setFont(new Font("Dialog", Font.PLAIN, 13));

    }

    private void btnsEnable() {
        if (loginInput.getText().trim().equals("") || passInput.getText().trim().equals("")) {
            identifier.setEnabled(false);
            inscrire.setEnabled(false);
        } else {
            identifier.setEnabled(true);
            inscrire.setEnabled(true);
        }
    }

    private JDialog continuerOuRejouer(User user, JFrame instance) {
        JDialog dilg = new JDialog(instance, true);
        dilg.setResizable(false);
        dilg.setBounds(new Rectangle(0, 0, 185, 135));
        dilg.setLocationRelativeTo(instance);
        dilg.getContentPane().setBackground(new Color(255, 204, 51));
        dilg.getContentPane().setLayout(null);

        JButton btnContinuer = new JButton("Continuer");
        btnContinuer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dilg.dispose();
                instance.dispose();
                Othello.execute(user, null);
            }
        });
        btnContinuer.setBounds(5, 7, 160, 40);
        btnContinuer.setBackground(new Color(51, 204, 255));
        btnContinuer.setFont(new Font("Dialog", Font.PLAIN, 13));
        btnContinuer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dilg.getContentPane().add(btnContinuer);

        JButton btnRejouer = new JButton("Rejouer");
        btnRejouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dilg.dispose();
                instance.dispose();
                PreparationJeu.execute(user);
            }
        });
        btnRejouer.setBounds(5, 50, 160, 40);
        btnRejouer.setBackground(new Color(51, 204, 255));
        btnContinuer.setFont(new Font("Dialog", Font.PLAIN, 13));
        btnRejouer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dilg.getContentPane().add(btnRejouer);

        return dilg;
    }
}