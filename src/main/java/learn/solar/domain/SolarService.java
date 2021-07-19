package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.SolarRepository;
import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarService{
    private final SolarRepository repository;

    public SolarService(SolarRepository repository){
        this.repository = repository;
    }

    public List<Solar> findByMaterial(SolarMaterial material) throws DataAccessException {
        return repository.findByMaterial(material);
    }




    //add
    //can't be null
    //name required
    //module == 4 astro
    //module with dock == 2 astro, 1 shuttle

    public SolarResult add(Solar solar) throws DataAccessException {
        SolarResult result = validateInputs(solar);
        if(!result.isSuccess()){
            return result;
        }

        Map<SolarMaterial, Integer> counts = countTypes();
        counts.put(solar.getMaterial(), counts.get(solar.getMaterial()) + 1);
        result = validateDomain(counts);
        if(!result.isSuccess()){
            return result;
        }

        Solar s = repository.add(solar);
        result.setPayload(s);

        return result;

    }

    public SolarResult update(Solar solar) throws DataAccessException{
        SolarResult result = validateInputs(solar);
        if(!result.isSuccess()){
            return result;
        }
        Solar existing = repository.findById(solar.getSolarId());
        if(existing == null){
            result.addErrorMessage("Solar Id " + solar.getSolarId() + " not found." );
            return result;
        }

        if(existing.getMaterial() != solar.getMaterial()){
            result.addErrorMessage("Cannot update Material." );
            return result;
        }
        boolean success = repository.update(solar);
        if(!success){
            result.addErrorMessage("Could not find Solar Id " + solar.getSolarId());
        }
        return result;
    }

    public SolarResult deleteById(int solarId) throws DataAccessException{
        SolarResult result = new SolarResult();
        Solar solar = repository.findById(solarId);
        if(solar == null){
            result.addErrorMessage("Could not find Solar Id " + solar);
            return result;
        }

        Map<SolarMaterial, Integer> counts = countTypes();
        counts.put(solar.getMaterial(), counts.get(solar.getMaterial()) - 1);
        result = validateDomain(counts);
        if(!result.isSuccess()){
            return result;
        }

        boolean success = repository.deleteById(solarId);
        if(!success){
            result.addErrorMessage("Could not find Solar Id " + solar);
        }

        return result;

    }

    private Map<SolarMaterial, Integer> countTypes(){
        HashMap<SolarMaterial, Integer> counts = new HashMap<>();
        counts.put(SolarMaterial.POLY, 0);
        counts.put(SolarMaterial.SI, 0);
        counts.put(SolarMaterial.ASI, 0);
        counts.put(SolarMaterial.CDTE, 0);
        counts.put(SolarMaterial.CIGS, 0);
        counts.put(SolarMaterial.CIS, 0);
        try {
            List<Solar> allSolars = repository.findAll();
            for(Solar s: allSolars){
                switch (s.getMaterial()){
                    case POLY:
                        counts.put(SolarMaterial.POLY, counts.get(SolarMaterial.POLY)+1);
                        break;
                    case SI:
                        counts.put(SolarMaterial.SI, counts.get(SolarMaterial.SI)+1);
                        break;
                    case ASI:
                        counts.put(SolarMaterial.ASI, counts.get(SolarMaterial.ASI)+1);
                        break;
                    case CDTE:
                        counts.put(SolarMaterial.CDTE, counts.get(SolarMaterial.CDTE)+1);
                        break;
                    case CIGS:
                        counts.put(SolarMaterial.CIGS, counts.get(SolarMaterial.CIGS)+1);
                        break;
                    case CIS:
                        counts.put(SolarMaterial.CIS, counts.get(SolarMaterial.CIS)+1);
                        break;
                }
            }
        }catch (DataAccessException ex) {

        }
        return counts;
    }

    private SolarResult validateInputs(Solar solar){
        SolarResult result = new SolarResult();
        int row = solar.getRow();
        int column = solar.getColumn();
        if(solar == null){
            result.addErrorMessage("solar cannot be null");
            return result;
        }
        if(solar.getSection() == null || solar.getSection().trim().length() == 0){
            result.addErrorMessage("Section is required");
        }
        if(row > 250){
            result.addErrorMessage("Row is too high to use");
        }

        if(column > 250){
            result.addErrorMessage("Column is too high to use");
        }
        return result;
    }

    private SolarResult validateDomain(Map<SolarMaterial, Integer> counts){

        Solar solar = new Solar();

        int polyCount = counts.get(SolarMaterial.POLY);
        int siCount = counts.get(SolarMaterial.SI);
        int asiCount = counts.get(SolarMaterial.ASI);
        int cdteCount = counts.get(SolarMaterial.CDTE);
        int cigsCount = counts.get(SolarMaterial.CIGS);
        int cisCount = counts.get(SolarMaterial.CIS);
        int row = solar.getRow();
        int column = solar.getColumn();

        SolarResult result = new SolarResult();

        if(polyCount > 10 ||
                siCount > 10 ||
                asiCount > 10 ||
                cdteCount > 10 ||
                cigsCount > 10 ||
                cisCount > 10){
            result.addErrorMessage("No room for an astronaut.");
        }
        return result;

    }
}
