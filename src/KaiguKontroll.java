import java.util.HashSet;

public class KaiguKontroll {

	public static Nupp valiNupp(String sisend, HashSet<Nupp> mangijaNupud){
		for (Nupp nupp : mangijaNupud) {
			if (nupp.getNimi().equals(sisend))
				return nupp;
		}
		return null;
	}


	public static int[] teisendaSihtkoht(String sisend){
		int [] tulemus = new int[2];
		char[] tahed = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		boolean leitud = false;
		for (int indeks = 0; indeks < 8; indeks++) {
			if (tahed[indeks] == sisend.charAt(0)){
				tulemus[0] = indeks;
				leitud = true;
				break;
			}
		}
		if (!leitud)
			tulemus[0] = -1;
		tulemus[1] = 8 - Integer.parseInt(sisend.substring(1));
		return tulemus;
	}


	public static boolean kontroll(Nupp nupp, int veerg, int rida, Nupp[][] laud, Mangija vastane){
			String nimi = nupp.getNimi().substring(0,nupp.getNimi().length()-1);
		return switch (nimi) {
			case "ettur" -> ettur(nupp, rida, veerg, laud, vastane);
			case "vanker" -> risti(nupp, rida, veerg, laud, vastane);
			case "ratsu" -> ratsu(nupp, rida, veerg, laud, vastane);
			case "oda" -> diagonaal(nupp, rida, veerg, laud, vastane);
			case "lipp" -> risti(nupp, rida, veerg, laud, vastane) || diagonaal(nupp, rida, veerg, laud, vastane);
			case "kuningas" -> kuningas(nupp, rida, veerg, laud, vastane);
			default -> false;
		};
	}

	public static boolean ruuduKontroll(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane){
		if (laud[rida][veerg] == null){ 		// kui ruut on tühi
			liigutaNuppu(nupp, rida, veerg, laud);
			return true;
		}else if (nupp.getVarv() != laud[rida][veerg].getVarv()){	// kui ruudul on vastase nupp
			kaotaNupp(vastane, nupp.getAsukohtx(), veerg, laud);
			liigutaNuppu(nupp, rida, veerg, laud);
			return true;
		}

		return false;
	}

	public static void liigutaNuppu(Nupp nupp, int rida, int veerg, Nupp[][] laud){	 // liigutab nuppu laual
		laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
		laud[rida][veerg] = nupp;
		nupp.setAsukohtx(rida);
		nupp.setAsukohty(veerg);

		for (Nupp[] nupud : laud) {			// paneb kõik eelnevad en passant kontrollid kinni
			for (Nupp nupp1 : nupud) {
				if (nupp1 != null)
					nupp1.setEnPassant(false);
			}
		}

		if (!nupp.getKasLiikunud()){		// lisab en passant võimaluse
			nupp.setKasLiikunud(true);
			nupp.setEnPassant(true);
		}


	}

	public static void kaotaNupp(Mangija vastane, int rida, int veerg, Nupp[][] laud) {		// kaotab tapetud nupu
		Nupp nupp = laud[rida][veerg];
		vastane.getNupud().remove(nupp);
		laud[rida][veerg] = null;
	}

	public static boolean ettur(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane){

		if (nupp.getAsukohty() == veerg) {			// kontrollib, kas käik oli otse liikumine
			if (laud[rida][veerg] != null) { 			// Kontrollib, et sihtkoht oleks tühi
				return false;
			}

			if (nupp.getAsukohtx() + 1 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 1 == rida && nupp.getVarv() == 'v'){	// kontrollib kas oli 1 võrra edasi liikumine
				liigutaNuppu(nupp, rida, veerg, laud);
				return true;
			}

			if (nupp.getAsukohtx() + 2 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 2 == rida && nupp.getVarv() == 'v'){	// kontrollib kahekordset liikumist
				if (!nupp.getKasLiikunud()){
					liigutaNuppu(nupp, rida, veerg, laud);
					return true;
				}
			}
		}


		else if (nupp.getAsukohty() == veerg + 1 || nupp.getAsukohty() == veerg - 1){		// kontrollib rünnakut
			if (nupp.getAsukohtx() + 1 == rida && nupp.getVarv() == 'm' || nupp.getAsukohtx() - 1 == rida && nupp.getVarv() == 'v'){
				if (laud[rida][veerg] != null){			// tavaline rünnak
					if (laud[rida][veerg].getVarv() != nupp.getVarv()){			// kohal on vastase nupp
						kaotaNupp(vastane, rida, veerg, laud);
						liigutaNuppu(nupp, rida, veerg, laud);
						return true;
					}
				}

				else {		// en passant kontroll
					Nupp korvalNupp = laud[nupp.getAsukohtx()][veerg];
					if (korvalNupp != null){	// etturi kõrval on nupp
						if (korvalNupp.getNimi().substring(0, korvalNupp.getNimi().length() - 1).equals("ettur")){ 	// kontrollib kas kõrval on ettur
							if (korvalNupp.isEnPassant()){
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


	public static boolean ratsu(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){

		return true;
	}


	public static boolean kuningas(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		return true;
	}

	public static boolean risti(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane){
		if (nupp.getAsukohtx() == rida && nupp.getAsukohty() != veerg){		// kui liigutati pikki rida
			int suund = (veerg > nupp.getAsukohty()) ? 1 : -1;		// määrab liikumissuuna

			for (int i = suund; i < Math.abs(veerg - nupp.getAsukohty()); i += suund) { 		// kontrollib kas tee sihtkohta on vaba
				if (laud[rida][nupp.getAsukohty() + i] != null){
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane);		// kontrollib, kas ruudule saab minna
		}

		if (nupp.getAsukohtx() != rida && nupp.getAsukohty() == veerg){		// kui liigutati pikki veergu
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;		// määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) { 		// kontrollib kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx()+i][veerg] != null){
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane); // kontrollib, kas ruudule saab minna
		}

		return false;
	}

	public static boolean diagonaal(Nupp nupp, int rida, int veerg, Nupp[][] laud, Mangija vastane){
		if (nupp.getAsukohtx() - nupp.getAsukohty() == rida - veerg && nupp.getAsukohtx() != rida){		// kontrollib peadiagonaalil liikumist
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;		// määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) { 		// kontrollib kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx()+i][nupp.getAsukohty() + i] != null){
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane); // kontrollib, kas ruudule saab minna
		}

		if (nupp.getAsukohtx() + nupp.getAsukohty() == rida + veerg && nupp.getAsukohtx() != rida){		// kontrollib kõrvaldiagonaalil liikumist
			int suund = (rida > nupp.getAsukohtx()) ? 1 : -1;		// määrab liikumissuuna

			for (int i = suund; i < Math.abs(rida - nupp.getAsukohtx()); i += suund) { 		// kontrollib kas tee sihtkohta on vaba
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i] != null){
					return false;
				}
			}

			return ruuduKontroll(nupp, rida, veerg, laud, vastane); // kontrollib, kas ruudule saab minna
		}


		return false;
	}

}
