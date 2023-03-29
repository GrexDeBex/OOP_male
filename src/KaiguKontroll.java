/**
 * Käikude kontrollide klass
 * <p>
 * Klass sisaldab kõiki käigu sooritamise kontrollide funktsioone
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class KaiguKontroll {


	/**
	 * Leiab sisendi põhjal nupu, mida mängija tahab liigutada, või tagastab, et sellist nuppu ei ole
	 *
	 * @param sisend       Mängija sisestatud nimetus
	 * @param mangijaNupud Kõik mängija nupud
	 * @return Soovitud nupp või negatiivne tulemus
	 */
	public static Nupp valiNupp(String sisend, HashSet<Nupp> mangijaNupud) {
		for (Nupp nupp : mangijaNupud) {
			if (nupp.getNimi().equals(sisend)) return nupp;
		}
		return null;
	}

	/**
	 * Teisendab mängija sisestatud sihtkoha rea ning veeru indeksiks mängulaua maatriksis või et sellist ei leidu
	 *
	 * @param sisend Mängija sisend
	 * @return Järjend, kus esimene koht on kas rida või -1 (vastavalt, kas sihtkoht leidus või mitte) ning teine on veerg
	 */
	public static int[] teisendaSihtkoht(String sisend) {
		int[] tulemus = new int[2];

		try {            // Kontrollib, et sisestatud rida oleks sobival kujul
			tulemus[0] = 8 - Integer.parseInt(sisend.substring(1));
			if (tulemus[0] < 0 || tulemus[0] > 7) tulemus[0] = -1;

		} catch (Exception e) {
			tulemus[0] = -1;
		}

		char[] tahed = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		boolean leitud = false;

		for (int indeks = 0; indeks < 8; indeks++) {    // Kontrollib, et sisestatud veerg oleks sobival kujul
			if (tahed[indeks] == sisend.charAt(0)) {
				tulemus[1] = indeks;
				leitud = true;
				break;
			}
		}

		if (!leitud) tulemus[0] = -1;

		return tulemus;
	}

	/**
	 * Võtab mängija liikumissoovi ning kontrollib, kas soovitud käik on võimalik ning sooritab selle
	 *
	 * @param nupp    Mängija valitud nupp
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastas mängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean kontroll(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		String nimi = nupp.getNimi().substring(0, nupp.getNimi().length() - 1);        // Võtab nupult nupu tüübi ilma numbrita
		return switch (nimi) {
			case "ettur" -> ettur(nupp, rida, veerg, laud, vastane);            // Kasutatakse sobivat kontrolli
			case "vanker" -> risti(nupp, rida, veerg, laud, vastane);
			case "ratsu" -> ratsu(nupp, rida, veerg, laud, vastane);
			case "oda" -> diagonaal(nupp, rida, veerg, laud, vastane);
			case "lipp" -> risti(nupp, rida, veerg, laud, vastane) || diagonaal(nupp, rida, veerg, laud, vastane);
			case "kuningas" -> kuningas(nupp, rida, veerg, laud, vastane);
			default -> false;
		};
	}

	/**
	 * Kontrollib, kas nuppu saab asetada soovitud ruudule ning teeb seda
	 *
	 * @param nupp    Mängija valitud nupp
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean ruuduKontroll(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		if (laud[rida][veerg] == null) {                                // Kui ruudul ei ole nuppe ees
			liigutaNuppu(nupp, rida, veerg, laud);
			return true;

		} else if (nupp.getVarv() != laud[rida][veerg].getVarv()) {    // Kui ruudul on vastase nupp
			kaotaNupp(vastane, nupp.getAsukohtx(), veerg, laud);
			liigutaNuppu(nupp, rida, veerg, laud);
			return true;
		}

		return false;
	}

	/**
	 * Liigutab nupu soovitud ruudule
	 *
	 * @param nupp  Mängija valitud nupp
	 * @param rida  Sihtkoha rida
	 * @param veerg Sihtkoha veerg
	 * @param laud  Mängulaud
	 */
	public static void liigutaNuppu(Nupp nupp, int rida, int veerg, Nupp[][] laud) {
		laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;    // Kustutab nupu eelmiselt kohale
		laud[rida][veerg] = nupp;                                // Viib uuele kohale
		nupp.setAsukohtx(rida);                                    // Uuendab nupu andmeid
		nupp.setAsukohty(veerg);

		for (Nupp[] nupud : laud) {            // Paneb kõik eelnevat en passant lipud kinni
			for (Nupp nupp1 : nupud) {
				if (nupp1 != null) nupp1.setEnPassant(false);
			}
		}

		if (nupp.kasPoleLiikunud()) {            // Lisab en passant lipu
			nupp.setKasLiikunud(true);
			nupp.setEnPassant(true);
		}


	}

	/**
	 * Kaotab vastase nupu, kui mängija soovis seda rünnata
	 *
	 * @param vastane Vastasmängija
	 * @param rida    Vastasmängija nupu rida
	 * @param veerg   Vastasmängija nupu veerg
	 * @param laud    Mängulaud
	 */
	public static void kaotaNupp(Mangija vastane, int rida, int veerg, Nupp[][] laud) {
		vastane.getNupud().remove(laud[rida][veerg]);        // Kustutab laualt
		laud[rida][veerg] = null;                            // Kustutab vastase nuppude seast
	}

	/**
	 * Kontrollib etturi käigu sobivust ning sooritab selle
	 *
	 * @param nupp    Mängija valitud ettur
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean ettur(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {

		if (nupp.getAsukohty() == veerg) {            // Kui mängija tahtis liikuda otse
			if (laud[rida][veerg] != null) {           // Kontrollib, et sihtkoht oleks tühi
				return false;
			}

			if (nupp.getAsukohtx() + 1 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 1 == rida && nupp.getVarv() == 'v') {    // Kontrollib, kas liiguti ühe võrra
				liigutaNuppu(nupp, rida, veerg, laud);
				etturiMuutus(nupp, rida, laud);
				return true;
			}

			if (nupp.getAsukohtx() + 2 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 2 == rida && nupp.getVarv() == 'v') {    // Kontrollib kahe võrra liikumist
				if (nupp.kasPoleLiikunud()) {
					liigutaNuppu(nupp, rida, veerg, laud);
					etturiMuutus(nupp, rida, laud);
					return true;
				}
			}


		} else if (nupp.getAsukohty() == veerg + 1 || nupp.getAsukohty() == veerg - 1) {        // Kontrollib diagonaalis liikumist
			// Kontrollib vastase nuppu rünnakut
			if (nupp.getAsukohtx() + 1 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 1 == rida && nupp.getVarv() == 'v') {
				if (laud[rida][veerg] != null) {                                    // Tavaline rünnak
					if (ruuduKontroll(nupp, rida, veerg, laud, vastane)) {
						etturiMuutus(nupp, rida, laud);
						return true;
					}


				} else {                                                    // En passant
					Nupp korvalNupp = laud[nupp.getAsukohtx()][veerg];
					if (korvalNupp != null) {                                // Kontrollib, et etturi kõrval oleks vastase ettur
						if (korvalNupp.getNimi().substring(0, korvalNupp.getNimi().length() - 1).equals("ettur")
								&& korvalNupp.getVarv() != nupp.getVarv()) {

							if (korvalNupp.isEnPassant()) {        // Kontrollib en passanti
								kaotaNupp(vastane, nupp.getAsukohtx(), veerg, laud);
								liigutaNuppu(nupp, rida, veerg, laud);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Kui ettur on jõudnud mängulaua lõppu, siis pakutase selle välja vahetamist
	 *
	 * @param nupp Ettur
	 * @param rida Etturi rida
	 * @param laud Mängulaud
	 */
	public static void etturiMuutus(Nupp nupp, int rida, Nupp[][] laud) {
		Scanner sc;

		if (rida == 0 || rida == 7) {        // Kui ettur on viimasel real

			while (true) {                    // Küsib vastaselt sisendit
				System.out.println("Vali nupp (vanker, ratsu, oda, lipp):");
				sc = new Scanner(System.in);
				ArrayList<String> valikud = new ArrayList<>(Arrays.asList("vanker", "ratsu", "oda", "lipp"));
				if (valikud.contains(sc.toString())) {
					break;
				}
				System.out.println("Vale sisend!!!");
			}


			for (int i = 0; i < 10; i++) {        // Otsib nupule sobivat indeksit, millega seda kuvada
				boolean sobivNimi = true;
				for (Nupp[] nupud : laud) {
					for (Nupp nupp1 : nupud) {
						if (nupp1.getNimi().equals(sc + Integer.toString(i))) {        // Kontrollib, et sama nimega nuppu juba ei oleks
							sobivNimi = false;
							break;
						}
					}
				}

				if (sobivNimi) nupp.setNimi(sc + Integer.toString(i));
			}
		}
	}

	/**
	 * Kontrollib ratsu käigu sobivust ning sooritab selle
	 *
	 * @param nupp    Mängija valitud ratsu
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean ratsu(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		int kontrollitavRida = nupp.getAsukohtx() + 2;        // Esimene ruut, mida kontrollitakse
		int kontrollitavVeerg = nupp.getAsukohtx() + 1;
		int veeruSuund = 1;                                    // Kontrollimis suund
		int reaSuund = 1;

		for (int i = 0; i < 8; i++) {                                // Kontrollib ratsu kõiki võimalike liikumiskohti
			if (rida == kontrollitavRida && veerg == kontrollitavVeerg) {    // Ratsu liigub mööda rombi (välja arvatud tipud)
				ruuduKontroll(nupp, rida, veerg, laud, vastane);
			}

			kontrollitavRida += reaSuund;
			kontrollitavVeerg += veeruSuund;

			if (i == 1) {                            // Suuna muutused, kui kontrollitakse järgmist külge
				veeruSuund = -1;
				kontrollitavRida += reaSuund;
				kontrollitavVeerg += veeruSuund;
			}

			if (i == 3) {
				reaSuund = -1;
				kontrollitavRida += reaSuund;
				kontrollitavVeerg += veeruSuund;
			}

			if (i == 5) {
				veeruSuund = 1;
				kontrollitavRida += reaSuund;
				kontrollitavVeerg += veeruSuund;
			}

		}
		return false;
	}

	/**
	 * Kontrollib kuninga käigu sobivust ning sooritab selle
	 *
	 * @param nupp    Mängija valitud kuningas
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean kuningas(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		int kontrollitavRida = nupp.getAsukohtx() + 1;            // Esimene kontrollitav ruut
		int kontrollitavVeerg = nupp.getAsukohtx() + 1;
		int veeruSuund = 0;                                        // Kontrollimissuund
		int reaSuund = 1;

		for (int i = 0; i < 9; i++) {                                        // Kontrollib kõiki kuninga käike
			if (rida == kontrollitavRida && veerg == kontrollitavVeerg) {        // Kuninga liikumine on ruut ehk kontrollitakse mööda ruutu
				ruuduKontroll(nupp, rida, veerg, laud, vastane);
			}

			kontrollitavRida += reaSuund;
			kontrollitavVeerg += veeruSuund;

			if (i == 2) {                    // Suuna muutus, kui kontrollitakse järgmist külge
				veeruSuund = -1;
				reaSuund = 0;
			}

			if (i == 4) {
				veeruSuund = 0;
				reaSuund = -1;
			}

			if (i == 6) {
				veeruSuund = 1;
				reaSuund = 0;
			}

		}

		int nupuRida = nupp.getAsukohtx();
		// Kontrollib musta kuninga vangerdust
		if (rida == nupuRida && veerg == 2 && nupp.kasPoleLiikunud() && laud[nupuRida][0] != null) {
			if (laud[nupuRida][0].kasPoleLiikunud() && laud[nupuRida][1] == null && laud[nupuRida][2] == null && laud[nupuRida][3] == null) {

				ruuduKontroll(nupp, rida, veerg, laud, vastane);
				return ruuduKontroll(laud[nupuRida][0], rida, 3, laud, vastane);
			}
		}
		// Kontrollib valge kuninga vangerdust
		if (rida == nupuRida && veerg == 6 && nupp.kasPoleLiikunud() && laud[nupuRida][7] != null) {
			if (laud[nupuRida][7].kasPoleLiikunud() && laud[nupuRida][6] == null && laud[nupuRida][5] == null) {

				ruuduKontroll(nupp, rida, veerg, laud, vastane);
				return ruuduKontroll(laud[nupuRida][7], rida, 5, laud, vastane);
			}
		}

		return true;
	}

	/**
	 * Kontrollib risti liikuva nupu käigu sobivust ning sooritab selle
	 *
	 * @param nupp    Mängija valitud nupp
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean risti(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		if (nupp.getAsukohtx() == rida && nupp.getAsukohty() != veerg) {	// Kui liigutamine oli pikki rida
			int suund = (veerg > nupp.getAsukohty()) ? 1 : -1;        		// Määrab liikumissuuna

			for (int i = suund; i < Math.abs(veerg - nupp.getAsukohty()); i += suund) {  // Kontrollib, kas tee sihtkohta on vaba
				if (laud[rida][nupp.getAsukohty() + i] != null) {
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane);
		}


		if (nupp.getAsukohtx() != rida && nupp.getAsukohty() == veerg) { 	// Kui liigutamine oli pikki veergu
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;        		// Määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) { 	// Kontrollib, kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx() + i][veerg] != null) {
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane);
		}

		return false;
	}

	/**
	 * Kontrollib diagonaalis liikuva nupu käigu sobivust ning sooritab selle
	 *
	 * @param nupp    Mängija valitud nupp
	 * @param rida    Sihtkoha rida
	 * @param veerg   Sihtkoha veerg
	 * @param laud    Mängulaud
	 * @param vastane Vastasmängija
	 * @return Kas käiku sai sooritada
	 */
	public static boolean diagonaal(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane) {
		if (nupp.getAsukohtx() - nupp.getAsukohty() == rida - veerg && nupp.getAsukohtx() != rida) {	// Kui liikumine oli pikki peadiagonaali
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;        						// Määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) {     // Kontrollib, kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i] != null) {
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane);
		}

		if (nupp.getAsukohtx() + nupp.getAsukohty() == rida + veerg && nupp.getAsukohtx() != rida) {    // Kui liikumine oli pikki kõrvaldiagonaali
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;       						// Määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) {      // Kontrollib, kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i] != null) {
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane);
		}


		return false;
	}

}
