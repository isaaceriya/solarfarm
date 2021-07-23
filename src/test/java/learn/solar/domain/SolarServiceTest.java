package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.SolarRepositoryDouble;
import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarServiceTest {

    SolarService service = new SolarService(new SolarRepositoryDouble());

    @Test
    void shouldFindByMaterial() throws DataAccessException, DataAccessException {
        List<Solar> poly = service.findByMaterial(SolarMaterial.POLY);
        assertNotNull(poly);
        assertEquals(2, poly.size());
    }

    @Test
    void shouldNotAddSolarWithInvalidRowAndColumn() throws DataAccessException{
        SolarResult result = service.add(
                new Solar(0, "Main", 251, 252, 2020, SolarMaterial.POLY, "yes"));
        assertFalse(result.isSuccess());

    }


    @Test
    void shouldBeAbleToAddSolar() throws DataAccessException{
        service.add(new Solar(0, "Main",20,31,2021,SolarMaterial.POLY, "yes"));
        SolarResult result = service.add(
                new Solar(0, "Flats",2,3,2020,SolarMaterial.SI, "yes"));
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }


    @Test
    void shouldNotUpdateEmptyName() throws  DataAccessException{
        SolarResult result = service.update(
                new Solar(3, "  ",2,3,2020,SolarMaterial.SI, "yes"));
        assertFalse(result.isSuccess());
    }




}