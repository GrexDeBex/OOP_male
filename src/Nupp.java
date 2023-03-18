/**
 * Loob nupu objekti
 *
 * */


public class Nupp {
	final private String nimi;			// Nupu nimetus
	final private String varv;			// Millise mängija nupp
	private int asukohtx;
	private int asukohty;
	private int enPassant;				// Kui ettur liigub kaks ruutu salvetatakse käigu number, et kontrollida enpassanti
	private boolean kasLiikunud;		// Default false, kui ettur, kuningas või vanker liigub esimest korda, siis muutub true-ks

	public Nupp(String nimi, String varv, int asukohtx, int asukohty) {
		this.nimi = nimi;
		this.varv = varv;
		this.enPassant = 0;
		this.kasLiikunud = false;
		this.asukohtx = asukohtx;
		this.asukohty = asukohty;
	}

	public String getNimi() {
		return nimi;
	}

	public String getVarv() {
		return varv;
	}

	public int getAsukohtx() {
		return asukohtx;
	}

	public int getAsukohty() {
		return asukohty;
	}

	public void setAsukohtx(int asukohtx) {
		this.asukohtx = asukohtx;
	}

	public void setAsukohty(int asukohty) {
		this.asukohty = asukohty;
	}

	public void setEnPassant(int kaiguNr) {
		this.enPassant = kaiguNr;
	}

	public void setLiikunud() {
		this.kasLiikunud = true;
	}
}
