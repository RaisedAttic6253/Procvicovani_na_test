import java.time.LocalDate;

public class Zamestnanec {
    private String jmeno;
    private String prijmeni;
    private boolean pojisteni;
    private LocalDate narozeni;
    private int hodnoceni;

    public Zamestnanec(String jmeno, String prijmeni, boolean pojisteni, LocalDate narozeni, int hodnoceni) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.pojisteni = pojisteni;
        this.narozeni = narozeni;
        this.hodnoceni = hodnoceni;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public boolean isPojisteni() {
        return pojisteni;
    }

    public void setPojisteni(boolean pojisteni) {
        this.pojisteni = pojisteni;
    }

    public LocalDate getNarozeni() {
        return narozeni;
    }

    public void setNarozeni(LocalDate narozeni) {
        this.narozeni = narozeni;
    }

    public int getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(int hodnoceni) {
        this.hodnoceni = hodnoceni;
    }
}
