import java.net.*;
import java.util.StringTokenizer;
import java.io.*;
public class Utente implements Runnable
{
	private Socket connessione = null;
	private InputStreamReader in;
	private BufferedReader sIN;
	private OutputStream out;
	private PrintWriter sOUT;
	// rubrica telefonica
	private FileReader f;
	private BufferedReader fIN;
	Contatto c;
	String s;
	StringTokenizer st;
	// dati per socket
	String telefDaInviare;
	String nomeRicevuto;

	public Utente(Socket conn)
	{
		this.connessione = conn;
		try
		{	
			// apre i flussi in uscita e in ingresso dalla socket
			// flusso in uscita su socket
			out = connessione.getOutputStream();
			sOUT = new PrintWriter(out);
			// flusso in ingresso da socket
			in = new InputStreamReader(connessione.getInputStream());
			sIN = new BufferedReader(in);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}

		new Thread(this).start();
		System.out.println("Il client ha avviato una ricerca.");
	}

	public void run()
	{
		try
		{
			while (true)
			{
				// riceve il nome
				nomeRicevuto = sIN.readLine();
				System.out.println(nomeRicevuto);
				if (nomeRicevuto == null)
				{
					System.out.println("Il client ha chiuso la connessione.");
					break;
				}
				// cerca il nome nella rubrica
				f = new FileReader("c:\\temp\\agenda.txt");
				fIN = new BufferedReader(f);
				c = new Contatto();
				boolean trovato = false;
				s = fIN.readLine();
				// legge una riga
				while ((s != null) && (!trovato))
				{
					st = new StringTokenizer(s, ";");
					c.nome = st.nextToken();
					c.telefono = st.nextToken();
					if (c.nome.equals(nomeRicevuto)) 
					{
						telefDaInviare = c.telefono;
						trovato = true;
					}
					s = fIN.readLine();
				}
				f.close();
				if (!trovato) 
				{
					telefDaInviare = "non trovato";
				}
				// invia il numero di telefono
				sOUT.println(telefDaInviare);
				sOUT.flush();
			}
			connessione.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}