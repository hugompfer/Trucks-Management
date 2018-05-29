/**
 * A classe Coordinate representa uma posiçao no mapa tendo os eixos do X (Longitude) e Y(Latitude).
 * @author (Hugo Ferreira) 
 * @version (6/01/2017)
 */
public class Coordinate
{
    // variáveis de instância 
    private double latitude;//posição Y
    private double longitude;//posição X
    /**
     * Cria uma cordenada com a posição X(Longitude) com o intervalo -180 a 180, Y(Latitude) com o intervalo -90 a 90.
     */
    public Coordinate(double latitude,double longitude)
    {
        this.latitude=latitude < -90.0 ? -90.0 : latitude>90.0 ? 90.0 : latitude;
        this.longitude=longitude < -180.0 ? -180.0 : longitude > 180.0 ? 180.0 : longitude;
    }

    /**
     * getLatitude - Metodo para retornar a latitude da cordenada.
     * 
     * @return double - Latitude da posição.
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * getLongitude - Metodo para retornar a longitude da cordenada.
     * 
     * @return double - Longitude da posição.
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * setLatitude - Metodo para alterar a latitude da cordenada, com o intervalo -90 a 90
     * 
     * @param latitude - Nova latitude da cordenada.
     */
    public void setLatitude(double latitude){     
        this.latitude=latitude > 90 ? 90 : latitude <-90 ? -90 :latitude;  
    }

    /**
     * setLongitude - Metodo para alterar a longitude da cordenada, com o intervalo -180 a 180
     * 
     * @param longitude - Nova longitude da cordenada.
     */
    public void setLongitude(double longitude){  
        this.longitude=longitude >180 ? 180 : longitude <-180 ? -180 :longitude;     
    }

    /**
     * toStringLatiude - Metódo que representa textualmente o latitude da cordenada(Sul,Norte).
     * 
     * @return String- Informação do latitude.
     */
    public String toStringLatiude(){
        String latitude="";

        if(this.latitude<0){
            latitude="Sul";
        }else if(this.latitude>0){
            latitude="Norte";
        }else{
            latitude="Equador";
        }
        return "Latitude : "+this.latitude+"- "+latitude;
    }

    /**
     * toStringLongitude - Metódo que representa textualmente o longitude da cordenada(Este,Oeste).
     * 
     * @return String- Informação da longitude.
     */
    public String toStringLongitude(){
        String longitude="";
        if(this.longitude<0){
            longitude="Oeste";
        }else if(this.longitude>0){
            longitude="Este";
        }else{
            longitude="Equador"; 
        }
        return " Longitude : "+this.longitude+"- "+longitude;

    }  

    //faz a conta do quilometros feitos, se as posiçoes atual e posiçao pretendida forem diferentes,
    public double getQuilometers(double latitude,double longitude){
        double lastLatitude=this.latitude; 
        double lastLongitude=this.longitude;
        setLatitude(latitude);
        setLongitude(longitude); 
        if(checkPosition(lastLatitude,lastLongitude)){
            System.out.println("O camião não se mover para a mesma posiçao");
            return 0.0;
        }else{
            System.out.println("O caminhão moveu-se de : \n Latitude: "+lastLatitude+
                " para "+toStringLatiude()+"\n Longitude:"+lastLongitude+" para "
                +toStringLongitude()+" .");
            return calculateDistance(lastLatitude,lastLongitude)*60*1852;
        }
    }

    //calcula da distancia entre dois pontos
    private double calculateDistance(double latitude,double longitude){
        double X=Math.pow(this.latitude-latitude,2);
        double Y=Math.pow(this.longitude-longitude,2);
        return Math.sqrt(X+Y);
    }
    
    /**
     * isInRadius - Metódo que verifica se a distancia entre a cordenada atual e a cordenada pretendia e menor que a distancia dada.
     * 
     * @return boolean- True se for menor a distancia;false o contrario.
     * @param float distance- raio maximo
     * @param double latitude- latitude pretendida
     * @param double longitude- longitude pretendida
     */
    public boolean isInRadius(float distance,double latitude,double longitude){
        if(distance<0 || latitude <-90 || latitude >90 || longitude <-180 || longitude>180){
            return false;
        }else{
            return calculateDistance(latitude,longitude)<=distance;
        }

    }

    /**
     * checkPosition - Metódo que verifica se a posição atual e igual a posiçao pretendida.
     * 
     * @return boolean-se a posição for a mesma.
     * @param double latitude- latitude pretendida
     * @param double longitude- longitude pretendida
     */
    public boolean checkPosition(double latitude,double longitude){
        if(this.latitude==latitude && this.longitude==longitude){
            return true;
        }
        return false;
    }
    
    /**
     * toString - Metódo que representa textualmente o objeto(latitude,longitude).
     * 
     * @return String- Informação da cordenada.
     */
    public String toString(){
        return toStringLatiude()+toStringLongitude();
    }
}
