/**
 * Loob nupu objekti
 *
 * */


public class Nupp {
	final private String nimi;			// Nupu nimetus
	final private char varv;			// Millise mängija nupp
	private int asukohty;
	private int asukohtx;
	private boolean enPassant;				// Kui ettur liigub kaks ruutu salvetatakse käigu number, et kontrollida enpassanti
	private boolean kasLiikunud;		// Default false, kui ettur, kuningas või vanker liigub esimest korda, siis muutub true-ks

	public Nupp(String nimi, char varv, int asukohtx, int asukohty) {
		this.nimi = nimi;
		this.varv = varv;
		this.enPassant = true;
		this.kasLiikunud = false;
		this.asukohtx = asukohtx;
		this.asukohty = asukohty;
	}

	public String getNimi() {
		return nimi;
	}

	public char getVarv() {
		return varv;
	}

	public int getAsukohtx() {
		return asukohtx;
	}

	public int getAsukohty() {
		return asukohty;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public void setAsukohtx(int asukohtx) {
		this.asukohtx = asukohtx;
	}

	public void setAsukohty(int asukohty) {
		this.asukohty = asukohty;
	}

	public void setEnPassant(boolean väärtus) {
		this.enPassant = väärtus;
	}

	public void setLiikunud() {
		this.kasLiikunud = true;
	}
}
