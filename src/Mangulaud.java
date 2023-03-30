/**
 * Mängulaua klass
 * <p>
 * Klassi abil luuakse mängulaud ning sisaldab mängu üldfunktsioone
 */


public class Mangulaud {
	final private Nupp[][] laud;                        // Mängulaua maatriks


	public Mangulaud(Mangija mangijaMust, Mangija mangijaValge) {
		Nupp[][] laud = new Nupp[8][8];
		asetaNupudLauale(laud, mangijaMust, mangijaValge);

		this.laud = laud;
	}

	public Nupp[][] getLaud() {
		return laud;
	}


	/**
	 * Loob kõik vajalikud nupud, lisab need lauale ning mängijate nuppude listidesse
	 *
	 * @param laud         Mängulaud
	 * @param mangijaMust  Mustadega mängija
	 * @param mangijaValge Valgetega mängija
	 */
	public static void asetaNupudLauale(Nupp[][] laud, Mangija mangijaMust, Mangija mangijaValge) {
		String[] nimetused = {"vanker1", "ratsu1", "oda1", "lipp1", "kuningas1", "oda2", "ratsu2", "vanker2"};    // Kõik võimalikud nupud

		for (int veerg = 0; veerg < 8; veerg++) {            // Käib läbi iga veeru

			// Must ettur
			Nupp mustEttur = new Nupp("ettur" + (veerg + 1), 'm', 1, veerg);
			mangijaMust.getNupud().add(mustEttur);
			laud[1][veerg] = mustEttur;

			// Valge ettur
			Nupp valgeEttur = new Nupp("ettur" + (veerg + 1), 'v', 6, veerg);
			mangijaValge.getNupud().add(valgeEttur);
			laud[6][veerg] = valgeEttur;

			// Must nupp veerus
			Nupp mustNupp = new Nupp(nimetused[veerg], 'm', 0, veerg);
			mangijaMust.getNupud().add(mustNupp);
			laud[0][veerg] = mustNupp;

			// Valge nupp veerus
			Nupp valgeNupp = new Nupp(nimetused[veerg], 'v', 7, veerg);
			mangijaValge.getNupud().add(valgeNupp);
			laud[7][veerg] = valgeNupp;

		}
	}

	/**
	 * Väljastab mängulaua loetaval kujul
	 */
	public void väljastaLaud() {
		System.out.println("  |" + "-----".repeat(8) + "-------" + "|");
		for (int i = 0; i < 8; i++) {
			System.out.print((8 - i) + " | ");
			for (int j = 0; j < 8; j++) {
				if (laud[i][j] == null) System.out.print("    | ");
				else {
					String nimi = laud[i][j].getNimi();
					char värv = laud[i][j].getVarv();
					System.out.print(nimi.substring(0, 1).toUpperCase() + nimi.substring(nimi.length() - 1).toUpperCase() + värv + " | ");
				}
			}
			System.out.print("\n");
			System.out.println("  |" + "-----".repeat(8) + "-------" + "|");
		}
		System.out.println("     a     b     c     d     e     f     g     h");
	}

	/**
	 * Väljastab reeglid
	 */
	public static void reeglid(Mangija mängija1, Mangija mängija2) {
		System.out.println("Male!");
		System.out.println("Reeglid leiab leheküljelt https://brainking.com/ee/GameRules?tp=1\n");
		System.out.println("""
				Juhised mängimiseks:\s
				
				Liikumiseks tuleb kirjutada konsooli nupu nimetus ning tühikuga eraldada nimest käigu koht.\s
				Tähised "m" ja "v" tähistavad värvi, kus m on must ning v on valge.\s
				Käigu näited: (oda1 b6), (ettur5 c3), (kuningas1 a8)\s
				Kui ettur jõuab mängulaua lõppu, siis tuleb väikeste tähtedega kirjutada mis nuppu soovitakse.
				
				Kui mängija paneb vastase kuninga tule alla, siis peab ta sellest vastast teatama.
				Viigiga lõppeb mäng ainult siis, kui mängijad selle ise kokkue lepivad.""");
		System.out.println("\nEsimene mängija mängib värviga " + mängija1.getVarv() + " ning teine mängija mängib värviga " + mängija2.getVarv() + ".");
	}
}
