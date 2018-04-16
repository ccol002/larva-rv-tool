package sniffer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.HashSet;

import jpcap.JpcapSender;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class Blocker {

	HashSet<InetAddress> blocked;
	TCPPacket resetPckt; 
	JpcapSender sender;
	int rr=0;
	
	public Blocker()
	{
		blocked = new HashSet<InetAddress>();
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("c:\\pakkett.abc"));
			resetPckt = (TCPPacket)ois.readObject();
			resetPckt.syn = false;
			resetPckt.rst = true;
			resetPckt.fin = false;
			resetPckt.ack = false;
			resetPckt.dst_ip = Main.ourIP;
			sender = JpcapSender.openDevice(Main.device);
			
			Runtime.getRuntime().exec("netsh ipsec static delete policy name=\"ontheflysecurity\"").waitFor();
			Runtime.getRuntime().exec("netsh ipsec static delete filterlist=\"list\"").waitFor();
			
			Runtime.getRuntime().exec("netsh ipsec static add policy name=\"ontheflysecurity\"").waitFor();
			Runtime.getRuntime().exec("netsh ipsec static set policy name=\"ontheflysecurity\" assign=\"yes\"").waitFor();
			Runtime.getRuntime().exec("netsh ipsec static add filteraction name=\"block\" action=\"block\"").waitFor();
			Runtime.getRuntime().exec("netsh ipsec static add filterlist=\"list\"").waitFor();
			Runtime.getRuntime().exec("netsh ipsec static add rule name=\"block\" policy=\"ontheflysecurity\" filterlist=\"list\" filteraction=\"block\"").waitFor();
		}catch(Exception ex)
		{ex.printStackTrace();}

	}

	public void blockIP(InetAddress ip)
	{
		if (!blocked.contains(ip))
		{
			blocked.add(ip);
			try{
				System.out.println("netsh ipsec static add filter filterlist=\"list\" srcaddr="+ip.toString().substring(1)+" dstaddr=me");
				Runtime.getRuntime().exec("netsh ipsec static add filter filterlist=\"list\" srcaddr="+ip.toString().substring(1)+" dstaddr=me");
				Runtime.getRuntime().exec("netsh ipsec static add rule name=\"block\" policy=\"ontheflysecurity\" filterlist=\"list\" filteraction=\"block\"");
				Runtime.getRuntime().exec("netsh ipsec static set policy name=\"ontheflysecurity\" assign=\"yes\"");
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		else {System.out.println("Tried to block " + ip + "...already blocked!");}
	}

	public void unBlockIP(InetAddress ip)
	{
		if (blocked.contains(ip))
		{
			blocked.remove(ip);
			try{
				Runtime.getRuntime().exec("netsh ipsec static delete filter filterlist=\"list\" srcaddr="+ip.toString().substring(1)+" dstaddr=me");
			}catch(Exception ex)
			{ex.printStackTrace();}
		}
		else {System.out.println("Tried to unblock " + ip + "...not blocked!");
		}
	}

	public void unBlockAll()
	{
		for (InetAddress ip : blocked)
		{
			unBlockIP(ip);
		}
	}

	public void reset(InetAddress ip, int port, int port2)
	{
		resetPckt.src_ip = ip;
		resetPckt.src_port = port;
		resetPckt.dst_port = port2;
		sender.sendPacket(resetPckt);
		System.out.println("Reset "+(++rr));
	}
	
	public void destroy()
	{
		try{
			Runtime.getRuntime().exec("netsh ipsec static delete policy name=\"ontheflysecurity\"");
		}catch(Exception ex)
	{ex.printStackTrace();}
	}
}
