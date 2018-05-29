/**
 * A classe Pack representa um pack(conjunto de produtos).
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Pack
{
    // variáveis de instância 
    private int codOfProduct;//Codigo do produto
    private String name;//Nome do produto
    private int quantity;//Quantidade do produto
    private float weightUnitary;//Peso unitario do produto
    private float volumeUnitary;//Volume unitario do produto

    /**
     * Constroi um novo pack, com a informação do código,nome, quantidade,peso(unitário),volume(unitário) do produto.
     */
    public Pack(int codOfProduct,String name, int quantity,float weightUnitary, float volumeUnitary)
    {
        this.codOfProduct=codOfProduct>0?codOfProduct:1;
        this.name=name == null || name.equals("") ? "Pack"+codOfProduct : name ;
        this.quantity= quantity>=0 ? quantity : 1;
        this.weightUnitary= weightUnitary>0 ? weightUnitary : 1;
        this.volumeUnitary= volumeUnitary>0 ? volumeUnitary : 1;
    }

    /**
     * toString - Metódo que representa textualmente o objeto(código, nome, quantidade, peso(unitário), volume(unitário) do produto).
     * 
     * @return String- Informação do pack.
     */
   public String toString(){
       return "Codigo do Produto: "+codOfProduct+"\n Nome do Produto: "+name
       +"\n Quantidade do Produto: "+quantity+"\n Peso Unitario: "+weightUnitary
       +"\n Volume Unitario: "+volumeUnitary+"\n";
   }
   
   /**
     * hashCode - Metodo que retorna o hashCode do objeto Pack.
     * 
     * @return int- numero do hashCode.
     */
   public int hashCode() {
        return this.codOfProduct;
    }
    
    /**
     * equals - Metodo que indica se um objeto e igual a outro objeto da mesma classe.
     * 
     * @return boolean- true:se for igual; false o contrario.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pack other = (Pack) obj;
        return this.codOfProduct == other.getCodOfProduct();
    }
   
   /**
     * getCodOfProduct - Metodo que retorn o código do produto.
     * 
     * @return int - Código do produto.
     */
   
   public int getCodOfProduct(){
        return codOfProduct;   
   }
   
   /**
     * getName - Metodo que retorna o nome do produto.
     * 
     * @return String- Nome do produto.
     */
    
   public String getName(){
        return name;   
   }
   
   /**
     * getQuantity - Metodo que retorna a quantidade de produtos.
     * 
     * @return int- Quantidade de produtos.
     */
    
   public int getQuantity(){
        return quantity;   
   }
   
   /**
     * getWeightUnitary - Metodo que retorna o peso unitário em Kg do produto.
     * 
     * @return float- Peso unitário .
   */
   
   public float getWeightUnitary(){
        return weightUnitary;   
   }
   
    /**
     * getVolumeUnitary - Metodo que retorna o volume unitário em cm3 do produto.
     * 
     * @return float- Volume unitário.
   */
   
   public float getVolumeUnitary(){
        return volumeUnitary;   
   }
   
     /**
     * setName - Metodo para alterar o nome atual do produto por um novo.
     * 
     * @param String name - Nome correto para alteração.
   */
   
   public void setName(String name){
        this.name=name != null && name!= "" ? name :this.name;
   }
   
     /**
     * increaseQuantity - Metodo para aumentar a quantidade atual, atualizando-a.
     * 
     * @param int quantity - Quantidade para aumentar.
   */
   
   public void increaseQuantity(int quantity){
        this.quantity= quantity>0 ? this.quantity+quantity :  this.quantity;
   }
   
    /**
     * decreaseQuantity - Metodo para diminuir a quantidade atual, atualizando-a.
     * 
     * @param int quantity - Quantidade para diminuir.
   */
   public void decreaseQuantity(int quantity){
        this.quantity= quantity>0 && this.quantity-quantity >=0 ? this.quantity-quantity : this.quantity;
   }
   
   /**
     * setWeightUnitary - Metodo para alterar o peso unitário do produto.
     * 
     * @param float weightUnitary - Peso correto para alteração.
   */

   public void setWeightUnitary(float weightUnitary){
        this.weightUnitary= weightUnitary>0 ? weightUnitary : this.weightUnitary;
   }
   
   /**
     * setVolumeUnitary - Metodo para alterar o volume unitário do produto.
     * 
     * @param float volumeUnitary - Volume correto para alteração.
   */
  
   public void setVolumeUnitary(float volumeUnitary){
        this.volumeUnitary= volumeUnitary>0 ? volumeUnitary : this.volumeUnitary;
   }
   

}
