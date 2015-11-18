package com.fantacalcio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Formazione.
 */
@Entity
@Table(name = "formazione")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formazione implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Min(value = 0)        
    @Column(name = "giornata", nullable = false)
    private Integer giornata;
    
    @Column(name = "titolare")
    private Boolean titolare;
    
    @Column(name = "valutazione", precision=10, scale=2, nullable = false)
    private BigDecimal valutazione;

    @ManyToOne
    private Giocatore giocatore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGiornata() {
        return giornata;
    }

    public void setGiornata(Integer giornata) {
        this.giornata = giornata;
    }

    public Boolean getTitolare() {
        return titolare;
    }

    public void setTitolare(Boolean titolare) {
        this.titolare = titolare;
    }

    public BigDecimal getValutazione() {
        return valutazione;
    }

    public void setValutazione(BigDecimal valutazione) {
        this.valutazione = valutazione;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Formazione formazione = (Formazione) o;

        if ( ! Objects.equals(id, formazione.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Formazione{" +
                "id=" + id +
                ", giornata='" + giornata + "'" +
                ", titolare='" + titolare + "'" +
                ", valutazione='" + valutazione + "'" +
                '}';
    }
}
