package PronosticosDeportivos;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Persona {

	private String nombre;
	private static ArrayList<Pronosticos> listaPronosticos = new ArrayList<Pronosticos>();
	private static ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
	
	public Persona(String nombre) {
		super();
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void agregarpronostico(Pronosticos pronostico) {
		listaPronosticos.add(pronostico);
		
	}
	
	public static void agregarPersona(Persona persona) {
		
		listaPersonas.add(persona);
	}
	
	public static Persona existePersona(String nombre) {
		
		for( Persona persona: listaPersonas) {
			if (persona.getNombre().equals(nombre));
				return persona;
			} 
		    Persona estaPersona= new Persona(nombre);
			agregarPersona(estaPersona);
			return estaPersona;
			
		}
	
	
	
	public static ArrayList<Persona> ArmarListaDePersonas(String archivo2, ArrayList<Partido> listaDePartidos ) {

		ArrayList<Persona> listaPersonas=new ArrayList<Persona>(); 
		try {

			CSVReader csvReader2 = new CSVReader(new FileReader(archivo2));
			String[] fila2 = null;
			String[] estaFila2 = null;
			Boolean primeraFila=true;
			
			while ((fila2 = csvReader2.readNext()) != null) {
				if (primeraFila) {
					primeraFila=false;
				} else {
				estaFila2 = fila2[0].split(";");
                
				Persona estaPersona=existePersona(estaFila2[0]);
				agregarPersona(estaPersona);
								
				String idPartido = estaFila2[1];
				Partido estePartido = null;
				for (Partido partido : listaDePartidos) {
					if (partido.getIdPartido().equals(idPartido)) {
						estePartido = partido;
					}
				}
         
				
				String resultadoEquipo1 = "";

				if (Arrays.asList(estaFila2).indexOf("x") == 2) {
					resultadoEquipo1 = "ganador";

				} else if (Arrays.asList(estaFila2).indexOf("x") == 3) {
					resultadoEquipo1 = "empate";
				} else if (Arrays.asList(estaFila2).indexOf("x") == 4) {
					resultadoEquipo1 = "perdedor";
				}

				Pronosticos nuevoPronostico = new Pronosticos(estePartido,  resultadoEquipo1);
				listaPronosticos.add(nuevoPronostico);

			}
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}

	return listaPersonas;

	}
	
	public Map<String,Integer> tabladePuntos() {
		
		Map<String,Integer> tablaPuntos = new HashMap<String,Integer>();
		for(Persona persona: listaPersonas ) {
			tablaPuntos.put(persona.getNombre(), Pronosticos.contarPuntos(persona.listaPronosticos));
		}
		return tablaPuntos;
	}
}
