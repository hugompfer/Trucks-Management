/**
 * A classe SupplyDistribuitionStation representa uma estação de distribuiçao e abastecimento, que como o nome indica gera todo o sistema de distribuiçoes e abastecimentos, caregando camioes e contentores para serem enviados com packs para as lojas.
 * 
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class SupplyDistribuitionStation
{
    // variáveis de instância 
    private static int id=0;// id para criação de nome
    private int identification;// Identificação da estação
    private String name;//nome da estação
    private Coordinate coordinate;//posiçao da garagem
    private GroupOfPacks warehouse;//Armazem
    private GroupContainers containers;//Conjunto de contentores
    private PacksMarketed packComercialized;
    
    /**
     *Cria uma estação com um nome,as cordenadas , com o numero de contentores e o tamanho do armazem . 
     */
    public SupplyDistribuitionStation(String name,float latitude,float longitude,int numberOfContainer,int sizeOfWarehouse)
    {
        ++id;
        identification=id;
        this.name=name == null && name.equals("") ? "estação"+id : name;
        coordinate=new Coordinate(latitude,longitude);
        warehouse=new GroupOfPacks(sizeOfWarehouse);
        containers=new GroupContainers(numberOfContainer);
        packComercialized=new PacksMarketed();
    }

    /**
     * getIdentification - Metodo para mostrar a identificação do contentor.
     * 
     * @return String - Identificação do contentor.
     */

    public int getIdentification(){       
        return identification;
    }
    
     /**
     * getContainer - Metodo que retorna o conjunto de contentores da estação.
     * 
     * @return GroupContainers - Conjunto de contentores.                
     */
    public GroupContainers getContainers(){       
        return containers;
    }
    
     /**
     * getWarehouse - Metodo que retorna o conjunto de packs.
     * 
     * @return GroupOfPacks- Objeto da classe GroupOfPacks.
     */
    public GroupOfPacks getWarehouse(){       
        return warehouse;
    }
    
     /**
     * getPackComercialized - Metodo que retorna o conjunto de packs comercializados.
     * 
     * @return PacksMarketed- Objeto da classe PacksMarketed.
     */
    public PacksMarketed getPackComercialized(){       
        return packComercialized;
    }
    
    /**
     * getCoordinates - Metodo para mostrar as cordenadas da estação.
     * 
     * @return String - Cordenadas da estação.
     */
    public Coordinate getCoordinates(){
        return coordinate; 
    }

    /**
     * loadTrucks - Metodo para carregar o contentor para dentro do camião, removendo o contentor da loja,
     * se o camião tiver na mesma posição que a estação,
     * se o mesmo ainda não tiver carregar e e o contentor for valido
     * 
     * @param Truck truck- camião que quer carregar.   
     * @param int idContainer- id do contentor que quer colocar dentro do camião.    
     */
    public void loadTrucks(Truck truck , int idContainer){
        if(truck ==null){
            System.out.println("Este camião que introduziu não é valido.");
        }else if(id<1) {
            System.out.println("O id do contentor não é válido.");  
        }else if(coordinate.checkPosition(truck.getCoordinates().getLatitude(),truck.getCoordinates().getLongitude())){ 
            if(truck.getContainer()==null){
                Container theContainer=containers.removeContainer(idContainer);
                if(theContainer!=null){
                    if(truck.load(theContainer)){
                        System.out.println("O camião foi carregado corretamente.");
                    }
                }
            }else{
                System.out.println("Este camião já tem um contentor.");
            }
        }
    }

    /**
     * loadContainers - Metodo para caregar um contentor com um pack do armazem, removendo o mesmo do armazem se
     * o contentor for valido, se tiver espaço para o tamanho do pack.
     * Se a quantidade pedida for maior que a quantidade existente no armazem, o aramzem fornece a quantidade existente removendo asim o stock do armazem
     * 
     * @param int idContainer- id do contentor que quer carregar o pack.
     * @param Pack pack- Pack que pretende carregar.                
     */
    public void loadContainers(Pack pack ,int idContainer){
        Container container=containers.hasContainer(idContainer);
        if(container!=null){
            if(container.haveSpace(pack)){
                Pack packRemoved=warehouse.removePack(pack);
                if(packRemoved!=null){
                    container.loadPack(packRemoved);
                    packComercialized.addPack(pack);
                }else{
                    System.out.println("Este contentor não tem espaço para este pack."); 
                }  
            }
        }else{
            System.out.println("Este contentor não existe, verifique se o id é válido."); 
        }
    }

    /**
     * loadContainers - Metodo para caregar um contentor com uma quantidade de um produto do armazem, removendo a mesma quantidade do armazem se
     * o contentor for valido, se tiver espaço para o tamanho do pack.
     * Se a quantidade pedida for maior que a quantidade existente no armazem, o aramzem fornece a quantidade existente removendo asim o stock do armazem
     * 
     * @param int codOfProduct- id do produto que quer carregar.
     * @param int quantity- quantidade do produto que quer carregar.
     * @param int idContainer- id do contentor que quer carregar o pack.              
     */
    public void loadContainers(int codOfProduct,int quantity,int idContainer){
        Container container=containers.hasContainer(idContainer);
        if(container!=null){
            if(codOfProduct<1){
                System.out.println("ID do produto não é valido."); 
            }else if(quantity<1){
                System.out.println("A quantidade não é valida."); 
            }else{ 
                Pack packToAdd=warehouse.getPack(codOfProduct,quantity);
                if(container.haveSpace(packToAdd)){
                    Pack pack=warehouse.removePack(packToAdd);
                    if(pack!=null){
                        container.loadPack(pack);
                        packComercialized.addPack(pack);
                    }
                }else{
                    System.out.println("Este contentor não tem espaço para este pack."); 
                }  
            }
        }else{
            System.out.println("Este contentor não existe, verifique se o id é válido."); 
        }
    }

    /**
     * unLoadTruck - Metodo para descarregar o contentor do camião, guardando o contentor na loja, se houver espaço para o mesmo e se  camião estiver na mesma posiçao da garagem.
     * 
     * @param Truck truck- objeto do camião que quer descarregar.                
     */

    public void unLoadTruck(Truck truck){
        if(truck!=null){
            if(coordinate.checkPosition(truck.getCoordinates().getLatitude(),truck.getCoordinates().getLongitude())){ 
                if(!containers.isFull()){
                    if(containers.addContainer(truck.unLoad())){  
                        System.out.println("O camião descarregou o contentor.");
                    }         
                } else{
                    System.out.println("Não existe espaço para mais contentores");
                }
            }
        }else{
            System.out.println("O camião que introduziu não é valido.");
        }

    }

    /**
     * addStock - Metodo para adicionar um novo pack ao armazem.
     * 
     * @param Pack pack- objeto do pack que quer adicionar ao armazem.                
     */

    public void addStock(Pack pack){
        if(pack==null){
            System.out.println("Pack invalido.");
        }else{
            if(warehouse.addPack(pack)){
                System.out.println("O pack adicionado ao armazem:\n"+pack);
            }
        }
    }

     /**
     * addStock - Metodo para adicionar novos packs ao armazem.
     * 
     * @param Pack[] pack- Array de objeto de packs que quer adicionar ao armazem.                
     */

    public void addStock(Pack[] pack){
        if(pack==null){
            System.out.println("Pack invalido.");
        }else{
            for(int i=0;i<pack.length;i++){
                if(pack[i]!=null){
                    if(warehouse.addPack(pack[i])){
                        System.out.println("O pack adicionado ao armazem:\n"+pack[i]);
                    }
                }
            }
        }
    }

    /**
     * incrementStock - Metodo para incrementar packs existentes no armazem
     * 
     * @param int codOfProduct- id do produto que quer incrementar.
     * @param int quantity- quantidade do produto que quer incrementar.            
     */

    public void incrementStock(int codOfProduct, int quantity){
        if(codOfProduct<1){
            System.out.println("ID do produto não é valido."); 
        }else if(quantity<1){
            System.out.println("A quantidade não é valida."); 
        }else{
            if(warehouse.increaseQuantityOfProduct(codOfProduct,quantity)){
                System.out.println("O stock do produto "+codOfProduct+" foi aumentado.");
            }
            else{
                System.out.println("Este produto não existe no armazem.");
            }
        }
    }

    /**
     * showStock - Mostra todo o stock do armazem.
     *  
     */
    public void showStock(){
        System.out.println("Stock:\n"+warehouse);
    }

    /**
     * containerAvailable - Metódo que retorna a posiçao do arraylist de contentores disponivel para o seu carregamento.
     * 
     * @return int- Posiçao de do contentor .
     */
    public int containerAvailable(){
       return containers.containerAvailable();
    }
    
    /**
     * toString - Metódo que representa textualmente o objeto(identificação,nome,cordenadas,capacidade do armazem e nº maximo de contentores).
     * 
     * @return String- Informação da estação.
     */
    public String toString(){
        return "\n Identificação: "+identification+"\n Nome : "+name+"\n Latitude : "+ coordinate.getLatitude()
        +"\n Longitude : "+coordinate.getLongitude()+ "\n Armazem com "
        +warehouse.getNumberOfPacks()+" packs.";

    }

}
