import java.util.HashSet;

public class Mangija {
	private char varv;
	private HashSet<Nupp> nupud;

	public Mangija() {
		this.nupud = new HashSet<>();
	}

	public char getVarv() {
		return varv;
	}

	public void setVarv(char varv) {
		this.varv = varv;
	}

	public HashSet<Nupp> getNupud() {
		return nupud;
	}


	public static void seaMängijateVärvid(Mangija mängija1, Mangija mängija2) {
		if (Math.random() < 0.50) {
			mängija1.setVarv('m');
			mängija2.setVarv('v');
		}
		else {
			mängija1.setVarv('v');
			mängija2.setVarv('m');
		}


	}
}
