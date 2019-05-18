

import java.net.*;
import java.util.HashMap;
import java.io.*; 
  
public class Server 
{ 
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 
    HashMap<String,Integer> lookup = new HashMap<String,Integer>();
    // constructor with port 
    public Server(int port) 
    { 
        // starts server and waits for a connection 
        try
        { 
        	lookup.put("0011", 3);lookup.put("0101", 5);lookup.put("0110", 6);lookup.put("0111", 7);
        	lookup.put("1001", 9);lookup.put("1010", 10);lookup.put("1011", 11);
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            System.out.println("Client accepted"); 
  
            // takes input from the client socket 
            in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            String line = ""; 
  
            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) 
            { 
                try
                { 
                	line = "";
                    line = in.readUTF();
                    String[] inputs = line.split(" ");
                    for(String s:inputs) {
                    	//System.out.print("\n"+s+" -");
                    	char chars[] = s.toCharArray();
                    	System.out.print("\n\n"+s+" - "+chars.length+" - ");
                    	int bits[] = new int[chars.length+1];
                    	for(int j=1;j<bits.length;j++) {
                    		if(chars[j-1] == '1')
                    			bits[j] = 1;
                    		else if(chars[j-1]=='0')
                    			bits[j] = 0;
                    		System.out.print(bits[j]);
                    	}
                    	int a = (bits[1]+bits[3]+bits[5]+bits[7]+bits[9]+bits[11])%2;
                    	int b = (bits[2]+bits[3]+bits[6]+bits[7]+bits[10]+bits[11])%2;
                    	int c = (bits[4]+bits[5]+bits[6]+bits[7])%2;
                    	int d = (bits[8]+bits[9]+bits[10]+bits[11])%2;
                    	if(a==1||b==1||c==1||d==1) {
                    	System.out.print("\n\nIt seems there is an error in the data!");
                    	System.out.print("\nCorrecting the data.......");
                    	String flippedpos = "";
                    	flippedpos = flippedpos+d+c+b+a;
                    	System.out.print("\nThe position where the error occured is: "+flippedpos+" a.k.a "+lookup.get(flippedpos));
                    	System.out.println("\nCorrecting the bit.....\nCorrect sequence is: ");
                    	if(bits[lookup.get(flippedpos)]==1)
                    		bits[lookup.get(flippedpos)]=0;
                    	else
                    		bits[lookup.get(flippedpos)]=1;
                    	for(int i=1;i<bits.length;i++)
                    		System.out.print(bits[i]);
                    	}
                    	else
                    		System.out.println("\nRecieved correct data! waiting for more characters.....\n");
                    	
                    }
                    //System.out.println(line); 
                    
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close(); 
            in.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        Server server = new Server(5000); 
    } 
} 