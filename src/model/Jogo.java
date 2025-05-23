package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import appconsole.Util;

public class Jogo {
	
	private int id;
    private String dataHora;
    private String local;
    private List<Ingresso> listaIngressos;
    
    private String timeA;
    private String timeB;
    private static int contador = 1;

    public Jogo(String dataHora, String local, String timeA, String timeB) {
        this.id = Util.getProximoIdJogo();
        this.dataHora = dataHora;
        this.local = local;
        this.timeA = timeA;
        this.timeB = timeB;
        this.listaIngressos = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public String getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
    
    public String getLocal() {
        return local;
    }
    
    public void setLocal(String local) {
        this.local = local;
    }
    
    public List<Ingresso> getListaIngressos() {
        return listaIngressos;
    }
    
    public String getTimeA() {
        return timeA;
    }
    
    public void setTimeA(String timeA) {
        this.timeA = timeA;
    }
    
    public String getTimeB() {
        return timeB;
    }
    
    public void setTimeB(String timeB) {
        this.timeB = timeB;
    }
    
    public void adicionarIngresso(Ingresso ingresso) {
        this.listaIngressos.add(ingresso);
    }
    
    public String gerarPrefixoCodigo() { // prefixo = 3 letras de cada time
        return timeA.substring(0, 3).toUpperCase() + timeB.substring(0, 3).toUpperCase();
    }
    
    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", local='" + local + '\'' +
                ", timeA='" + timeA + '\'' +
                ", timeB='" + timeB + '\'' +
                ", ingressos=" + listaIngressos.size() +
                '}';
    }
    
}
