import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.charset.Charset;

import org.omg.CORBA.portable.ValueOutputStream;

import jpcap.PacketReceiver;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

class PacketPrinter implements PacketReceiver {
  //this method is called every time Jpcap captures a packet
  public void receivePacket(jpcap.packet.Packet packet) {
    //just print out a captured packet
	  
	  if (packet instanceof IPPacket)
	  {
		  try{
		  InetAddress addr = Inet4Address.getByAddress(new byte[]{(byte)192,(byte)168,17,(byte)245});//getByName("localhost");//getByAddress(new byte[]{(byte)195,(byte)158,89,(byte)177});
//		  if ((((IPPacket)(packet)).dst_ip.equals(addr)) || ((IPPacket)(packet)).src_ip.equals(addr))
		  {
//				  && !(((TCPPacket)(packet)).dst_port == 3389 || ((TCPPacket)(packet)).src_port == 3389)) 
			  System.out.println(packet);
			  
//			  if (packet instanceof ICMPPacket)
//				//works for ICMP packets which return the packet which generated the error in their data
//			  {
//				  
//				  System.out.println(Inet4Address.getByAddress(new byte[]{packet.data[16],packet.data[17],packet.data[18],packet.data[19]}));
//				  //the data of the ICMP contains the wrongly sent packet
//				  //for any IP packet, at the 17th till the 21st byte there is the destination address
//				  
//				  int headerlen = 0x000f & packet.data[0];//number of 32-bit words
//				  int destport = 0x00ff & packet.data[headerlen*4+2];//skip IP header...start of TCP header...skip src port
//				  destport <<= 8;
//				  destport |= 0x00ff & packet.data[headerlen*4+3];
//				  System.out.println(destport);
//				  
//			  }
			  
			  
//			  if (packet instanceof ICMPPacket && ((ICMPPacket)packet).type == 3)
//					//works for ICMP packets which return the packet which generated the error in their data
//			  {
//				  System.out.println(packet);
//			  ObjectOutputStream output;
//			  try // open file
//			  {
//				  output = new ObjectOutputStream(new FileOutputStream("c:\\pakkettUnreached.abc"));
//				  output.writeObject(packet);
//				  output.close();
//			   } // end try
//			   catch ( IOException ioException )
//			   {System.err.println( "Error opening file." );}
//			  }
		  }
		  }catch(Exception ex)
		  {ex.printStackTrace();}
	  }
  }
}