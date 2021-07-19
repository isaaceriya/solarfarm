package learn.solar.ui;

import learn.solar.data.DataAccessException;
import learn.solar.domain.SolarResult;
import learn.solar.domain.SolarService;
import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.util.List;

public class Controller {
    private final SolarService service;
    private final View view;

    public Controller(SolarService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run(){
        try{
            runMenu();
        }catch (DataAccessException ex){
            view.printHeader("Fatal Err: " + ex);
        }

    }

    private void runMenu() throws DataAccessException {
        MenuOption option;
        do{
            option = view.displayMenuAndSelect();
            switch(option){
                case EXIT:
                    view.printHeader("Goodbye.");
                    break;
                case DISPLAY_SOLARS:
                    displaySolars();
                    break;
                case CREATE_SOLAR:
                    createSolar();
                    break;
                case UPDATE_SOLAR:
                    updateSolar();
                    break;
                case DELETE_SOLAR:
                    deleteSolar();
                    break;
            }
        }while(option != MenuOption.EXIT);
    }

    private void displaySolars() throws DataAccessException {
        view.printHeader(MenuOption.DISPLAY_SOLARS.getTitle());
        SolarMaterial material = view.readSolarMaterial();
        List<Solar> solars = service.findByMaterial(material);
        view.displaySolars(solars);
    }

    private void createSolar() throws DataAccessException {
        view.printHeader(MenuOption.CREATE_SOLAR.getTitle());
        Solar solar = view.makeSolar();
        SolarResult result = service.add(solar);
        view.displayResult(result);
    }

    private void updateSolar() throws DataAccessException {
        view.printHeader(MenuOption.UPDATE_SOLAR.getTitle());

        SolarMaterial material = view.readSolarMaterial();
        List<Solar> solars = service.findByMaterial(material);

        Solar solar = view.update(solars);
        if(solar == null){
            return;
        }

        SolarResult result = service.update(solar);
        view.displayResult(result);
    }

    private void deleteSolar() throws DataAccessException {
        view.printHeader(MenuOption.DELETE_SOLAR.getTitle());

        SolarMaterial material = view.readSolarMaterial();
        List<Solar> solars = service.findByMaterial(material);

        Solar solar = view.findSolar(solars);
        if(solar == null){
            return;
        }
        SolarResult result = service.deleteById(solar.getSolarId());
        view.displayResult(result);
    }
}
