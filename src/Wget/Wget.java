package Wget;

import  java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

//import java.io.InputStreamReader;
//import java.util.stream.Stream;

/**
 * Aquesta classe conté el main del programa, a on es creen els fluxos d'execució. 
 * @author Pol Garcia
 * @author Flavio Jiménez
 */

public class Wget {

	
	/** Aquesta és la funció principal del programa, la qual es encarregada d'iniciar 
	 * el programa, d'efectuar la lectura del fitxer designat i d'extreure les urls que 
	 * contingui per poder efectuar la descarrega del codi font futurament
	 * 
	 * @param arguments String amb els arguments que se li passen al programa mitjançant la consola.**/
	public static void main( String[] arguments ) 
	{ 

		//System.out.println("hello xarxes");


		
			String FILENAME = null;
			String url=null;
		try {
			
			
			
			boolean a=false;
			boolean z=false;
			boolean gz=false;
			boolean f=false;
			
			int nArgs=0;
			
			while (nArgs<arguments.length) //Aquest bucle te la funcio de tractar totes les posicions del array d'arguments, per tal de comprovar els filtres que vol aplicar el usuari
			{
				
				if (( arguments.length>nArgs+1 ) && ( Objects.equals(arguments[nArgs], "-f") ) && ( Objects.equals(arguments[nArgs+1], "urls.txt" )))// aquest if es per la comprovacio de l'existencia de l'etiqueta -f i de que seguidament te el fitxer urls.txt
				{
                    // Si la condicio anteriorment dita es compleix, activem el boolea f a true
						FILENAME=arguments[nArgs+1];
						f=true;
 
				}

				if ( Objects.equals(arguments[nArgs], "-a") ) //Aquest if s'encarrega de comprovar que en l'array d'arguments existeixi el filtre -a, si no esta simplement no s'activara el boolea i per tant no es tractara el filtre mes endavant
				{
					a=true;
				}
			
				if ( Objects.equals(arguments[nArgs], "-z") )//Aquest if s'encarrega de comprovar que en l'array d'arguments existeixi el filtre -z
				{
					z=true;
				}
			
				if ( Objects.equals(arguments[nArgs], "-gz") )//Aquest if s'encarrega de comprovar que en l'array d'arguments existeixi el filtre -gz
				{
					gz=true;
				}
			
			nArgs++; // Augmentem el numero d'arguments recorreguts en l'array per poder consultar tot el array en busca dels filtres que volem comprovar
			
			}
			
			if (f) // Si i nomes si f es igual a true, executarem el proces d'obrir el fitxer i descarregar les urls  
			{
			
			
			//Stream<String> stream = null;
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String s;
			Flux t=null;
			int i=0; //Contador de fitxers
			
			while ((s=in.readLine())!=null) // Aquest bucle es el serveix per efectuar la lectura de les urls dins del txt, i crear un nou thread que efectui la descarrega i el tractament de filtres amb la url corresponent 
		    {
				
		           //System.out.println(s);
		           	url=s;
		             
					t = new Flux(s,i,a,z,gz); // A la nova variable que genera un thread, li emviem els booleans dels filtres detectats i el string amb la url.
					i++;
					t.start();// Un cop creada la variable executem el thread per a que comenci efectuar la descarrega.
 
		    }
			
			 
			synchronized(t) 
			{
				t.wait();//Aqui prvoquem que el proces pare es quedi esperant a que els threads acabin d'executarse, per finalitzar quan tots el threads generats hagin acabat.
			}
			
			in.close();
			 
		}
			else
				{
				System.out.println("\nError: El archivo fuente no existe o está corrupto.\nRecuerde introducir siempre la ruta después de la etiqueta -f.");/* Cas que no es detecti correctament el txt o el argument -f llavors es llançarà una alerta.
				Cal tenir en compte que aquest és l'únic error esperat (no advertencia) que es pot produir i que aturarà el programa. */
				}
		}catch (FileNotFoundException e) {
				
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\nAdvertencia: La dirección "+url+"\nno existe o es inaccesible.");	
			}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	