package othello;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import rechercheAdversiale.OthelloAlgo;
import rechercheAdversiale.OthelloMove;
import rechercheAdversiale.OthelloPosition;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Cursor;

public class PreparationJeu extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    public static void execute(User user) {
        PreparationJeu frame = new PreparationJeu(user);
        frame.setVisible(true);
    }
    /**
     * @wbp.nonvisual location=262,109
     */
    private final ButtonGroup typePartie = new ButtonGroup();

    public PreparationJeu(User user) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 601, 475);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Préparation du jeu");
        contentPane = new JPanel();
        contentPane.setForeground(new Color(0, 0, 0));
        contentPane.setBackground(new Color(255, 250, 205));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Type de partie :");
        lblNewLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setBounds(59, 26, 170, 47);
        contentPane.add(lblNewLabel);

        JRadioButton radioHO = new JRadioButton("Humain - Ordinateur");
        radioHO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radioHO.setHorizontalAlignment(SwingConstants.LEFT);
        radioHO.setFocusable(false);
        radioHO.setSelected(true);
        radioHO.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/radio_Off.png")));
        radioHO.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/radio_On.png")));
        radioHO.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        radioHO.setBounds(56, 85, 232, 27);
        contentPane.add(radioHO);
        typePartie.add(radioHO);

        JLabel lblCommencerEnPremier = new JLabel("Commencer en premier : ");
        lblCommencerEnPremier.setHorizontalAlignment(SwingConstants.LEFT);
        lblCommencerEnPremier.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        lblCommencerEnPremier.setBounds(59, 129, 245, 47);
        contentPane.add(lblCommencerEnPremier);

        JCheckBox chkbxIsFirst = new JCheckBox("");
        chkbxIsFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (radioHO.isSelected() == false) {
                    chkbxIsFirst.setSelected(true);
                }
            }
        });
        chkbxIsFirst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chkbxIsFirst.setSelected(true);
        chkbxIsFirst.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/checked.png")));
        chkbxIsFirst.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/unchecked.png")));
        chkbxIsFirst.setFont(new Font("SansSerif", Font.PLAIN, 19));
        chkbxIsFirst.setBounds(326, 140, 33, 33);
        contentPane.add(chkbxIsFirst);

        JRadioButton radioHH = new JRadioButton("Humain - Humain");
        radioHH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkbxIsFirst.setSelected(true);
            }
        });
        radioHH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radioHH.setFocusable(false);
        radioHH.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/radio_Off.png")));
        radioHH.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/radio_On.png")));
        radioHH.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        radioHH.setBounds(326, 85, 214, 27);
        contentPane.add(radioHH);
        typePartie.add(radioHH);

        JLabel lblHeuristiques = new JLabel("Heuristiques :");
        lblHeuristiques.setHorizontalAlignment(SwingConstants.LEFT);
        lblHeuristiques.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        lblHeuristiques.setBounds(59, 187, 170, 47);
        contentPane.add(lblHeuristiques);

        JCheckBox hrCoins = new JCheckBox("Coins");
        hrCoins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hrCoins.setHorizontalAlignment(SwingConstants.LEFT);
        hrCoins.setFocusable(false);
        hrCoins.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/checked.png")));
        hrCoins.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/unchecked.png")));
        hrCoins.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        hrCoins.setBounds(52, 241, 91, 23);
        contentPane.add(hrCoins);

        JCheckBox hrCotes = new JCheckBox("Cot\u00E9s");
        hrCotes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hrCotes.setFocusable(false);
        hrCotes.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/checked.png")));
        hrCotes.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/unchecked.png")));
        hrCotes.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        hrCotes.setBounds(170, 241, 91, 23);
        contentPane.add(hrCotes);

        JCheckBox hrPions = new JCheckBox("Pions");
        hrPions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hrPions.setFocusable(false);
        hrPions.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/checked.png")));
        hrPions.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/unchecked.png")));
        hrPions.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        hrPions.setBounds(294, 241, 91, 23);
        contentPane.add(hrPions);

        JCheckBox hrGagnante = new JCheckBox("Gagnante");
        hrGagnante.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hrGagnante.setFocusable(false);
        hrGagnante.setSelectedIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/checked.png")));
        hrGagnante.setIcon(new ImageIcon(PreparationJeu.class.getResource("/othello/imgs/unchecked.png")));
        hrGagnante.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        hrGagnante.setBounds(410, 241, 130, 23);
        contentPane.add(hrGagnante);

        JLabel lblProfondeur = new JLabel("Profondeur :");
        lblProfondeur.setHorizontalAlignment(SwingConstants.LEFT);
        lblProfondeur.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 21));
        lblProfondeur.setBounds(59, 283, 138, 47);
        contentPane.add(lblProfondeur);

        Integer cbbxval[] = {2, 4, 6};
        JComboBox<Integer> maxDepth = new JComboBox<>(cbbxval);
        maxDepth.setBounds(188, 292, 105, 33);
        maxDepth.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        maxDepth.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(maxDepth);

        JFrame instance = this;

        JButton valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user.getParameters().setWithHuman(radioHH.isSelected());
                user.getParameters().setFirst(chkbxIsFirst.isSelected());
                user.getParameters().setHeuristiqueCoins(hrCoins.isSelected());
                user.getParameters().setHeuristiqueCotes(hrCotes.isSelected());
                user.getParameters().setHeuristiquePions(hrPions.isSelected());
                user.getParameters().setHeuristiqueGagnante(hrGagnante.isSelected());
                user.getParameters().setMaxDepth((int) maxDepth.getSelectedItem());
                user.getParameters().setHasConfiguration(true);
                int[][] brd = new int[8][8];
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        brd[i][j] = OthelloPosition.BLANK;
                    }
                }
                brd[3][3] = OthelloPosition.WHITE;
                brd[3][4] = OthelloPosition.BLACK;
                brd[4][3] = OthelloPosition.BLACK;
                brd[4][4] = OthelloPosition.WHITE;
                user.setOPosition(new OthelloPosition(brd));
                if (chkbxIsFirst.isSelected() == false && radioHO.isSelected()) {
                    OthelloMove move = new OthelloMove(-1, -1);
                    OthelloAlgo oA = new OthelloAlgo(user.getParameters());
                    oA.playGame(user, move);
                }
                user.update();
                instance.dispose();
                Othello.execute(user, null);
            }
        });
        valider.setForeground(new Color(255, 255, 255));
        valider.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 19));
        valider.setFocusPainted(false);
        valider.setFocusable(false);
        valider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        valider.setBackground(new Color(51, 204, 255));
        valider.setBounds(250, 369, 97, 33);
        contentPane.add(valider);
    }
}