package learn.solar.ui;

import learn.solar.domain.SolarResult;
import learn.solar.models.Solar;
import learn.solar.models.SolarMaterial;

import java.util.List;
import java.util.Scanner;

public class View {


    private final Scanner console = new Scanner(System.in);

    public MenuOption displayMenuAndSelect(){
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for(int i = 0; i < values.length; i++){
            System.out.printf("%s. %s%n", i, values[i].getTitle());

        }
        int index = readInt("Select [0-4]: ", 0, 4);
        return values[index];
    }

    public void printHeader(String message){
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void displaySolars(List<Solar> solars){
        printHeader("Solars:");
        if(solars.size() == 0){
            System.out.println("No solars found.");
        }else{
            for(Solar s : solars){
//                System.out.println("ID "+"Section "+ "Row "+ "Column "+ "Year "+"Material "+"Tracking");
                System.out.printf("%s. %s    %s    %s    %s    %s   %s%n",
                        s.getSolarId(),
                        s.getSection(),
                        s.getRow(),
                        s.getColumn(),
                        s.getYear(),
                        s.getMaterial(),
                        s.getIsTracking());
            }
        }
    }

    public void displayResult(SolarResult result){
        if(result.isSuccess()){
            System.out.println("Success!");
        }else{
            System.out.println("Err:");
            for(String err : result.getMessages()){
                System.out.println(err);
            }
        }
    }

    public Solar makeSolar(){
        Solar solar = new Solar();
        solar.setSection(readRequiredString("Section: "));
        solar.setRow(readInt("Row: "));
        solar.setColumn(readInt("Column: "));
        solar.setYear(readInt("Year: "));
        solar.setMaterial(readSolarMaterial());
        solar.setIsTracking(readRequiredString("Is Tracking: "));

        return solar;
    }

    public Solar update(List<Solar> solars){
        Solar solar = findSolar(solars);
        if(solar != null){
            update(solar);
        }
        return solar;
    }

    public Solar findSolar(List<Solar> solars){
        displaySolars(solars);
        if(solars.size() == 0){
            return null;
        }
        int solarId = readInt("Solar Id: ");
        for(Solar s : solars){
            if(s.getSolarId() == solarId){
                return s;
            }
        }
        System.out.println("Solar Id " + solarId +" not found.");
        return null;

    }

    private Solar update(Solar solar){
        String section = readString("Section (" + solar.getSection() + "): ");
        if(section.trim().length() > 0){
            solar.setSection(section);
        }
        int row = readInteger("Row (" + solar.getRow()+ "): ");
        if(row > 0) {
            solar.setRow(row);
        }
        int column = readInt("Column (" + solar.getColumn()+ "): ");
        if(column > 0){
            solar.setColumn(column);
        }
        int year = readInt("Year (" + solar.getYear()+ "): ");
        if(year > 0){
            solar.setYear(year);
        }

        SolarMaterial material = readSolarMaterial();
        if(material == SolarMaterial.POLY ||
                material == SolarMaterial.SI ||
                material == SolarMaterial.ASI ||
                material == SolarMaterial.CDTE ||
                material == SolarMaterial.CIGS ||
                material == SolarMaterial.CIS){
            solar.setYear(year);
        }

        String isTracking = readString("Is Tracking (" + solar.getIsTracking()+ "): ");
        if(isTracking.trim().length() > 0){
            solar.setIsTracking(isTracking);
        }

        return solar;
    }

    private String readString(String prompt){
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt){
        String result = null;
        do{
            result = readString(prompt).trim();
            if(result.length() == 0){
                System.out.println("Value is required.");
            }
        }while(result.length() == 0);
        return result;
    }

    public SolarMaterial readSolarMaterial(){
        System.out.println("Material:");
        SolarMaterial[] values = SolarMaterial.values();
        for(int i = 0; i < values.length; i++){
            System.out.printf("%s. %s%n", i, values[i]);

        }
        int index = readInt("Select [0-7]: ", 0, 7);
        return values[index];
    }



    private int readInt(String prompt){
        int result =0;
        boolean isValid = false;
        do{
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            }catch (NumberFormatException ex){
                System.out.println("Value must be a number");
            }
        }while (!isValid);
        return result;
    }


    private int readInteger(String prompt){
        int result = 0;
        String value = readRequiredString(prompt);
        result = Integer.parseInt(value);
        return result;

    }


    private int readInt(String prompt, int min, int max){
        int result = 0;
        do{
            result = readInt(prompt);
            if(result < min || result > max){
                System.out.printf("Value must be between %s and %s.%n", min, max);

            }
        }while(result < min || result > max);

        return result;
    }

}
