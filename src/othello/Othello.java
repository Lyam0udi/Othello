package othello;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import rechercheAdversiale.GameSearch;
import rechercheAdversiale.OthelloAlgo;
import rechercheAdversiale.OthelloMove;
import rechercheAdversiale.OthelloPosition;
import rechercheAdversiale.Position;

import java.awt.BasicStroke;
import java.awt.BorderLayout;

public class Othello extends JFrame {

    private static final long serialVersionUID = 1L;

    private static JPanel contentPane;
    private JPanel pnlJeu;
    private JButton annuler;
    public static JFrame instance;
    private OthelloPosition OPosition;

    public static void execute(User user, Point p) {
        Othello frame = new Othello(user);
        if (p == null) {
            frame.setLocationRelativeTo(null);
        } else {
            frame.setLocation(p);
        }
        frame.setVisible(true);
    }

    public Othello(User user) {
        instance = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 525, 635);
        setLocationRelativeTo(null);
        setTitle("Othello");
        this.setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(102, 204, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton indication = new JButton("Indication");
        indication.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OthelloAlgo oA = new OthelloAlgo(user.getParameters());
                Vector<?> v = oA.alphaBeta(0, user.getOPosition(), GameSearch.HUMAN);
                OthelloMove m = ((OthelloPosition) (Position) v.elementAt(1)).getoMove();
                JOptionPane.showMessageDialog(instance, "Position (" + (m.getxMoveIndex() + 1) + ", " + (m.getyMoveIndex() + 1) + ")");
            }
        });
        indication.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        indication.setBorderPainted(false);
        indication.setBackground(new Color(255, 204, 51));
        indication.setBounds(143, 6, 101, 38);
        contentPane.add(indication);

        annuler = new JButton("Annuler");
        annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user.setOPosition(OPosition);
                if (user.getParameters().isWithHuman()) {
                    user.getParameters().setFirst(!user.getParameters().isFirst());
                }
                user.update();
                annuler.setEnabled(false);
            }
        });
        annuler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        annuler.setEnabled(false);
        annuler.setBorderPainted(false);
        annuler.setBackground(new Color(102, 204, 102));
        annuler.setBounds(143, 48, 101, 38);
        contentPane.add(annuler);

        JButton rejouer = new JButton("Rejouer");
        rejouer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instance.dispose();
                PreparationJeu.execute(user);
            }
        });
        rejouer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rejouer.setBorderPainted(false);
        rejouer.setBackground(new Color(0, 153, 255));
        rejouer.setBounds(248, 6, 101, 38);
        contentPane.add(rejouer);

        JButton deconnecter = new JButton("D\u00E9connecter");
        deconnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user.logout();
                instance.dispose();
                Login.execute();
            }
        });
        deconnecter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deconnecter.setBorderPainted(false);
        deconnecter.setBackground(new Color(255, 102, 51));
        deconnecter.setBounds(248, 48, 101, 38);
        contentPane.add(deconnecter);

        JPanel pnlRows = new JPanel();
        pnlRows.setBounds(0, 119, 32, 471);
        pnlRows.setBackground(new Color(102, 204, 255));
        pnlRows.setLayout(new GridLayout(8, 1, 0, 0));
        contentPane.add(pnlRows);

        for (int i = 0; i < 8; i++) {
            JLabel lbl = new JLabel("" + (i + 1));
            lbl.setForeground(Color.BLACK);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setVerticalAlignment(SwingConstants.CENTER);
            pnlRows.add(lbl);
        }

        JPanel pnlCols = new JPanel();
        pnlCols.setBounds(32, 92, 471, 26);
        pnlCols.setBackground(new Color(102, 204, 255));
        pnlCols.setLayout(new GridLayout(1, 8, 0, 0));
        contentPane.add(pnlCols);

        for (int i = 0; i < 8; i++) {
            JLabel lbl = new JLabel("" + (i + 1));
            lbl.setForeground(Color.BLACK);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setVerticalAlignment(SwingConstants.CENTER);
            pnlCols.add(lbl);
        }

        pnlJeu = new JPanel();
        pnlJeu.setBackground(new Color(102, 204, 255));
        pnlJeu.setBounds(32, 119, 471, 471);
        contentPane.add(pnlJeu);
        pnlJeu.setLayout(new BorderLayout(0, 0));
        pnlJeu.add(refreshedPnl(user));

    }

    private JPanel refreshedPnl(User user) {
        JPanel pnl = new JPanel();
        pnl.setBackground(new Color(102, 204, 255));
        pnl.setLayout(new GridLayout(8, 8, 0, 0));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = i, y = j;
                OthelloMove move = new OthelloMove(i, j);
                OthelloAlgo oA = new OthelloAlgo(user.getParameters());
                JLabel lbl = new JLabel() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        if (user.getOPosition().getBoard()[x][y] == OthelloPosition.BLACK) {
                            g2.setColor(Color.BLACK);
                            g2.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                        if (user.getOPosition().getBoard()[x][y] == OthelloPosition.WHITE) {
                            g2.setColor(Color.WHITE);
                            g2.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                        if (user.getOPosition().getBoard()[x][y] == OthelloPosition.BLANK) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        }
                        if (oA.makeMove(user.getOPosition(), true, move) != null && user.getParameters().isMyTurn()) {
                            g2.setColor(Color.YELLOW);
                            g2.setStroke(new BasicStroke(2));
                            g2.drawOval(6, 6, this.getWidth() - 12, this.getHeight() - 12);
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        }
                        pnl.repaint();
                    }
                };
                lbl.setBorder(new LineBorder(new Color(0, 0, 0)));
                lbl.setBackground(new Color(102, 204, 255));
                lbl.setOpaque(true);
                lbl.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                        if (oA.makeMove(user.getOPosition(), true, move) != null && user.getParameters().isMyTurn()) {
                            OPosition = (OthelloPosition) user.getOPosition();
                            oA.playGame(user, move);
                            annuler.setEnabled(true);
                        }
                    }
                });
                pnl.add(lbl);
            }
        }
        return pnl;
    }
}