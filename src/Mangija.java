/**
 * Mängija klass
 * <p>
 * Selle klassi abil luuakse mängija ning omastatakse talle vajalikud väärtused
 */


import java.util.ArrayList;

public class Mangija {
	private char varv;                                                // Värv, millega mängija mängib
	final private ArrayList<Nupp> nupud;                                // Mängija olemas olevad nupud

	public Mangija() {
		this.nupud = new ArrayList<>();
	}

	public char getVarv() {
		return varv;
	}

	public void setVarv(char varv) {
		this.varv = varv;
	}

	public ArrayList<Nupp> getNupud() {
		return nupud;
	}


	/**
	 * Loosib mängijate värvid
	 *
	 * @param mangija1 Esimene mängija
	 * @param mangija2 Teine mängija
	 */
	public static void seaMängijateVärvid(Mangija mangija1, Mangija mangija2) {
		if (Math.random() < 0.50) {
			mangija1.setVarv('m');
			mangija2.setVarv('v');
		} else {
			mangija1.setVarv('v');
			mangija2.setVarv('m');
		}


	}
}
