import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

class Client {
    //declare variable for GUI components
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    //declare variable for networking concepts
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
//forming a constructor
    public Client() {
        initializeGUI();
        initializeConnection();
        startMessageListener();
    }

//creating GUI
    private void initializeGUI() {
        frame = new JFrame("Client Messenger");
        frame.setSize(350, 650); // Set the frame size

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Load and resize the image icon
        ImageIcon originalIcon = new ImageIcon("image2icon.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust the size of my icon
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel heading = new JLabel("Client Area", scaledIcon, SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        textField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.getContentPane().setBackground(Color.pink);
        textArea.setBackground(Color.white);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.add(heading, BorderLayout.NORTH);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 10));
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setVisible(true);
    }

    private void initializeConnection() {
        try {
            socket = new Socket("localhost", 7777);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startMessageListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //delcaring variable to store msg from server
                String msgFromServer;
                try {
                    while ((msgFromServer = bufferedReader.readLine()) != null) {
                        textArea.append("Server: " + msgFromServer + "\n");
                        if (msgFromServer.equalsIgnoreCase("BYE")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally { //ensure to close the connection if exception occurs
                    closeConnections();
                }
            }
        }).start();
    }

    private void sendMessage() {
        String msgToSend = textField.getText();
        try {
            bufferedWriter.write(msgToSend);
            bufferedWriter.newLine();//add newline to signify end of message
            bufferedWriter.flush();//makesure msg is sent
            textArea.append("Client: " + msgToSend + "\n");
            textField.setText("");// clear textfield to make it ready for next msg
            if (msgToSend.equalsIgnoreCase("Bye Bye")) {
                closeConnections();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            if (bufferedWriter != null) bufferedWriter.close();
            if (bufferedReader != null) bufferedReader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { //event dispatch thread
            @Override
            public void run() {
                new Client();
            }
        });
    }
}




