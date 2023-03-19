import java.util.Objects;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		Mangija mangija1 = new Mangija();
		Mangija mangija2 = new Mangija();
		Mangija.seaMängijateVärvid(mangija1, mangija2);

		Mangija kaiguTegija;
		Mangulaud mangulaud;
		if (mangija1.getVarv() == 'v'){
			kaiguTegija = mangija1;
			mangulaud = new Mangulaud(mangija2, mangija1);
		}else {
			kaiguTegija = mangija2;
			mangulaud = new Mangulaud(mangija1, mangija2);
		}

		Mangulaud.reeglid(mangija1, mangija2);
		mangulaud.väljastaLaud();


		//Põhi mängu tsükkel
		//Tsükli lõpetamiseks kirjuta lõpeta
		while (true) {
			Scanner sc = new Scanner(System.in);
			String[] sisend = sc.nextLine().split(" ");

			if (Objects.equals(sisend[0], "lõpeta")){
				break;
			}

			Nupp valitudNupp = KaiguKontroll.valiNupp(sisend[0], kaiguTegija.nupud);
			if (valitudNupp == null){
				System.out.println("Pole sellist nuppu");
				continue;
			}

			int[] sihtkoht = KaiguKontroll.teisendaSihtkoht(sisend[1]);

			if (!KaiguKontroll.kontroll(valitudNupp, sihtkoht[0], sihtkoht[1], mangulaud.laud)){
				System.out.println("Vale käik");
				continue;
			}

			mangulaud.väljastaLaud();
		}
	}
}
