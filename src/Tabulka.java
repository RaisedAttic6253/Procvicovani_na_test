import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

public class Tabulka extends AbstractTableModel {
    private List<Zamestnanec> zamestnanci;

    public Tabulka(List<Zamestnanec> zamestnanci) {
        this.zamestnanci = zamestnanci;
    }

    @Override
    public int getRowCount() {
        return zamestnanci.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Zamestnanec zamestnanec = zamestnanci.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return zamestnanec.getJmeno();
            case 1:
                return zamestnanec.getPrijmeni();
            case 2:
                return zamestnanec.getNarozeni();
            case 3:
                return zamestnanec.isPojisteni();
            case 4:
                return zamestnanec.getHodnoceni();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Jméno";
            case 1:
                return "Příjmení";
            case 2:
                return "Datum narození";
            case 3:
                return "Pojištění";
            case 4:
                return "Hodnocení";
            default:
                return null;
        }
    }
    public void pridejZamestnance(Zamestnanec zamestnanec) {
        fireTableDataChanged();
    }
}
