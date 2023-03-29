/**
 * Peaklass
 * <p>
 * Siin käivitatakse mäng
 */


import java.util.Objects;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Mangija mangija1 = new Mangija();					// Luuakse mängijad
		Mangija mangija2 = new Mangija();
		Mangija.seaMängijateVärvid(mangija1, mangija2);		// Jaotatkse värvid

		Mangija kaiguTegija, vastane;
		Mangulaud mangulaud;

		if (mangija1.getVarv() == 'v') {					// Määratakse mängijate järjekord ja luuakse mängulaud
			kaiguTegija = mangija1;
			vastane = mangija2;
			mangulaud = new Mangulaud(mangija2, mangija1);
		} else {
			kaiguTegija = mangija2;
			vastane = mangija1;
			mangulaud = new Mangulaud(mangija1, mangija2);
		}

		Mangulaud.reeglid(mangija1, mangija2);				// Kuvatakse reeglid ja mängulaud
		mangulaud.väljastaLaud();


		while (true) {
			System.out.println(kaiguTegija.getVarv() + " käib!");	// Kuvab, kelle kord

			System.out.println("Sisesta oma käik: ");				// Küsib käiku mängijalt
			Scanner sc = new Scanner(System.in);
			String[] sisend = sc.nextLine().split(" ");

			if (Objects.equals(sisend[0], "lõpeta")) {				// Lõpetab mängu soovi korral
				String nimi = (kaiguTegija == mangija1) ? "mängija 2" : "mängija 1";
				System.out.println("Võitja on " + nimi);
				break;
			}

			Nupp valitudNupp = KaiguKontroll.valiNupp(sisend[0], kaiguTegija.getNupud());	// Kontrollib valitud nupu õigsust
			if (valitudNupp == null) {
				System.out.println("Sellist nuppu pole laual või olemas");
				continue;
			}


			int[] sihtkoht = KaiguKontroll.teisendaSihtkoht(sisend[1]);						// Kontrollib sihtkoha ja käigu õigsust
			if (sihtkoht[0] == -1 || !KaiguKontroll.kontroll(valitudNupp, sihtkoht[0], sihtkoht[1], mangulaud.getLaud(), vastane)) {
				System.out.println("Vale käik");
				continue;
			}


			if (kaiguTegija == mangija1) {				// Määrab, kelle kord on järgmine käik teha
				kaiguTegija = mangija2;
				vastane = mangija1;
			} else {
				kaiguTegija = mangija1;
				vastane = mangija2;
			}


			mangulaud.väljastaLaud();

			boolean mangLabi = true;					// Otsib kuningat vastase nuppudest
			for (Nupp nupp : vastane.getNupud()) {
				if (nupp.getNimi().equals("kuningas")) {
					mangLabi = false;
					break;
				}
			}

			if (mangLabi) {								// Kui kuningat ei leitud, siis on mäng läbi
				String nimi = (kaiguTegija == mangija1) ? "mängija 1" : "mängija 2";
				System.out.println("Võitja on " + nimi);
				break;
			}
		}
	}
}
