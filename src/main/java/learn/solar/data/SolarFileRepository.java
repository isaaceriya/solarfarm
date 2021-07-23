package learn.solar.data;

import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SolarFileRepository implements SolarRepository{
    private final String filePath;
    private String section[] = {"Main", "The ridge", "Flats"};
    public SolarFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Solar> findAll() throws DataAccessException {
        ArrayList<Solar> result = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine();
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                String[] fields = line.split(",", -1);
                if(fields.length == 7){
                    Solar solar = new Solar();
                    solar.setSolarId(Integer.parseInt(fields[0]));
                    solar.setSection(fields[1]);
                    solar.setRow(Integer.parseInt(fields[2]));
                    solar.setColumn(Integer.parseInt(fields[3]));
                    solar.setYear(Integer.parseInt(fields[4]));
                    solar.setMaterial(SolarMaterial.valueOf(fields[5]));
                    solar.setIsTracking(fields[6]);
                    result.add(solar);
                }
            }
        }catch(FileNotFoundException ex){
            // okay to ignore.
        }
        catch (IOException ex){
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return result;
    }

    @Override
    public List<Solar> findBySection(String section) throws DataAccessException {
        ArrayList<Solar> result = new ArrayList<>();
        for (Solar solar : findAll()) {
            if (solar.getSection().equalsIgnoreCase(section)) {
                result.add(solar);
            }
        }
        return result;
    }

    @Override
    public Solar findById(int solarId) throws DataAccessException {
        for(Solar solar : findAll()){
            if(solar.getSolarId()== solarId){
                return solar;
            }
        } return null;
    }


    /*public List<Solar> findBySection(String solarSection) throws  DataAccessException{
        ArrayList<Solar> result = new ArrayList<>();
        for(Solar solar : findAll()){
            for(int i = 0; i < section.length; i++ )
            if(section[i] == solarSection){
                result.add(solar);
            }
        } return result;
    }

    @Override
    public List<Solar>  findBySection(String solarSection) throws DataAccessException {
        ArrayList<Solar> result = new ArrayList<>();
        for(Solar solar : findAll()){
            if(solar.getSection()== solarSection){
                result.add(solar);
            }
        }
        return result;
    }*/

    @Override
    public List<Solar> findByMaterial(SolarMaterial material) throws DataAccessException {
        ArrayList<Solar> result = new ArrayList<>();
        for(Solar solar : findAll()){
            if(solar.getMaterial()== material){
                result.add(solar);
            }
        }
        return result;
    }

    @Override
    public Solar add(Solar solar) throws DataAccessException {
        // grab all solars

        List<Solar> all = findAll();

        // create next id
        int nextId = 0;
        for(Solar s : all){
            nextId = Math.max(nextId, s.getSolarId());
        }
        nextId++;
        solar.setSolarId(nextId);
        // add new solar to all
        all.add(solar);
        writeAll(all);

        return solar;
    }



    @Override
    public boolean update(Solar solar) throws DataAccessException {
        List<Solar> all = findAll();
        for(int i= 0; i < all.size(); i++){
            if(all.get(i).getSolarId() == solar.getSolarId()){
                all.set(i, solar);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int solarId) throws DataAccessException {
        List<Solar> all = findAll();
        for(int i= 0; i < all.size(); i++){
            if(all.get(i).getSolarId() == solarId){
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Solar> solars) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filePath)){
            writer.println("solarId,section,row,column,year,material,isTracking"); // print header
            for(Solar s : solars){
                writer.println(serialize(s));
            }
        }catch(IOException ex){
            // exception
            throw new DataAccessException(ex.getMessage(), ex);

        }
    }

    private String serialize(Solar solar){
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                solar.getSolarId(),
                solar.getSection(),
                solar.getRow(),
                solar.getColumn(),
                solar.getYear(),
                solar.getMaterial(),
                solar.getIsTracking());
    }

}
