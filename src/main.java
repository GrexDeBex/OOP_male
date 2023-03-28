import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Mangija mangija1 = new Mangija();
		Mangija mangija2 = new Mangija();
		Mangija.seaMängijateVärvid(mangija1, mangija2);

		Mangija kaiguTegija, vastane;
		Mangulaud mangulaud;
		if (mangija1.getVarv() == 'v'){
			kaiguTegija = mangija1;
			vastane = mangija2;
			mangulaud = new Mangulaud(mangija2, mangija1);
		}else {
			kaiguTegija = mangija2;
			vastane = mangija1;
			mangulaud = new Mangulaud(mangija1, mangija2);
		}

		Mangulaud.reeglid(mangija1, mangija2);
		mangulaud.väljastaLaud();


		//Põhi mängu tsükkel
		//Tsükli lõpetamiseks kirjuta lõpeta
		while (true) {
			System.out.println(kaiguTegija.getVarv() + " käib!");
			System.out.println("Sisesta oma käik: ");
			Scanner sc = new Scanner(System.in);
			String[] sisend = sc.nextLine().split(" ");

			if (Objects.equals(sisend[0], "lõpeta")){
				break;
			}

			Nupp valitudNupp = KaiguKontroll.valiNupp(sisend[0], kaiguTegija.nupud);
			if (valitudNupp == null){
				System.out.println("Sellist nuppu pole laual või olemas");
				continue;
			}

			int[] sihtkoht = KaiguKontroll.teisendaSihtkoht(sisend[1]);
			System.out.println(Arrays.toString(sihtkoht));
			if (sihtkoht[0] == -1 || !KaiguKontroll.kontroll(valitudNupp, sihtkoht[0], sihtkoht[1], mangulaud.laud, kaiguTegija.getVarv(), vastane.getNupud())){
				System.out.println("Vale käik");
				continue;
			}

			for (int i = 0; i < vastane.getNupud().length; i++) {
				if (vastane.getNupud()[i] != null && vastane.getNupud()[i].isEnPassant())
					vastane.getNupud()[i].setEnPassant(false);
			}

			if (kaiguTegija == mangija1) {
				kaiguTegija = mangija2;
				vastane = mangija1;
			}
			else{
				kaiguTegija = mangija1;
				vastane = mangija2;
			}


			mangulaud.väljastaLaud();
		}
	}
}
