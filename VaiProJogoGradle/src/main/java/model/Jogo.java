package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jogos")
public class Jogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_hora", nullable = false)
    private String dataHora;
    
    @Column(nullable = false)
    private String local;
    
    @Column(name = "time_a", nullable = false)
    private String timeA;
    
    @Column(name = "time_b", nullable = false)
    private String timeB;
    
    // Cascade: não delete, mas faça update e merge
    @OneToMany(mappedBy = "jogo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Ingresso> listaIngressos = new ArrayList<>();
    
    public Jogo() {}

    public Jogo(String dataHora, String local, String timeA, String timeB) {
        this.dataHora = dataHora;
        this.local = local;
        this.timeA = timeA;
        this.timeB = timeB;
        this.listaIngressos = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public void setListaIngressos(List<Ingresso> listaIngressos) {
        this.listaIngressos = listaIngressos;
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
        ingresso.setJogo(this);
    }
    
    public String gerarPrefixoCodigo() {
        if (timeA.length() >= 3 && timeB.length() >= 3) {
            return timeA.substring(0, 3).toUpperCase() + timeB.substring(0, 3).toUpperCase();
        }
        return timeA.toUpperCase() + timeB.toUpperCase();
    }
    
    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", dataHora='" + dataHora + '\'' +
                ", local='" + local + '\'' +
                ", timeA='" + timeA + '\'' +
                ", timeB='" + timeB + '\'' +
                ", ingressos=" + (listaIngressos != null ? listaIngressos.size() : 0) +
                '}';
    }
}