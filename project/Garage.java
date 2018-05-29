/**
 * A classe Garage representa uma garagem, que serve para estacionar contentores e camiões pudendo fazer troca entre eles e fazer inspeçoes aos camiões.
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Garage
{
    // variáveis de instância 
    private static int id=0; // id para criação de nome
    private String name; //nome da garagem
    private Coordinate coordinate; //posiçao da garagem
    private GroupSpots spots; //conjunto de lugares

    /**
     * Cria uma garagem com um nome, um nº de lugares da garagem e uma localização(cordenadas)
     */
    public Garage(String name,int numberOfSpots,double latitude,double longitude)
    {
        ++id;
        this.name=name == null || name.equals("") ? "Garagem"+id : name ;
        coordinate=new Coordinate(latitude,longitude);
        spots=new GroupSpots(numberOfSpots);

    }

    /**
     * getCoordinates - Metodo que retorna as cordenadas da garagem.
     * 
     * @return Coordinate- Objeto das cordenadas.
     */

    public Coordinate getCoordinates(){
        return coordinate;
    }

    /**
     * getCoordinates - Metodo que retorna as cordenadas da garagem.
     * 
     * @return Coordinate- Objeto das cordenadas.
     */

    public GroupSpots getSpots(){
        return spots;
    }
    
    /**
     * parkTruck - Metodo estacionar um camião num lugar da garagem,verficando se este se encontra na posiçao da garagem
     * 
     * @param Truck truck - Camião que quer estacionar.
     */
    public void parkTruck(Truck truck){
        checkParkingSpaces();
        if(truck==null){
            System.out.println("Este camião não existe.");
        }else {
            if(!checkTruckPosition(truck)){
                System.out.println("Este camião não se encontra na garagem para poder estacionar.");
            }else{
                int index=spots.indexOfTruck(truck);
                if(index==-1){
                    spots.addTruck(truck);
                }else{
                    System.out.println("Este camião já está estaciona.");
                }
            }                    
        }
    }   

    //verifica se o camião se encontra na posição da garagem
    private boolean checkTruckPosition(Truck truck){
        if(truck.getCoordinates().getLatitude()==coordinate.getLatitude() && truck.getCoordinates().getLongitude()==coordinate.getLongitude()){
            return true;
        }
        return false;
    }

    /**
     * goOutTruck - Metodo para mandar o camião para outra localização,retirando este do lugar da garagem.
     * 
     * @param Truck truck - Camião que quer retirar da garagem.
     * @param float latitude - Latitude para onde quer ir.
     * @param float longitude - Longitude para onde quer ir.
     * 
     */
    public void goOutTruck(Truck truck,float latitude,float longitude){
        checkParkingSpaces();
        if(truck!=null){
            int index=spots.indexOfTruck(truck);
            if(index!=-1){
                if(truck.moveTruck(latitude,longitude)){
                    if(spots.removeTruck(truck)){
                        System.out.println("\nO camião\n"+truck+" saiu da garagem.");
                    }
                } 
            }else{
                System.out.println("Esse camião não se encontra na garagem estacionado.");
            }
        }else{
            System.out.println("Esse camião não é válido.");
        }
    }

    /**
     * inspection - Metodo para inspecionar um camião dando reset nos quilometos e incrementando o nº de inspeçoes.
     * 
     * @param Truck truck - Camião que quer inspecionar.
     */
    public void inspection(Truck truck){
        checkParkingSpaces();
        if(truck==null){
            System.out.println("Esse camião não é valido.");
        }else{
            int index=spots.indexOfTruck(truck);
            if(index!=-1){
                truck.alterNumberOfInspections();
                System.out.println("Inspeção feita com sucesso.");
            }else{
                System.out.println("Esse camião não se encontra na garagem estacionado.");
            }
        }
    }

    /**
     * switchContainers - Metodo para descarregar um camião, metendo o contentor num lugar da garagem, e caregar com outro contentor que esta num lugar da garagem.
     * 
     * @param int idContainer- identificaçao do contentor que quer meter dentro do camião.
     * @param Truck truck - Camião que quer descaregar e depois caregar.
     */
    public void switchContainers(int idContainer,Truck truck){
        checkParkingSpaces();
        if(idContainer>0){ 
            Container container=spots.getContainer(idContainer);
            if(container!=null){
                if(checkTruckPosition(truck)){
                    if(spots.removeContainer(container)){
                        Container containerFromTruck=truck.unLoad();
                        if(containerFromTruck!=null){
                            spots.addContainer(containerFromTruck);
                        }
                        if(truck.load(container)){
                            System.out.println("O camião foi caregado com o contentor:\n"+container);
                        }
                    }
                }
            }else{
                System.out.println("Não existe nenhum lugar com o contentor que pretende trocar.");
            }
        }else{
            System.out.println("Este contentor não é válido.");
        }
    }

    /**
     * switchContainers - Metodo para descarregar um camião, metendo o contentor num lugar da garagem, e o camião tambem se este já nao estiver estacionado.
     * 
     * @param Truck truck - Camião que quer descaregar.
     */
    public void switchContainers(Truck truck){
        checkParkingSpaces();
        if(truck!=null){
            if(checkTruckPosition(truck)){
                if(truck.getContainer()==null){
                    System.out.println("Este camião não tem nenhum contentor para descarregar.");
                }else{
                    if(!spots.isFull()){
                        spots.addContainer(truck.unLoad());
                        if(spots.indexOfTruck(truck)==-1){
                            spots.addTruck(truck); 
                        }
                    }else{
                        System.out.println("Garagem esta cheia.");
                    }
                }
            }else{
                System.out.println("Este camião não se encontra na garagem.");
            }
        }else{
            System.out.println("Este camião não existe.");
        }

    }

    /**
     * checkParkingSpaces - Metodo que percorre todos os lugares em que estão estacionados camiões, verificando se estes estão na mesmo posiçao da garagem,caso algum não teja remove esvazio o lugar do mesmo..
     * 
     */
    public void checkParkingSpaces(){
        if(spots.getTrucks()!=null){
            for(Truck truck: spots.getTrucks()){
                if(truck!=null){  
                    if(!checkTruckPosition(truck)){
                        spots.removeTruck(truck);
                    }
                }
            }
        }
    }

    /**
     * showSpots - Metodo que mostra a informação de os lugares ocupados(camião ou contentor).
     * 
     */
    public void showSpots(){
        System.out.println("Lugares ocupados da garagem:\n"+spots);
    }

    /**
     * toString - Metódo que representa textualmente o objeto(Nome,cordenadas e nº de lugar da garagem para estacionar).
     * 
     * @return String- Informação da garagem.
     */
    public String toString(){
        checkParkingSpaces();
        return "\n Nome:"+name+"\n Latitude:"
        +coordinate.getLatitude()+"\n Longitude:"+coordinate.getLongitude()
        +"\ncom "+spots.getSize()+"espaçoes de estacionamento";
    }
}

