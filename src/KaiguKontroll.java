import java.util.Objects;

public class KaiguKontroll {

	public static Nupp valiNupp(String sisend, Nupp[] mangijaNupud){
		for (Nupp nupp : mangijaNupud) {
			if (nupp == null)
				continue;
			if (nupp.getNimi().equals(sisend))
				return nupp;
		}
		return null;
	}

	public static void kaotaNupp(Nupp nupp, Nupp[] vastaseNupud) {
		for (int i = 0; i < vastaseNupud.length; i++) {
			if (Objects.equals(vastaseNupud[i], nupp)) {
				vastaseNupud[i] = null;
				break;
			}
		}
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

	public static void nuppSööb(Nupp nupp, int rida, int veerg, Nupp[][] laud, Nupp[] vastaseNupud) {
		kaotaNupp(laud[rida][veerg], vastaseNupud);
		laud[rida][veerg] = nupp;
		laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
		nupp.setAsukohtx(rida);
		nupp.setAsukohty(veerg);
	}

	public static boolean mustEtturEdasi(Nupp nupp, int rida, int veerg, Nupp[][] laud) {
		if (rida - nupp.getAsukohtx() == 1 && laud[rida][veerg] == null) {
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			nupp.setAsukohtx(rida);
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = nupp;
			return true;
		}
		if(rida - nupp.getAsukohtx() == 2 && laud[rida][veerg] == null && laud[rida - 1][veerg] == null && nupp.getAsukohtx() == 1) {
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			nupp.setAsukohtx(rida);
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = nupp;
			nupp.setLiikunud();
			nupp.setEnPassant(true);
			return true;
		}
		return false;
	}

	public static boolean mustEtturSoob(Nupp nupp, int rida, int veerg, Nupp[][] laud, Nupp[] vastaseNupud, char varv) {
		if (nupp.getAsukohtx() + 1 == rida && laud[rida][veerg] != null && laud[rida][veerg].getVarv() != varv) {
			nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
			return true;
		}
		if (nupp.getAsukohtx() + 1 == rida && laud[nupp.getAsukohtx()][veerg] != null && laud[rida][veerg] == null
				&& laud[nupp.getAsukohtx()][veerg].isEnPassant() && laud[nupp.getAsukohtx()][veerg].getVarv() != varv) {
			kaotaNupp(laud[nupp.getAsukohtx()][veerg], vastaseNupud);
			laud[rida][veerg] = nupp;
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			laud[nupp.getAsukohtx()][veerg] = null;
			nupp.setAsukohtx(rida);
			nupp.setAsukohty(veerg);
			return true;
		}
			return false;
	}

	public static boolean valgeEtturEdasi(Nupp nupp, int rida, int veerg, Nupp[][] laud) {
		if (nupp.getAsukohtx() - rida == 1 && laud[rida][veerg] == null) {
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			nupp.setAsukohtx(rida);
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = nupp;
			return true;
		}
		if(nupp.getAsukohtx() - rida == 2 && laud[rida][veerg] == null && laud[rida + 1][veerg] == null && nupp.getAsukohtx() == 6) {
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			nupp.setAsukohtx(rida);
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = nupp;
			nupp.setLiikunud();
			nupp.setEnPassant(true);
			return true;
		}
		return false;
	}
	public static boolean valgeEtturSoob(Nupp nupp, int rida, int veerg, Nupp[][] laud, Nupp[] vastaseNupud, char varv) {
		if (nupp.getAsukohtx() - 1 == rida && laud[rida][veerg] != null && laud[rida][veerg].getVarv() != varv) {
			nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
			return true;
		}
		if (nupp.getAsukohtx() - 1 == rida && laud[nupp.getAsukohtx()][veerg] != null && laud[rida][veerg] == null
				&& laud[nupp.getAsukohtx()][veerg].isEnPassant() && laud[nupp.getAsukohtx()][veerg].getVarv() != varv) {
			kaotaNupp(laud[nupp.getAsukohtx()][veerg], vastaseNupud);
			laud[rida][veerg] = nupp;
			laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
			laud[nupp.getAsukohtx()][veerg] = null;
			nupp.setAsukohtx(rida);
			nupp.setAsukohty(veerg);
			return true;
		}
		return false;
	}

	public static boolean kontroll(Nupp nupp, int veerg, int rida, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
			String nimi = nupp.getNimi().substring(0,nupp.getNimi().length()-1);
		return switch (nimi) {
			case "ettur" -> ettur(nupp, rida, veerg, laud, varv, vastaseNupud);
			case "vanker" -> vanker(nupp, rida, veerg, laud, varv, vastaseNupud);
			case "ratsu" -> ratsu(nupp, rida, veerg, laud, varv, vastaseNupud);
			case "oda" -> oda(nupp, rida, veerg, laud, varv, vastaseNupud);
			case "lipp" -> lipp(nupp, rida, veerg, laud, varv, vastaseNupud);
			case "kuningas" -> kuningas(nupp, rida, veerg, laud, varv, vastaseNupud);
			default -> false;
		};
	}

		public static boolean ettur(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		if (nupp.getAsukohty() == veerg) {
			if (Objects.equals(varv, 'm')) {
				return mustEtturEdasi(nupp, rida, veerg, laud);
			}
			if (Objects.equals(varv, 'v')) {
				return valgeEtturEdasi(nupp, rida, veerg, laud);
			}
		}
		else if (nupp.getAsukohty() == veerg + 1 || nupp.getAsukohty() == veerg - 1){
			if (Objects.equals(varv, 'm')){
				return mustEtturSoob(nupp, rida, veerg, laud, vastaseNupud, varv);
			}
			if (Objects.equals(varv, 'v')){
				return valgeEtturSoob(nupp, rida, veerg, laud, vastaseNupud, varv);
			}
		}
		return false;
	}

	public static boolean vanker(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		return true;
	}

	public static boolean ratsu(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){

		return true;
	}

	public static boolean oda(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		return diagonaal(nupp, rida, veerg, laud, vastaseNupud, varv);
	}

	public static boolean lipp(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		return true;
	}

	public static boolean kuningas(Nupp nupp, int rida, int veerg, Nupp[][] laud, char varv, Nupp[] vastaseNupud){
		return true;
	}

	public static boolean risti(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean diagonaal(Nupp nupp, int rida, int veerg, Nupp[][] laud, Nupp[] vastaseNupud, char varv){

		//Üles vasakule
		if (rida < nupp.getAsukohtx() && veerg < nupp.getAsukohty())
			for (int i = 1; i <= Math.min(nupp.getAsukohtx(), nupp.getAsukohty()); i++) {
				if (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() - i] != null && nupp.getAsukohtx() - i == rida && nupp.getAsukohty() - i == veerg
				&& laud[nupp.getAsukohtx() - i][nupp.getAsukohty() - i].getVarv() != varv){
					nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
					return true;
				}
				if (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() - i] != null && nupp.getAsukohtx() - i != rida && nupp.getAsukohty() - i != veerg
						|| (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() - i] != null && laud[nupp.getAsukohtx() - i][nupp.getAsukohty() - i].getVarv() == varv))
					return false;
				if (nupp.getAsukohtx() - i == rida && nupp.getAsukohty() - i == veerg) {
					laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
					laud[rida][veerg] = nupp;
					nupp.setAsukohty(veerg);
					nupp.setAsukohtx(rida);
					return true;
				}
		}
		//Üles paremale
		if (rida < nupp.getAsukohtx() && veerg > nupp.getAsukohty())
			for (int i = 1; i <= Math.min(nupp.getAsukohtx(), 7 - nupp.getAsukohty()); i++) {
				if (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() + i] != null && nupp.getAsukohtx() - i == rida && nupp.getAsukohty() + i == veerg
						&& laud[nupp.getAsukohtx() - i][nupp.getAsukohty() + i].getVarv() != varv){
					nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
					return true;
				}
				if (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() + i] != null && nupp.getAsukohtx() - i != rida && nupp.getAsukohty() + i != veerg
						|| (laud[nupp.getAsukohtx() - i][nupp.getAsukohty() + i] != null && laud[nupp.getAsukohtx() - i][nupp.getAsukohty() + i].getVarv() == varv))
					return false;
				if (nupp.getAsukohtx() - i == rida && nupp.getAsukohty() + i == veerg) {
					laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
					laud[rida][veerg] = nupp;
					nupp.setAsukohty(veerg);
					nupp.setAsukohtx(rida);
					return true;
				}
		}
		//Alla vasakule
		if (rida > nupp.getAsukohtx() && veerg < nupp.getAsukohty())
			for (int i = 1; i <= Math.min(7 - nupp.getAsukohtx(), nupp.getAsukohty()); i++) {
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i] != null && nupp.getAsukohtx() + i == rida && nupp.getAsukohty() - i== veerg
						&& laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i].getVarv() != varv){
					nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
					return true;
				}
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i] != null && nupp.getAsukohtx() + i != rida && nupp.getAsukohty() - i != veerg
						|| (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i] != null && laud[nupp.getAsukohtx() + i][nupp.getAsukohty() - i].getVarv() == varv))
					return false;
				if (nupp.getAsukohtx() + i == rida && nupp.getAsukohty() - i == veerg) {
					laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
					laud[rida][veerg] = nupp;
					nupp.setAsukohty(veerg);
					nupp.setAsukohtx(rida);
					return true;
				}
		}
		//Alla paremale
		if (rida > nupp.getAsukohtx() && veerg > nupp.getAsukohty())
			for (int i = 1; i <= Math.min(7 - nupp.getAsukohtx(), 7 - nupp.getAsukohty()); i++) {
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i] != null && nupp.getAsukohtx() + i == rida && nupp.getAsukohty() + i== veerg
						&& laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i].getVarv() != varv){
					nuppSööb(nupp, rida, veerg, laud, vastaseNupud);
					return true;
				}
				if (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i] != null && nupp.getAsukohtx() + i != rida && nupp.getAsukohty() + i != veerg
						|| (laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i] != null && laud[nupp.getAsukohtx() + i][nupp.getAsukohty() + i].getVarv() == varv))
					return false;
				if (nupp.getAsukohtx() + i == rida && nupp.getAsukohty() + i == veerg) {
					laud[nupp.getAsukohtx()][nupp.getAsukohty()] = null;
					laud[rida][veerg] = nupp;
					nupp.setAsukohty(veerg);
					nupp.setAsukohtx(rida);
					return true;
				}
		}
		return false;
	}

}
