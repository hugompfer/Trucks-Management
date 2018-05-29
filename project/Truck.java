
/**
 * A classe Truck representa um camião, que serve para transporte de mercadorias(packs) atraves de um contentor por varios locais.
 * 
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Truck
{
    // variáveis de instância 
    private static String designation;//variavel para criação da designação
    public String identification;// desginação do camião
    private Container container;//Contentor do camião   
    private Coordinate coordinate;//cordeanas atuais do camião
    private int totalOfKilimeters;//total de quilometros
    private int quilometersLastInpection;//quilometros desde a ultima inspeçao
    private int numberOfInspection;//numero de inspeçoes

    /**
     * Cria um camião, com as coordenadas com que o utilizador pretender, com os quilometros,
     * number de inspeçoes a 0,tendo uma designação gerada automaticamente, sem nenhum contentor caregado.
     */
    public Truck(double latitude, double longitude)
    {
        designation=generateDesignation();
        identification=designation;
        coordinate=new Coordinate(latitude,longitude);
        totalOfKilimeters=0;
        quilometersLastInpection=0;
        numberOfInspection=0;
        container=null;
    }

    /**
     * Cria um novo camião, na posição de uma garagem que o utilizador pretende, com o quilometros,
     * number de inspeçoes a 0,tendo uma designação gerada automaticamente, sem nenhum contentor caregado.
     */
    
    public Truck(Garage garage)
    {
        designation=generateDesignation();
        identification=designation;
        if(garage!=null){
            coordinate=new Coordinate(garage.getCoordinates().getLatitude(),garage.getCoordinates().getLongitude());
        }else {
            coordinate=new Coordinate(0,0);
        }
        totalOfKilimeters=0;
        quilometersLastInpection=0;
        numberOfInspection=0;
        container=null;
    }
        
     /**
     * Cria um novo camião, na posição de uma estação que o utilizador pretende, com o quilometros,
     * number de inspeçoes a 0,tendo uma designação gerada automaticamente, sem nenhum contentor caregado.
     */

    public Truck(SupplyDistribuitionStation station)
    {
        designation=generateDesignation();
        identification=designation;
        if(station!=null){
            coordinate=new Coordinate(station.getCoordinates().getLatitude(),station.getCoordinates().getLongitude());
        }else{
            coordinate=new Coordinate(0,0);
        }
        totalOfKilimeters=0;
        quilometersLastInpection=0;
        numberOfInspection=0;
        container=null;
    }
    
    /**
     * hashCode - Metodo que retorna o hashCode do objeto Truck.
     * 
     * @return int- numero do hashCode.
     */
    public int hashCode() {
        return this.identification.hashCode();
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
        final Truck other = (Truck) obj;
        return this.identification.equalsIgnoreCase(other.getIdentification());
    }
    
    //gera um codigo de identificação para um camião atraves do abecedário e numeros
    private String generateDesignation(){
        char [] letters="ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String word="";
        if(designation==null || designation.equals("")){
            return "A01";
        }else{ 
            if(designation.substring(1).equals("99")){
                for(int i =0;i<letters.length;i++)
                {
                    if(designation.charAt(0)==letters[i]){
                        word=letters[i+1]+"01";
                        break;
                    }
                }  
            }else{
                String number=designation.charAt(1)+"" + designation.charAt(2)+"";
                int correctNumber=0;
                if(designation.charAt(1)=='0' && designation.charAt(2)< '9'){
                    correctNumber=convertToInt(""+designation.charAt(2));
                    number="0"+ ++correctNumber;     
                }else if(designation.charAt(1)=='0' && designation.charAt(2)=='9'){
                    number="10";
                }else{
                    int result;
                    result =convertToInt(number);
                    number=""+ ++result;
                }
                word=designation.charAt(0)+ number+"";
            }
            return word;
        }
    }
    
    //converte uma string em numero
    private int convertToInt(String number){
        return Integer.parseInt(number);  
    }
    
    /**
     * moveTruck - Metodo para mover o camião para outra posição,incrementando o nº de quilometros.
     * 
     * @param double latitude-Latitude para onde o camião se quer mover.
     * @param double longitude-Longitude para onde o camião se quer mover.
     * 
     * @return boolean- true: Se o camião se mover, em contrario false.
     *                  
     */

    public boolean moveTruck(double latitude, double longitude){ 
        double quilometers = coordinate.getQuilometers(latitude,longitude);
        totalOfKilimeters += quilometers;
        quilometersLastInpection+=quilometers;
        return quilometers!=0;
    }

      /**
     * getContainer - Metodo que retorna o contentor que está dentro do camião.
     * 
     * @return Container - Contentor carregado.                
     */

    public Container getContainer(){
        return container;
    }
    
    /**
     * getQuilometers - Metodo que retorna o numero total de quilometros.
     * 
     * @return int - Numero de quilometros totais.                
     */

    public int getQuilometers(){
        return totalOfKilimeters;
    }
    
    /**
     * getQuilometersLastInspection -Metodo que retorna o numero de quilometros desde a ultima inspeção.
     * 
     * @return Container - Numero de quilometros desde a ultima inspeção.                
     */

    public int getQuilometersLastInspection(){
        return quilometersLastInpection;
    }
    
      /**
     * load - Metodo para caregar o camião com um contentor,
     * sendo que só existe carregamento se o contentor for valido e este já não estiver caregado.
     * 
     * @param Container container-contentor que pretende caregar.                
     */
    public boolean load(Container container)
    {
        if(container==null) {
            System.out.println("Este contentor não é válido.");
        }else if(this.container!=null){
            System.out.println("O camião já está carregado.");
        }else{
            
            this.container=container;
            return true;
        }
        return false;
    }
    
      /**
     * unLoad - Metodo que retorna o contentor que está dentro do camião,retirando este no camião.
     * 
     * @return Container - Contentor retirado.                
     */

    public Container unLoad(){
        Container containers=this.container;
        this.container=null;
        return containers;
    }

     /**
     * alterNumberOfInspections - Metodo para inspecionar o camião, reiniciando os quilometros e incrementando numero de inspeçoes .
     *           
     */

    public void alterNumberOfInspections(){
        numberOfInspection++;
        quilometersLastInpection=0;
    }

    /**
     * getIdentification - Metodo para mostrar a identificação do camião.
     * 
     * @return String - Identificação do camião.
     */
    
    public String getIdentification(){
        return identification;
    }
    
    /**
     * getCoordinates - Metodo para mostrar a posição do camião.
     * 
     * @return Coordinate - Cordenadas do camião.
     */

    public Coordinate getCoordinates(){
        return coordinate;
    }
    
      /**
     * toString - Metódo que representa textualmente o objeto(identificação,contentor,coordenadas,
     * total de quilometros, total de quilometros desde a ultima inspeçao e o numero de inspeções).
     * 
     * @return String- Informação do camião.
     */
    
    public String toString(){
        return "Identificação : "+identification+"\n Contentor " +container+
        "\n Cordenadas: \n "+coordinate+ " \n Total de quilometros : "+totalOfKilimeters+
        "\n Total de quilometros desde a ultima inspeção : "+quilometersLastInpection+
        "\n Numero de inspeções : "+numberOfInspection;

    }
}
