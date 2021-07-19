package learn.solar.data;

import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.util.ArrayList;
import java.util.List;

public class SolarRepositoryDouble implements SolarRepository {
    private ArrayList<Solar> solars = new ArrayList<>();

    public SolarRepositoryDouble(){
        Solar poly = new Solar();
        poly.setSolarId(1);
        poly.setSection("The New One");
        poly.setRow(2);
        poly.setColumn(4);
        poly.setYear(2020);
        poly.setMaterial(SolarMaterial.CIGS);
        poly.setIsTracking("yes");
        solars.add(poly);
        solars.add(new Solar(2, "Main", 5, 1,2020,SolarMaterial.POLY,"yes"));
        solars.add(new Solar(3, "The Ridge", 6, 9,2020,SolarMaterial.POLY,"yes"));
        solars.add(new Solar(4, "Flats", 21, 2,2020,SolarMaterial.SI,"yes"));
    }

    @Override
    public List<Solar> findAll() throws DataAccessException {
        return new ArrayList<>(solars);
    }

    @Override
    public Solar findById(int solarId) throws DataAccessException {
        for(Solar s: solars){
            if(s.getSolarId() == solarId){
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Solar> findByMaterial(SolarMaterial material) throws DataAccessException {
        ArrayList<Solar> result = new ArrayList<>();
        for(Solar s : solars){
            if(material == s.getMaterial()){
                result.add(s);
            }
        }
        return result;
    }

    /*@Override
    public List<Solar> findBySection(String solarSection) throws  DataAccessException{
        ArrayList<Solar> result = new ArrayList<>();
        for(Solar s : solars){
            if( solarSection  == s.getSection()){
                result.add(s);
            }
        }
        return result;

    }*/

    @Override
    public Solar add(Solar solar) throws DataAccessException {
        solars.add(solar);
        return solar;
    }

    @Override
    public boolean update(Solar solar) throws DataAccessException {
        return true;
    }

    @Override
    public boolean deleteById(int solarId) throws DataAccessException {
        return true;
    }
}
