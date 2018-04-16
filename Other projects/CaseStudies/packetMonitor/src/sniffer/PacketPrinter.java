package sniffer;
import java.net.Inet4Address;
import java.net.InetAddress;

import jpcap.PacketReceiver;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class PacketPrinter implements PacketReceiver {
  //this method is called every time Jpcap captures a packet
	
	//outgoing
	public void sendSYN(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//incoming
	public void receiveSYN(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//incoming
	public void receiveSYNACK(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//outgoing
	public void sendSYNACK(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}	
	
	//incoming
	public void receiveACK(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//incoming
	public void receiveRST(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//incoming
	public void receiveFIN(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
		
	//outgoing
	public void sendFIN(InetAddress src, InetAddress dst, int src_port, int dst_port)
	{}
	
	//incoming
	public void receiveUnreached(InetAddress src)
	{}
	
	//incoming
	private void receiveUnreachedPort(InetAddress src, int port) 
	{}
	
	//incoming
	public void receiveRedirectICMP(InetAddress src)
	{}
	
	//incoming
	public void packetReceived(InetAddress src, int dst_port)
	{}
	
	//incoming connection
	public void connectionSuccess(InetAddress src)
	{}
	
	//incoming connection
	public void connectionFail(InetAddress src)
	{}
	
	public void receivePacket(jpcap.packet.Packet packet) 
	{
		try {
		
		if (packet instanceof IPPacket
				&& !Main.blocker.blocked.contains(((IPPacket)packet).src_ip)
				&& !Main.blocker.blocked.contains(((IPPacket)packet).dst_ip))
		{
			
			System.out.println(packet);
		
			//packet was sent to us
			if (((IPPacket)packet).dst_ip.equals(Main.ourIP))
			{	
				
				//checking for redirectPacket
				if (packet instanceof ICMPPacket)
				{					
					  //the data of the ICMP contains the wrongly sent packet
					  //for any IP packet, at the 17th till the 21st byte there is the destination address
					  
					  int headerlen = 0x000f & packet.data[0];//number of 32-bit words
					  int destport = 0x00ff & packet.data[headerlen*4+2];//skip IP header...start of TCP header...skip src port
					  destport <<= 8;
					  destport |= 0x00ff & packet.data[headerlen*4+3];
				
					if (((ICMPPacket)packet).type == 5)
						receiveRedirectICMP(Inet4Address.getByAddress(new byte[]{packet.data[16],packet.data[17],packet.data[18],packet.data[19]}));
					else if (((ICMPPacket)packet).type == 3)
					{
						if (((ICMPPacket)packet).code == 3)//unreached pot
							receiveUnreachedPort(Inet4Address.getByAddress(new byte[]{packet.data[16],packet.data[17],packet.data[18],packet.data[19]}), destport);
						else
							receiveUnreached(Inet4Address.getByAddress(new byte[]{packet.data[16],packet.data[17],packet.data[18],packet.data[19]}));
					}
				}
				else if (packet instanceof TCPPacket)
				{
					TCPPacket tcp = (TCPPacket)packet;
					
					//packet received (maybe attempted port scan)
					packetReceived(tcp.src_ip, tcp.dst_port);					
					
					//SYN packet received
					if (tcp.syn && tcp.ack)
						receiveSYNACK(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
					if (tcp.syn && !tcp.ack)
						receiveSYN(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
					if (tcp.ack)
						receiveACK(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
					if (tcp.rst)
						receiveRST(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
					if (tcp.fin && !tcp.ack)
						receiveFIN(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
				}
				else if (packet instanceof UDPPacket)
				{
					//packet received (maybe attempted port scan)
					packetReceived(((IPPacket)packet).src_ip, ((UDPPacket)packet).dst_port);					
					
				}
			}
			else //we sent the packet
			{
				if (packet instanceof TCPPacket)
				{
					TCPPacket tcp = (TCPPacket)packet;
					//SYN packet sent
					if (tcp.syn)
						sendSYN(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
					if (tcp.syn && tcp.ack)
						sendSYNACK(tcp.src_ip, tcp.dst_ip, tcp.src_port, tcp.dst_port);
				}
			}
		}
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}