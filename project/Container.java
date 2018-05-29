import java.util.HashSet;
/**
 * A classe Container representa um contentor, que serve para guardar packs de produtos para depois serem caregados por um camião.
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Container
{
    // variáveis de instância 
    private  GroupOfPacks packs;//Conjuntos de packs
    private float capacity;//Capacidade atual
    private float containerLoad;//Carga atual
    private static int id=0;//variavel para criaçao da identificaçao
    private int identification;//identificação do contentor
    private final int maxCapacity=3300000;//Capacidade maxima
    private final int maxLoad=22000;//Carga maxima
    private final int max=10;//maximo de packs
    /**
     * Cria um contentor, cabendo lá dentro 10 packs, com um volume maximo de 33m^3 e uma carga maxima de 22000kg.
     */

    public Container(){
        this.containerLoad=0 ;
        this.capacity= 0;
        packs=new GroupOfPacks(max);;
        ++id;
        identification=id;
    }

    /**
     * getIdentification - Metodo que retorna a identificação do contentor.
     * 
     * @return String - Identificação do contentor.
     */

    public int getIdentification(){       
        return identification;
    }

    /**
     * isEmpty - Metodo que verifica se o contentor esta vazio.
     * 
     * @return boolean -true: se o contentor estiver vazio;false:o contrario.
     */
    public boolean isEmpty(){
        return capacity==0 && containerLoad==0;
    }
    
    /**
     * getCapacity - Metodo que retorna a capacidade atual do contentor.
     * 
     * @return float - Volume atual em m^3.
     */
    
    public float getCapacity(){
        return capacity;
    }
    
    /**
     * getLoad - Metodo que retorna a carga atual do contentor.
     * 
     * @return float - Carga atual em kg.
     */
    
    public float getLoad(){
        return containerLoad;
    }

    /**
     * unLoadPacks - Metodo para retirar do contentor um determinado pack.
     * 
     * @param Pack[] packsToRemove- Pack que pretende remover.
     * @return Pack[] - Pack que removeu.
     *                  
     */

    public Pack unLoadPack(Pack packsToRemove){   
        if(packsToRemove==null ) {
            System.out.println("Verifique se o pack que pretende não estão vazios.");
            return null;  
        }
        return unLoad(packsToRemove);
    }

    //remove o pack do contentor e faz o reset ao volume e ao peso do contentor 
    private Pack unLoad(Pack packsToRemove){
        Pack packsRemoved=null;
        packsRemoved=packs.removePack(packsToRemove); 
        resetVolumeLoad(packsRemoved);
        return packsRemoved;
    }

    // diminuir o peso e o volume do contentor a partir de um determinado pack
    private void resetVolumeLoad(Pack packToReset){
        if(packToReset!=null){
            decreaseCapatity(packToReset.getVolumeUnitary()*packToReset.getQuantity());
            decreaseLoad(packToReset.getWeightUnitary()*packToReset.getQuantity());
        }
    }

    /**
     * loadPacks - Metodo para adicionar ao contentor um pack, verificando se cabem no contento e incrementando assim o peso e o volume atual do mesmo
     * 
     * @param Pack packsToCharge- Pack que pretende adicionar.
     * @return Pack - Pack que adicionou.
     *                  
     */
    public void loadPack(Pack packsToCharge) {
        if(packsToCharge!=null){        
                Pack packsAdded=null;
                if(haveSpace(packsToCharge)){
                    if(packs.addPack(packsToCharge)){
                        increaseCapacity(packsToCharge.getVolumeUnitary()*packsToCharge.getQuantity());
                        increaseLoad(packsToCharge.getWeightUnitary()*packsToCharge.getQuantity());
                        System.out.println("O contentor foi carregado com sucesso.");
                    }
                }else{
                    System.out.println("Já não existe mais espaço no contentor.");
                }
        }else{
            System.out.println("Pack inválido.");
        }
 
    }
   
    /**
     * freeCapacity - Metodo para mostrar o volume livre do contentor.
     * 
     * @return double - Volume livre.                
     */

    public double freeCapacity(){
        return maxCapacity-capacity;
    }

    /**
     * freeLoad - Metodo para mostrar o peso livre do contentor.
     * 
     * @return double - Peso livre.                
     */

    public double freeLoad(){
        return maxLoad-containerLoad;
    }

    /**
     * increaseLoad - Metodo para aumentar a quantidade de carga atual do contentor, atualizando-a.
     * 
     * @param int quantity - Carga a aumentar.
     */
    private void increaseLoad(float quantity){
        containerLoad=quantity>0?quantity+containerLoad:containerLoad;
    }

    /**
     * decreaseLoad - Metodo para diminuir a quantidade de carga atual do contentor, atualizando-a.
     * 
     * @param int quantity - Carga a diminuir.
     */
    private void decreaseLoad(float quantity){
        containerLoad=quantity>0?containerLoad-quantity:containerLoad;
    }

    /**
     * increaseCapacity - Metodo para aumentar a quantidade de volume atual do contentor, atualizando-a.
     * 
     * @param int quantity - Volume a aumentar.
     */
    private void increaseCapacity(float quantity){
        capacity=quantity>0?quantity+capacity:capacity;
    }

    /**
     * decreaseCapatity - Metodo para diminuir a quantidade de volume atual do contentor, atualizando-a.
     * 
     * @param int quantity - Volume a diminuir.
     */
    private void decreaseCapatity(float quantity){
        capacity=quantity>0?capacity-quantity:capacity;
    }

    /**
     * haveSpace - Metodo verificar se existe espaço para adicionar um determinado pack, 
     * verfificando se a carga e o volume é maior que a carga e o volume atual com o o peso do pack que quer adicionar.
     * 
     * @param Pack pack - Pack que pretende adicionar.
     */
    public boolean haveSpace(Pack pack){
        if(freeCapacity()>occupiedSpace(pack) && freeLoad()>weigthCarried(pack)){
            return true;
        }
        System.out.println("O pacote"+pack+" já não cabe neste contentor.");
        return false;
    }

      /**
     * occupiedSpace - Metodo que retorna o volume que ocupa um determinado pack, multiplicando a quantidade de produto com o volume unitario do mesmo. 
     * 
     * @param Pack pack - Pack que pretende ver o volume.
     * @return double - Volume que ocupa.  
     */
    
    private double occupiedSpace(Pack pack){
        double occupiedSpace=0.0;
        if(pack!=null){
            occupiedSpace+=pack.getVolumeUnitary()*pack.getQuantity();
        }

        return occupiedSpace;
    }
    
     /**
     * weigthCarried - Metodo que retorna o peso em kg de um determinado pack, multiplicando a quantidade de produto com o volume unitario do mesmo. 
     * 
     * @param Pack pack - Pack que pretende ver a peso.
     * @return double - Peso do pack.  
     */
   

    private double weigthCarried(Pack pack){
        double weigthCarried=0.0;
        if(pack!=null){
            weigthCarried+=pack.getWeightUnitary()*pack.getQuantity();
        }
        return weigthCarried;
    }

    /**
     * getPacks - Metodo para mostrar os pack que estão no contentor.
     * 
     * @return Pack[] - Packs do contentor.
     */

    public HashSet<Pack> getPacks(){
        return packs.getPacks();
    }
    
     /**
     * getGroupOfPacks - Metodo que retorna o conjunto de packs.
     * 
     * @return GroupOfPacks- Objeto da classe GroupOfPacks.
     */
    public GroupOfPacks getGroupOfPacks(){
        return packs;
    }
    
    /**
     * getPack - Metodo que verifica se existe o id do pack no contentor e
     * retorna um pack com o mesmo id,nome,peso e volume e com a quantidade pretendida.  
     * 
     * @param int codOfProduct - codigo do produto do pack.
     * @param int quantity - quantidade do produto.
     * @return Pack - pack existente no contentor com a quantidade pretendida.
     */
    
    public Pack getPack(int codOfProduct,int quantity){
       return packs.getPack(codOfProduct,quantity);
    }

    /**
     * toString - Metódo que representa textualmente o objeto(identificação,capacidade,carga,
     * e os packs que estão no contentor).
     * 
     * @return String- Informação do camião.
     */

    public String toString(){
        String info="\nIdentificação : "+identification+" \nCapacidade : "+capacity
            +"\nCarga : "+containerLoad+ "\nPacks do contentor: ";
        if(packs!=null){
            for(Pack p: packs.getPacks()){
                if(p!=null){
                    info+= "\n"+p.toString(); 
                }
            }
        }
        return info;
    }
}
