import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        // socket / input & output
        System.out.println("[C1] Conectando com servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());


        System.out.println("[C2] Conexao estabelecida, eu sou o cliente: " + socket.getLocalSocketAddress());        

        MessageHandler msgHandler = new MessageHandler(input);
        msgHandler.start();

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String msg;
        while (true) {
            System.out.println("> ");
            msg = scanner.nextLine();
            output.writeUTF(msg);
        }
    }
 
    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "0.0.0.0";
        int serverPort = 6666;
        try {
    
            TCPClient client = new TCPClient();
            client.start(serverIp, serverPort);
            
       
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
