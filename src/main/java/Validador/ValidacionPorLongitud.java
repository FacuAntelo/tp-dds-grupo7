package Validador;

public class ValidacionPorLongitud extends CriterioValidador{

    public ValidacionPorLongitud(){

    }

    @Override
    public boolean esValida(String clave) {
        boolean estadoDeValidacion = false;
        try{
            estadoDeValidacion = clave.length() > 8;
        }catch(Exception exception){
            System.out.println("No se pudo validar por longitud");
        }
        return estadoDeValidacion;
    }
}
