package sniffer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;


public class Main {

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static InetAddress ourIP = null; 
	public static NetworkInterface device = null;
	public static int timeout = 1000;
    public static Blocker blocker;
    
    public static int read()
	{
		try{
			return Integer.parseInt(br.readLine());
		}
		catch(Exception ex)
		{ex.printStackTrace();}
		return -1;
	}

    public static void initialize()
    {}
    
	public static void main(String[] args) throws Exception
	{
		initialize();initialize();
		try{
			System.out.println("Packet Monitor Started...");
			
			NetworkInterface[] devices = JpcapCaptor.getDeviceList();

			if (devices.length == 0)
				throw new Exception("No Network Devices Found...make sure you are running with administrator privileges!");

//			//for each network interface
//			for (int i = 0; i < devices.length; i++) {
//				//print out its name and description
//				System.out.println(i+" >>> "+devices[i].name + "(" + devices[i].description+")");
//			}
//
//			int choice = 0;
//			do {
//				System.out.print("Enter a valid device number [0-" + (devices.length-1) +"]: ");
//				choice = read();
//			} while (choice < 0 || choice >= devices.length);
//
//			for (int i = 0; i < devices[choice].addresses.length; i++) {
//				NetworkInterfaceAddress a = devices[choice].addresses[i];
//				System.out.println(i+" >>> address:" + a.address + " " + a.subnet + " "+ a.broadcast);
//			}
//
//			int ip = 0;
//			do {
//				System.out.print("Enter a valid address number [0-" + (devices[choice].addresses.length-1) +"]: ");
//				ip = read();
//			} while (ip < 0 || ip >= devices[choice].addresses.length);
//
//				
//			System.out.print("Enter timeout for incoming connections (ms): ");
//			timeout = read();
			
			int choice = 2;
			int ip = 3;//3
			timeout = 1000;
			
			device = devices[choice];
			ourIP = device.addresses[ip].address;	
			blocker = new Blocker();
			
			System.out.print("Starting Packet Capture Device...");
			JpcapCaptor captor = JpcapCaptor.openDevice(devices[choice], 65535, true, 20);
			System.out.println("started OK...");
			captor.loopPacket(Integer.MAX_VALUE,new PacketPrinter());
			captor.close();
			System.out.println("Packet Monitoring Ended...");


		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}
}
