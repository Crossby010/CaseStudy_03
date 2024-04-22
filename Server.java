import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        
        Socket S = null;
        ServerSocket SS = null;
        SS = new ServerSocket(6969);

        InputStreamReader ISR = null;
        OutputStreamWriter OSW = null;

        BufferedReader BR = null;
        BufferedWriter BW = null;

        while (true) {
            
            try{

                S = SS.accept();

                ISR = new InputStreamReader(S.getInputStream());
                OSW = new OutputStreamWriter(S.getOutputStream());
            
                BR = new BufferedReader(ISR);
                BW = new BufferedWriter(OSW);

                System.out.println("User Connected");

                BW.write("You Are Now Connected To The Server!");
                BW.newLine();
                BW.flush();

                while (true) {
                    
                    String msgFc = BR.readLine();

                    System.out.println("Client:" + msgFc);

                    BW.write(msgFc);
                    BW.newLine();
                    BW.flush();

                    if (msgFc.equalsIgnoreCase("BYE")) {

                        System.out.println("User Disconnect");

                        break;
                        
                    }

                    

                }

                S.close();
                ISR.close();
                OSW.close();
                BR.close();
                BW.close();


            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
