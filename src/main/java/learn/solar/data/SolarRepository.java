package learn.solar.data;

import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.util.List;

public interface SolarRepository {
    List<Solar> findAll() throws DataAccessException;

    Solar findById(int solarId) throws DataAccessException;

    List<Solar> findByMaterial(SolarMaterial material) throws DataAccessException;

    Solar add(Solar solar) throws DataAccessException;

    boolean update(Solar solar) throws DataAccessException;

    boolean deleteById(int solarId) throws DataAccessException;
}
