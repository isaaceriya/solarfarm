package learn.solar.data;

import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarFileRepositoryTest {

    private static final String SEED_PATH = "./data/solar-seed.csv";
    private static final String TEST_PATH = "./data/solar-test.csv";

    private SolarFileRepository repository = new SolarFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(
                Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);

    }

    @Test
    void shouldFindFourSolars() throws DataAccessException {
        List<Solar> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(6, actual.size());
    }

    @Test
    void shouldFindExistingSolarSection() throws DataAccessException {
        Solar main = repository.findById(4);
        assertNotNull(main);
        assertEquals("Main", main.getSection());
    }

    @Test
    void shouldNotFindMissingSolar() throws DataAccessException {
        Solar nope = repository.findById(10000);
        assertNull(nope);

    }

    /*@Test
    void shouldFindBySection() throws  DataAccessException{
        Solar section = repository.findBySection("Main");

    }*/
    @Test
    void shouldFindOneOfEachMaterial() throws DataAccessException {
        List<Solar> poly = repository.findByMaterial(SolarMaterial.POLY);
        assertNotNull(poly);
        assertEquals(1, poly.size());

        List<Solar> si = repository.findByMaterial(SolarMaterial.SI);
        assertNotNull(si);
        assertEquals(1, si.size());

        List<Solar> asi = repository.findByMaterial(SolarMaterial.ASI);
        assertNotNull(asi);
        assertEquals(1, asi.size());

        List<Solar> cdte = repository.findByMaterial(SolarMaterial.CDTE);
        assertNotNull(cdte);
        assertEquals(1, cdte.size());

        List<Solar> cigs = repository.findByMaterial(SolarMaterial.CIGS);
        assertNotNull(cigs);
        assertEquals(1, cigs.size());

        List<Solar> cis = repository.findByMaterial(SolarMaterial.CIS);
        assertNotNull(cigs);
        assertEquals(1, cis.size());
    }

    @Test
    void shouldAddOrbiter() throws DataAccessException {
        Solar solar = new Solar();
        solar.setSection("Main");
        solar.setRow(2);
        solar.setColumn(1);
        solar.setYear(2020);
        solar.setMaterial(SolarMaterial.POLY);
        solar.setIsTracking("Yes");


        Solar actual = repository.add(solar);

        assertNotNull(actual);
        assertEquals(7, actual.getSolarId());
    }
    @Test
    void shouldUpdateExisting() throws DataAccessException{
        Solar solar = new Solar();
        solar.setSolarId(3);
        solar.setSection("Main");
        solar.setRow(2);
        solar.setColumn(1);
        solar.setYear(2020);
        solar.setMaterial(SolarMaterial.POLY);
        solar.setIsTracking("no");

        boolean success = repository.update(solar);
        assertTrue(success);

        Solar actual = repository.findById(3);
        assertNotNull(actual);
        assertEquals("Main", actual.getSection());
        assertEquals("no", actual.getIsTracking());
    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException{
        Solar solar = new Solar();
        solar.setSolarId(100000);

        boolean actual = repository.update(solar);
        assertFalse(actual);
    }

    @Test
    void ShouldDeleteExisting() throws DataAccessException{
        boolean actual = repository.deleteById(2);
        assertTrue(actual);

        Solar s = repository.findById(2);
        assertNull(s);

    }

    @Test
    void ShouldNotDeleteMissing() throws DataAccessException{
        boolean actual = repository.deleteById(1000000);
        assertFalse(actual);

    }


}