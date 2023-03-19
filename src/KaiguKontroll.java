import java.util.Arrays;

public class KaiguKontroll {

	public static Nupp valiNupp(String sisend, Nupp[] mangijaNupud){
		for (Nupp nupp : mangijaNupud) {
			if (nupp.getNimi().equals(sisend)){
				return nupp;
			}
		}
		return null;
	}

	public static int[] teisendaSihtkoht(String sisend){
		int [] tulemus = new int[2];
		char[] tahed = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

		for (int indeks = 0; indeks < 8; indeks++) {
			if (tahed[indeks] == sisend.charAt(0)){
				tulemus[0] = indeks;
				break;
			}
		}

		tulemus[1] = Integer.parseInt(sisend.substring(1)) - 1;
		return tulemus;
	}

	public static boolean kontroll(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		String nimi = nupp.getNimi().substring(0,nupp.getNimi().length()-1);

		return switch (nimi) {
			case "ettur" -> ettur(nupp, rida, veerg, laud);
			case "vanker" -> vanker(nupp, rida, veerg, laud);
			case "ratsu" -> ratsu(nupp, rida, veerg, laud);
			case "oda" -> oda(nupp, rida, veerg, laud);
			case "lipp" -> lipp(nupp, rida, veerg, laud);
			case "kuningas" -> kuningas(nupp, rida, veerg, laud);
			default -> false;
		};
	}

	public static boolean ettur(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean vanker(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean ratsu(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean oda(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean lipp(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean kuningas(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean risti(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

	public static boolean diagonaal(Nupp nupp, int rida, int veerg, Nupp[][] laud){
		return true;
	}

}
