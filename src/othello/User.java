package othello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import rechercheAdversiale.OthelloPosition;
import rechercheAdversiale.Parameters;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private OthelloPosition oPosition;
    private Parameters parameters;

    private boolean isLogged = false;
    private ArrayList<User> users;

    public User(String username, String password) {
        users = new ArrayList<User>();
        this.username = username;
        this.password = password;
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
        oPosition = new OthelloPosition(brd);
        parameters = new Parameters();
    }

    public boolean create() {
        if (!isExist()) {
            users.add(this);
            this.ecrire();
            return true;
        } else {
            return false;
        }
    }

    public boolean login() {
        if (!isExist()) {
            return false;
        } else {
            isLogged = true;
            backup();
            return true;
        }
    }

    public void logout() {
        if (isLogged()) {
            isLogged = false;
        }
    }

    public void update() {
        if (isExist()) {
            int i = this.getIndex();
            users.set(i, this);
            this.ecrire();
        };
    }

    public void backup() {
        if (isExist()) {
            int i = this.getIndex();
            this.setOPosition(users.get(i).getOPosition());
            this.setParameters(users.get(i).getParameters());
            this.ecrire();
        };
    }

    private int getIndex() {
        this.lire();
        int i = 0;
        for (User u : users) {
            if (u.equals(this)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean isLogged() {
        return isLogged;
    }

    private boolean isExist() {
        this.lire();
        for (User u : users) {
            if (u.equals(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (username.equals(((User) obj).getUsername())
                && password.equals(((User) obj).getPassword())) {
            return true;
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public OthelloPosition getOPosition() {
        return oPosition;
    }

    public void setOPosition(OthelloPosition oPosition) {
        this.oPosition = oPosition;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    private void lire() {
        File f = new File(parameters.getFile());
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
            }
            return;
        }
        try {
            FileInputStream fileIs = new FileInputStream(parameters.getFile());
            ObjectInputStream in = new ObjectInputStream(fileIs);
            users = (ArrayList<User>) in.readObject();
            in.close();
            fileIs.close();
        } catch (Exception ex) {
        }
    }

    public void ecrire() {
        try {
            FileOutputStream fileOs = new FileOutputStream(parameters.getFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOs);
            out.writeObject(users);
            out.flush();
            out.close();
            fileOs.close();
        } catch (IOException ex) {
        }
    }

}