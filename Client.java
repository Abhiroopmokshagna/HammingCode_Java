import java.net.*; 
import java.io.*; 
import java.util.*;  
public class Client 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
  
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
    	Random rand = new Random();
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
         
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
            	System.out.println("\nEnter a stream of characters: ");
                line = input.readLine();
                if(!(line=="Over")) {
                
                char[] chars = line.toCharArray();
                String linex = "";
                int[] bits = null;
                int[] tembits = null;
                int asc = 0;
                System.out.print("The bits sent to the Reciever  are: \n");
                for(int j=0;j<chars.length;j++) {
                	char c = chars[j];
                	asc = (int)c;
                	//System.out.print(asc+" ");
                	bits = new int[12];
                	tembits = new int[8];
                	int x = 1;
                	for(int i=7;i>=1;i--) {          
                		if((asc&x)>0)
                		tembits[i] = 1;
                		else
                		tembits[i] = 0;
                		//System.out.print(tembits[i]);
                		x=x<<1;
                	}
                	bits[3]=tembits[1]; bits[5]=tembits[2]; bits[6]=tembits[3]; bits[7]=tembits[4];
                	bits[9]=tembits[5]; bits[10]=tembits[6]; bits[11]=tembits[7];
                	bits[1] = (bits[3]+bits[5]+bits[7]+bits[9]+bits[11])%2;
                	bits[2] = (bits[3]+bits[6]+bits[7]+bits[10]+bits[11])%2;
                	bits[4] = (bits[5]+bits[6]+bits[7])%2;
                	bits[8] = (bits[9]+bits[10]+bits[11])%2;
                	for(int i=1;i<bits.length;i++) {
                		System.out.print(bits[i]);
                	}
                	System.out.print(" ");
                	//Calendar cal = Calendar.getInstance();
                	int flipornot = rand.nextInt(2);
                	if(flipornot==0) {
                		int flipbit = rand.nextInt(12);
                		if(!(flipbit==1||flipbit==2||flipbit==4||flipbit==8)) {
                			if(bits[flipbit]==1)
                				bits[flipbit]=0;
                				else
                					bits[flipbit]=1;
                		}
                	}
            		for(int i=1;i<bits.length;i++)
            		linex = linex+Integer.toString(bits[i]);
                	linex = linex + " ";
                }
                out.writeUTF(linex);
               }
                else
                out.writeUTF(line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        Client client = new Client("127.0.0.1", 5000); 
    } 
} 