//imports packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//main class
public class Server {
    private JFrame frame;
    private JTextArea textArea;
    private JButton startButton, stopButton;
    private ServerSocket serverSocket;
    private boolean isRunning = false;//restrict the visibility of variable isrunning
//constructor of server class
    public Server() {
        createGUI();
        getMessages(); //previous messages show
    }
// creating GUI
    private void createGUI() {
        frame = new JFrame("Server Messenger");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Load and resize the image icon
        ImageIcon originalIcon = new ImageIcon("image2icon.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjusting the side if image icon
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel heading = new JLabel("Server Area", scaledIcon, SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        textArea.setBackground(Color.white);  // Setting background color for text area
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.darkGray);  // Set background color for button panel

        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopButtonListener());
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(Color.pink); // Set background color for frame
        frame.add(heading, BorderLayout.NORTH);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        frame.setSize(350, 650);
        frame.setVisible(true);
    }

    static void insertMessges(String message, String time) {
        String sql = "INSERT INTO message (messages, TIME) VALUES (?, ?)";
     //? placeholder place will be filled later with actual values
        try {
            //establishing a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/messages", "root", "Areesh@123");
            // creating a prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //set the parameter for prepare statement
            preparedStatement.setString(1, message);
            preparedStatement.setString(2, time);
            //now executing update operation of Sql
            preparedStatement.executeUpdate();
            //closing both
            preparedStatement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            isRunning = true;
            startServer();
        }
    }

//getting previous messages
    void getMessages(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/messages", "root", "Areesh@123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM message");
            int index=1;//initailizing coumter index for messages number
            textArea.append("Previous Messgaes :-\n");
            while (resultSet.next()) { //iterating through the resultset to process each row
                             textArea.append(  (  " Client: " + resultSet.getString("messages")+" ["+ resultSet.getString("TIME") + "]" + "\n")
                             );
                // Output to console for debugging or logging purposes
                System.out.println("Message " + index++ +" : " + resultSet.getString("messages") + "Time:-" + resultSet.getString("TIME"));
            }
            textArea.append("Current Messgaes :-\n");
            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();

        }
        catch (Exception ex){
            System.out.println(ex.getStackTrace().toString()); // Handling any exceptions that may occur during database operations
        }

    }



    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            isRunning = false;
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(7777);
                    // Server loop to accept incoming client connections
                    while (isRunning) {
                        Socket socket = serverSocket.accept();
                        //setting streams
                        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                        while (true) { //communication loop
                            String msgFromClient = bufferedReader.readLine();
                            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

                            try { //inserting messages and time into database
                              insertMessges(msgFromClient,timeStamp);
                                System.out.println("DataSaved");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            textArea.append(  " Client: " + msgFromClient+"["+ timeStamp + "]" + "\n");
                            bufferedWriter.write("msg received by server");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            if (msgFromClient.equalsIgnoreCase("Bye Bye")) {
                                break;
                            }
                        }
                        socket.close();
                        inputStreamReader.close();
                        outputStreamWriter.close();
                        bufferedWriter.close();
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start(); //starting and allowing multiple clients to cnnect concurrently
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //method that ensure the runnable pass to it execute in EDT
          // A new runnable instance is created
            @Override
            public void run() {
                new Server();
            }
        });
    }
}
