import java.util.Arrays;

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
		for (int rida = 0; rida < 2; rida++) {
			for (int veerg = 0; veerg < 8; veerg++) {
				if (rida == 1) {
					//Siin number ka etturi lõppu, sest kuidagi on vaja neid eristada.
					Nupp mustEttur = new Nupp("ettur" + (veerg + 1), "m");
					laud[rida][veerg] = mustEttur;
					Nupp valgeEttur = new Nupp("ettur" + (veerg + 1), "v");
					laud[6][veerg] = valgeEttur;
				}
				//mustade nuppude lauale asetamine (mitte ettur)
				if (rida == 0 && (veerg == 0 || veerg == 7)) {
					Nupp mustVanker = new Nupp("vanker", "m");
					laud[rida][veerg] = mustVanker;
					Nupp valgeVanker = new Nupp("vanker", "v");
					laud[7][veerg] = valgeVanker;
				}
				if (rida == 0 && (veerg == 1 || veerg == 6)) {
					Nupp mustHobu = new Nupp("hobune", "m");
					laud[rida][veerg] = mustHobu;
					Nupp valgeHobu = new Nupp("hobune", "v");
					laud[7][veerg] = valgeHobu;
				}
				if (rida == 0 && (veerg == 2 || veerg == 5)) {
					Nupp mustOda = new Nupp("oda", "m");
					laud[rida][veerg] = mustOda;
					Nupp valgeOda = new Nupp("oda", "v");
					laud[7][veerg] = valgeOda;
				}
				if (rida == 0 && veerg == 3) {
					Nupp mustLipp = new Nupp("lipp", "m");
					laud[rida][veerg] = mustLipp;
					Nupp valgeLipp = new Nupp("lipp", "v");
					laud[7][veerg] = valgeLipp;
				}
				if (rida == 0 && veerg == 4) {
					Nupp mustKuningas = new Nupp("kuningas", "m");
					laud[rida][veerg] = mustKuningas;
					Nupp valgeKuningas = new Nupp("kuningas", "v");
					laud[7][veerg] = valgeKuningas;
				}
			}
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
				Käigu näited: (oda b6), (ettur c3), (kuningas a8)\s
				Kui ettur jõuab mängulaua lõppu, siis tuleb väikeste tähtedega kirjutada mis nuppu soovitakse.""");

		Mangija mängija1 = new Mangija();
		Mangija mängija2 = new Mangija();
		seaMängijateVärvid(mängija1, mängija2);
		System.out.println("\nEsimene mängija mängib värviga " + mängija1.getVarv() + " ning teine mängija mängib värviga " + mängija2.getVarv() + ".");
	}
}
