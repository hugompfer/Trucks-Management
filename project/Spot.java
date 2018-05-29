/**
 * A classe Spot representa um lugar na garagem, pudendo estacionar um contentor ou um camião.
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Spot
{
    private Truck truck; //camião para ocupar
    private Container container; //contentor para ocupar

    /**
     * Constroi um lugar no parque de estacionamento, com um camião ou contentor estacionado ou vazio.
     */
    public Spot(Truck truck,Container container)
    {
        this.truck=truck!=null ? truck:null;
        this.container=container!=null ? container:null;
       
    }
    
    /**
     * Constroi um lugar vazio no parque de estacionamento.
     */
    public Spot()
    {
        this.truck=null;
        this.container=null;
       
    }

     /**
     * getTruck - Metodo que retorna o camião estacionado, se não estiver nenhum retorna null.
     * 
     * @return Truck - Camião do lugar.
     */
    public Truck getTruck(){
        return truck;
    }

 
      /**
     * getContainer - Metodo que retorna o contentor estacionado, se não estiver nenhum retorna null.
     * 
     * @return Truck - Contentor do lugar.
     */
    public Container getContainer(){
        return container;
    }

    /**
     * isTruckEmpty - Metodo que verifica se o lugar não tem um camião.
     * 
     * @return boolean -true: se o lugar não estiver um camião;false:o contrario.
     */
    public boolean isTruckEmpty(){
        return truck==null;
    }
    
    /**
     * isContainerEmpty - Metodo que verifica se o lugar não tem um contentor.
     * 
     * @return boolean -true: se o lugar não estiver um contentor;false:o contrario.
     */
    public boolean isContainerEmpty(){
        return container==null;
    }
    
    /**
     * isEmpty - Metodo que verifica se o lugar está vazio.
     * 
     * @return boolean -true: se o lugar estiver vazio;false:o contrario.
     */
    public boolean isEmpty() {
        return isTruckEmpty() && isContainerEmpty();
    }
    
    /**
     * addTruck - Metodo que adiciona um camião ao lugar, se este estiver vazio.
     * 
     * @return boolean -true: adicione com sucesso;false:o contrario.
     */
    public boolean addTruck(Truck truck){
        if(isEmpty()){
            this.truck=truck;
            return true;
        }
        return false;
    }
    
    /**
     * addContainer - Metodo que adiciona um contentor ao lugar, se este estiver vazio.
     * 
     * @return boolean -true: adicione com sucesso;false:o contrario.
     */
     public boolean addContainer(Container container){
         if(isEmpty()){
            this.container=container;
            return true;
        }
        return false;
    }
    
    /**
     * removeTruck - Metodo que remove o camião do lugar, metendo este a null.
     * 
     * @return boolean -true: remove com sucesso;false:o contrario.
     */
    public boolean removeTruck(){
        if(!isEmpty()){
            this.truck=null;
            return true;
        }
        return false;
    }
    
     /**
     * removeContainer - Metodo que remove o contentor do lugar, metendo este a null.
     * 
     * @return boolean -true: remove com sucesso;false:o contrario.
     */
     public boolean removeContainer(){
         if(!isEmpty()){
            this.container=null;
            return true;
        }
        return false;
    }
}
