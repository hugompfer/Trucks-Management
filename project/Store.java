import java.util.HashSet;
/**
 * A classe Store representa uma loja,  estas recebem os contentores dos camiões e guardam o bem-recebido nos seus armazéns, pudendo vender produtos.
 * 
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Store
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private static int id=0;//Variavel para criação da identificaçao
    private  int identification;
    private String name;//nome da loja
    private Coordinate coordinate;//Posição da loja
    private GroupOfPacks warehouse;//Armazem da loja
    private GroupContainers containers;//Conjunto de contentores

    /**
     * Cria uma loja com um nome,as cordenadas, o tamanho do armazem e com 2 lugares para guardar contentores. 
     */
    public Store(String name,double latitude,double longitude,int size )
    {
        ++id;
        identification=id;
        this.name=name == null || name.equals("") ? "loja"+identification : name ;
        coordinate=new Coordinate(latitude,longitude);
        this.warehouse=new GroupOfPacks(size);
        containers=new GroupContainers();
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
     * getWarehouse - Metodo que retorna a identificação da loja.
     * 
     * @return int- Identificaçao da loja.
     */
    public int getIdentification(){
        return identification;
    }
    
    /**
     * getName - Metodo que retorna o nome da loja.
     * 
     * @return String- Nome da loja.
     */
    public String getName(){
        return name; 
    }

    /**
     * getName - Metodo que retorna o tamanho do armazem da loja.
     * 
     * @return int- Tamanho do armazem.
     */
    public int getSizeOfwarehouse(){
        return warehouse.getSize();
    }

    /**
     * getCoordinates - Metodo para mostrar a posição da loja.
     * 
     * @return Coordinate - Cordenadas da loja.
     */

    public Coordinate getCoordinates(){
        return coordinate;
    }

    /**
     * unLoadContainer - Metodo para retirar um pack de produtos que a loja necessita,armazenando este no armazem, 
     * verificando se o id do contentor e valido e existente na loja,
     * se o pack é valido e se existe espaça no armazem para o pack a adicionar
     * 
     * @param int idContainer- id do contentor que quer descarregar o pack.
     * @param Pack pack- Pack que pretende remover.                
     */
    public void unLoadContainer(int idContainer,Pack pack){
        Container container=containers.hasContainer(idContainer);
        if(container==null){
            System.out.println("Este contentor não existe nesta loja."); 
        }else{
            if(pack==null){
                System.out.println("Pack invalido."); 
            }else{
                Pack packsToAdd=container.unLoadPack(pack);
                if(packsToAdd!=null){
                    if(warehouse.addPack(packsToAdd)){
                        System.out.println("O contentor foi descarregado.");
                    }
                }else{
                    System.out.println("O armazem já não tem espaço.");
                }
            }
        }
    }

    /**
     * unLoadContainer - Metodo para retirar todos os packs de produtos que o contentor tem,
     * armazenando estes no armazem,
     * verificando se o id do contentor e valido e existente na loja.
     * 
     * @param int idContainer- id do contentor que quer descarregar.              
     */

    public void unLoadContainer(int idContainer){
        Container container=containers.hasContainer(idContainer);
        if(container==null){
            System.out.println("Este contentor não existe nesta loja."); 
        }else{
            HashSet<Pack> packsToRemove=container.getPacks();
            Pack[] pack=packsToRemove.toArray(new Pack[packsToRemove.size()]);
            Pack[] packsAdded=new Pack[packsToRemove.size()];
            int i=0;
            for(Pack p:pack){
                if(pack!=null){
                        packsAdded[i]=container.unLoadPack(p);
                        warehouse.addPack(packsAdded[i]);
                        i++;
                }
            }
            System.out.println("O contentor descarregou o packs que couberam no armazem");
        }
    }

    /**
     * unLoadContainer - Metodo para retirar um determinado nº de produtos que a loja necessita,
     * armazenando este no armazem,
     * verificando se o id do contentor e valido e existente na loja,
     * se codigo do produto e a quantidade for valida e se existe espaço no armazem para essa quantidade
     * 
     * @param int idContainer- id do contentor que quer descarregar. 
     * @param int codOfProduct- codigo do produto que quer descarregar. 
     * @param int quantity- quantidade do produto que quer descarregar. 
     */
    public void unLoadContainer(int idContainer ,int codOfProduct,int quantity){
        Container container=containers.hasContainer(idContainer);
        if(container==null){
            System.out.println("Este contentor não existe nesta loja."); 
        }else{
            if(codOfProduct<1){
                System.out.println("ID invalido."); 
            }else{
                Pack packFromContainer=container.getPack(codOfProduct, quantity);
                if(packFromContainer!=null) {
                    Pack packsToAdd=container.unLoadPack(packFromContainer);
                    if(packsToAdd!=null){
                        warehouse.addPack(packsToAdd);
                        System.out.println("O contentor descarregou corretamente.");
                    }else{
                        System.out.println("Não este este pack no contentor");
                    }
                }else{
                    System.out.println("O armazem já não tem espaço.");
                }
            }
        }
    }

    /**
     * unLoadTruck - Metodo para descarregar o contentor do camião, guardando o contentor na loja,
     * verificando a posiçao do camião e se existe espaço na loja para o guardar
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
            }else{
                System.out.println("Este camião que introduziu não é valido.");
            }
        }
    }

    /**
     * loadTrucks - Metodo para carregar o contentor para dentro do camião, removendo o contentor da loja.
     * 
     * @param Truck truck- camião que quer carregar.   
     * @param int idContainer- id do contentor que quer colocar dentro do camião.    
     */

    public void loadTrucks(Truck truck , int  idContainer){
        Container container=containers.hasContainer(idContainer);
        if(truck ==null){
            System.out.println("Este camião que introduziu não é valido.");
        }else if(container==null) {
            System.out.println("Este contentor não é valido,verifique o seu id.");  
        }else if(coordinate.checkPosition(truck.getCoordinates().getLatitude(),truck.getCoordinates().getLongitude())){
            Container theContainer=containers.removeContainer(idContainer);
            if(theContainer!=null){
                if(truck.load(theContainer)){
                    System.out.println("O camião carregou o contentor:"+theContainer);
                }
            }
        }
    }

    /**
     * sellProduct - Metodo para vender um produto a partir do codigo do produto e retirar do armazem esse produto,ou diminuir a quantidade do produto.
     * 
     * @param int codOfProduct- Codigo do produto que quer vender.   
     * @param int quantity- Quantidade do produto que quer vender.    
     */
    public void sellProduct(int codOfProduct,int quantity){
        if(codOfProduct>0 && quantity>0){
            warehouse.removeProduct(codOfProduct,quantity);
            System.out.println("O produto : "+codOfProduct+" foi vendido com sucesso.");
        }else{
            System.out.println("O produto que pretende não existe ou a quantidade não é aceitavel.");
        }
    }

    /**
     * sellProduct - Metodo para vender um produto a partir do nome e retirar do armazem esse produto,ou diminuir a quantidade do produto.
     * 
     * @param String name- Name do produto que quer vender.   
     * @param int quantity- Quantidade do produto que quer vender.    
     */
    public void sellProduct(String name, int quantity){
        if(name!=null && quantity>0){
            warehouse.removeProduct(name,quantity);
            System.out.println("O produto :"+name+" foi vendido com sucesso.");
        }else{ 
            System.out.println("O produto que pretende não existe.");
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
     * toString - Metódo que representa textualmente o objeto(identificação,nome,cordenadas,capacidade do armazem).
     * 
     * @return String- Informação da loja.
     */
    public String toString(){
        return "\n Nome: "+name
        +"\n Latitude: "+coordinate.getLatitude()+"\n Longitude: "+coordinate.getLongitude()
        +"\n com "+warehouse.getNumberOfPacks()+" packs.";

    }

}
