/**
 * Loob nupu objekti
 *
 * */


public class Nupp {
	final private String nimi;			// Nupu nimetus
	final private String varv;			// Millise mängija nupp
	private int enPassant;				// Kui ettur liigub kaks ruutu salvetatakse käigu number, et kontrollida enpassanti
	private boolean kasLiikunud;		// Default false, kui ettur, kuningas või vanker liigub esimest korda, siis muutub true-ks

	public Nupp(String nimi, String varv) {
		this.nimi = nimi;
		this.varv = varv;
		this.enPassant = 0;
		this.kasLiikunud = false;
	}

	public String getNimi() {
		return nimi;
	}

	public String getVarv() {
		return varv;
	}


	public void setEnPassant(int kaiguNr) {
		this.enPassant = kaiguNr;
	}

	public void setLiikunud() {
		this.kasLiikunud = true;
	}
}
