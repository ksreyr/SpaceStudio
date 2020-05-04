package de.bremen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.*;
import java.util.Collections;
import java.util.Enumeration;

public class LogginScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;

    private Label userName;
    private Label userPassword;
    private Label myIP;

    //Verbindung
    Socket socket;
    private String IPAdresse = "127.0.0.1";
    Net.Protocol protocol = Net.Protocol.TCP;
    int portServer = 8080;
    int portClient = 8081;
    //DataBase
    Connection con;
    String url = "jdbc:h2:./Database/test;DB_CLOSE_DELAY=-1";
    Statement stmt;
    String sql;

    //
    boolean found=false;

    //Contructor
    public LogginScreen(final MainServer game) {
        super(game);


        stage = new Stage(new FitViewport(800, 600));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        userName = new Label("NameToSearch", skin);
        myIP = new Label(IPAdresse, skin);
        userPassword = new Label("Password", skin);

        //DataBase Creation
        /*try {
            creationInsertionDataBase();
            insertDataBase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        //Server Listen
        serverListen();

        //DrawComponents
        myIP.setSize(200, 50);
        myIP.setPosition(220, 450);

        userName.setSize(200, 50);
        userName.setPosition(220, 350);

        userPassword.setSize(200, 50);
        userPassword.setPosition(220, 250);

        stage.addActor(userName);
        stage.addActor(userPassword);
        stage.addActor(myIP);

    }
    private void responde(String willkomen){
        try {
            SocketHints sh = new SocketHints();
            sh.connectTimeout = 10000;
            socket = Gdx.net.newClientSocket
                    (protocol, "127.0.0.1",
                            portClient, sh);
            System.out.println("Sending willkomen");
            socket.getOutputStream().write(willkomen.getBytes());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void serverListen() {
        //Server connection
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketHints ssh = new ServerSocketHints();
                //0 damit ganzen Zeit wartet der Server
                ssh.acceptTimeout = 0;
                ServerSocket socket = Gdx.net.newServerSocket(protocol, portServer, ssh);
                while (true) {
                    Socket s = socket.accept(null);
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    try {
                        userName.setText(buffer.readLine());
                        userPassword.setText(buffer.readLine());
                        //Query
                        try {
                            System.out.println("Make Query with name: " + userName.getText().toString());
                            makeQuery(userName.getText().toString());
                            if (found) {
                                responde("Welcome");
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private ResultSet makeQuery(String parameterOne) throws SQLException {
        con = DriverManager.getConnection(url, "sa", "");
        stmt = con.createStatement();
        //Read
        sql = "SELECT " + "* " + "FROM USER WHERE NAME='" + parameterOne + "'";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println("Data For this query: " + "ID: " + rs.getInt("ID")
                    + " Name:" + rs.getString("NAME") + " Password:" + rs.getString("PASSWORD"));
             found = true;
        }
        con.close();
        return rs;
    }

    private void creationInsertionDataBase() throws SQLException {
        con = DriverManager.getConnection(url, "sa", "");
        stmt = con.createStatement();
        //Creation
        sql = "CREATE TABLE USER " +
                "(ID INT PRIMARY KEY,  NAME VARCHAR, PASSWORD VARCHAR)";
        stmt.executeUpdate(sql);
        con.close();
    }

    private void insertDataBase() throws SQLException {
        con = DriverManager.getConnection(url, "sa", "");
        stmt = con.createStatement();

        //Insert
        sql = "INSERT INTO USER" + " VALUES(1, 'Kevin', 'password')";
        stmt.executeUpdate(sql);
        con.close();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    private void getAdressen() {
        //Adresse unsere Server
        // ArrayList<String> addressen = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface i :
                    Collections.list(interfaces)) {
                for (InetAddress addr :
                        Collections.list(i.getInetAddresses())) {
                    if (addr instanceof Inet4Address) {
                        //addressen.add(addr.getHostAddress());
                        IPAdresse = IPAdresse + addr.getHostAddress() + "\n";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


}
