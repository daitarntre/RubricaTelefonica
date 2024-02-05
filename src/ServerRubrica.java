import java.net.*;
import java.io.*;
public class ServerRubrica
{
	public static void main(String args[])
	{
		ServerSocket sSocket;
		Socket connessione = null;
		int port = 2345;
		try
		{
			sSocket = new ServerSocket(port);
			System.out.println("In attesa di connessioni...");
			// ciclo infinito
			while (true)
			{
				connessione = sSocket.accept();
				new Utente(connessione);
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		try
		{
			connessione.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}
