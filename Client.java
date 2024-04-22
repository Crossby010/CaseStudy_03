import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        
        Socket S = null;

        InputStreamReader ISR = null;
        OutputStreamWriter OSW = null;

        BufferedReader BR = null;
        BufferedWriter BW = null;

        try{
           
            S = new Socket("localHost", 6969);

            ISR = new InputStreamReader(S.getInputStream());
            OSW = new OutputStreamWriter(S.getOutputStream());

            BR = new BufferedReader(ISR);
            BW = new BufferedWriter(OSW);

            Scanner IN = new Scanner(System.in);

            System.out.println("Server: " + BR.readLine());

            while (true) {
                
                String msgts = IN.nextLine();

                BW.write(msgts);
                BW.newLine();
                BW.flush();

                System.out.println("Server Recieved:" + BR.readLine());
                
                if (msgts.equalsIgnoreCase("BYE")) {

                    break;
                    
                }

            }

            IN.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           try{
            if (S != null)
                S.close();
            if (ISR != null)
                ISR.close();
            if (OSW != null)
                OSW.close();
            if (BR != null)
                BR.close();
            if (BW != null)
                BW.close();
           } catch (IOException e) {

                e.printStackTrace();

           }
        }

    }
}
