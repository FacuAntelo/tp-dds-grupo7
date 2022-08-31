package MediosDeTransporte;

public class Horario {
    private int minutos;
    private int horas;
    public Horario(int minutos,int horas){
        this.minutos=minutos;
        this.horas= horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public int getHoras() {
        return horas;
    }
}
