import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class main {

	public static void seaMängijateVärvid(Mangija mängija1, Mangija mängija2) {
		if (Math.random() < 0.50) {
			mängija1.setVarv("m");
			mängija2.setVarv("v");
		}
		else {
			mängija1.setVarv("v");
			mängija2.setVarv("m");
		}


	}
	public static void asetaNupudLauale(Nupp[][] laud) {
			for (int veerg = 0; veerg < 8; veerg++) {
				//Siin number ka etturi lõppu, sest kuidagi on vaja neid eristada.
					Nupp mustEttur = new Nupp("ettur" + (veerg + 1), "m", 1, veerg);
					laud[1][veerg] = mustEttur;
					Nupp valgeEttur = new Nupp("ettur" + (veerg + 1), "v", 6, veerg);
					laud[6][veerg] = valgeEttur;
				//Ülejäänud nupud
				if (veerg == 0) {
					Nupp mustVanker = new Nupp("vanker1", "m", 0, veerg);
					laud[0][veerg] = mustVanker;
					Nupp valgeVanker = new Nupp("vanker1", "v", 7, veerg);
					laud[7][veerg] = valgeVanker;
				}
				if (veerg == 7) {
					Nupp mustVanker = new Nupp("vanker2", "m", 0, veerg);
					laud[0][veerg] = mustVanker;
					Nupp valgeVanker = new Nupp("vanker2", "v", 7, veerg);
					laud[7][veerg] = valgeVanker;
				}
				if (veerg == 1) {
					Nupp mustHobu = new Nupp("ratsu1", "m", 0, veerg);
					laud[0][veerg] = mustHobu;
					Nupp valgeHobu = new Nupp("ratsu1", "v", 7, veerg);
					laud[7][veerg] = valgeHobu;
				}
				if (veerg == 6) {
					Nupp mustHobu = new Nupp("ratsu2", "m", 0, veerg);
					laud[0][veerg] = mustHobu;
					Nupp valgeHobu = new Nupp("ratsu2", "v", 7, veerg);
					laud[7][veerg] = valgeHobu;
				}
				if (veerg == 2) {
					Nupp mustOda = new Nupp("oda1", "m", 0, veerg);
					laud[0][veerg] = mustOda;
					Nupp valgeOda = new Nupp("oda1", "v", 7, veerg);
					laud[7][veerg] = valgeOda;
				}
				if (veerg == 3) {
					Nupp mustLipp = new Nupp("lipp", "m", 0, veerg);
					laud[0][veerg] = mustLipp;
					Nupp valgeLipp = new Nupp("lipp", "v", 7, veerg);
					laud[7][veerg] = valgeLipp;
				}
				if (veerg == 4) {
					Nupp mustKuningas = new Nupp("kuningas", "m", 0, veerg);
					laud[0][veerg] = mustKuningas;
					Nupp valgeKuningas = new Nupp("kuningas", "v", 7, veerg);
					laud[7][veerg] = valgeKuningas;
				}
				if (veerg == 5) {
					Nupp mustOda = new Nupp("oda2", "m", 0, veerg);
					laud[0][veerg] = mustOda;
					Nupp valgeOda = new Nupp("oda2", "v", 7, veerg);
					laud[7][veerg] = valgeOda;
				}
		}
	}
	
	public static void väljastaLaud(Nupp[][] laud){
		System.out.println("|" + "-----".repeat(8) + "-------" + "|");
		for (int i = 0; i < 8; i++) {
			System.out.print("| ");
			for (int j = 0; j < 8; j++) {
				if (laud[i][j] == null)
					System.out.print("    | ");
				else {
					String nimi = laud[i][j].getNimi();
					String värv = laud[i][j].getVarv();
					System.out.print(nimi.substring(0,1).toUpperCase() + nimi.substring(nimi.length() - 1).toUpperCase() + värv + " | ");
				}
			}
			System.out.print("\n");
			System.out.println("|" + "-----".repeat(8) +  "-------" + "|");
		}
	}

	public static void main(String[] args) {
		Mangulaud mängulaud = new Mangulaud();
		asetaNupudLauale(mängulaud.laud);
		System.out.println("Male!");
		System.out.println("""
				Juhised mängimiseks:\s
				Liikumiseks tuleb kirjutada konsooli nupu nimetus ning tühikuga eraldada nimest käigu koht.\s
				Tähised "m" ja "v" tähistavad värvi, kus m on must ning v on valge.\s
				Käigu näited: (oda1 b6), (ettur5 c3), (kuningas a8)\s
				Kui ettur jõuab mängulaua lõppu, siis tuleb väikeste tähtedega kirjutada mis nuppu soovitakse.""");

		Mangija mängija1 = new Mangija();
		Mangija mängija2 = new Mangija();
		seaMängijateVärvid(mängija1, mängija2);
		System.out.println("\nEsimene mängija mängib värviga " + mängija1.getVarv() + " ning teine mängija mängib värviga " + mängija2.getVarv() + ".");
		boolean protsess = true;
		while (protsess) {
			väljastaLaud(mängulaud.laud);
			Scanner sc = new Scanner(System.in);
			String sisend = sc.nextLine();
			if (Objects.equals(sisend, "Lõpeta"))
				protsess = false;
		}
	}
}
