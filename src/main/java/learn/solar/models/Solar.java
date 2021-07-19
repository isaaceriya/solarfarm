package learn.solar.models;

public class Solar {
    private int solarId;
    private String section;
    private int row;
    private int column;
    private int year;
    private SolarMaterial material;
    private String isTracking;

    public Solar(){

    }

    public Solar(int solarId, String section, int row, int column, int year, SolarMaterial material, String isTracking) {
        this.section = section;
        this.row = row;
        this.column = column;
        this.year = year;
        this.material = material;
        this.isTracking = isTracking;
    }

    public int getSolarId() {
        return solarId;
    }

    public void setSolarId(int solarId) {
        this.solarId = solarId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SolarMaterial getMaterial() {
        return material;
    }

    public void setMaterial(SolarMaterial material) {
        this.material = material;
    }

    public String getIsTracking() {
        return isTracking;
    }

    public void setIsTracking(String isTracking) {
        this.isTracking = isTracking;
    }
}
