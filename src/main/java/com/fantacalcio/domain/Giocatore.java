package com.fantacalcio.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fantacalcio.domain.util.CustomDateTimeDeserializer;
import com.fantacalcio.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Giocatore.
 */
@Entity
@Table(name = "giocatore")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Giocatore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull        
    @Column(name = "codice", nullable = false)
    private String codice;

    @NotNull        
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull        
    @Column(name = "nome_gazzetta", nullable = false)
    private String nome_gazzetta;

    @NotNull
    @Pattern(regexp = "^[PDCA]")        
    @Column(name = "ruolo", nullable = false)
    private String ruolo;
    
    @Column(name = "presenze")
    private Integer presenze;
    
    @Column(name = "cambio_in")
    private Integer cambio_in;
    
    @Column(name = "cambio_out")
    private Integer cambio_out;
    
    @Column(name = "gol")
    private Integer gol;
    
    @Column(name = "espulsioni")
    private Integer espulsioni;
    
    @Column(name = "ammonizioni")
    private Integer ammonizioni;
    
    @Column(name = "media_punti", precision=10, scale=2, nullable = false)
    private BigDecimal media_punti;
    
    @Column(name = "stato")
    private String stato;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "ultima_modifica", nullable = false)
    private DateTime ultima_modifica;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_gazzetta() {
        return nome_gazzetta;
    }

    public void setNome_gazzetta(String nome_gazzetta) {
        this.nome_gazzetta = nome_gazzetta;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Integer getPresenze() {
        return presenze;
    }

    public void setPresenze(Integer presenze) {
        this.presenze = presenze;
    }

    public Integer getCambio_in() {
        return cambio_in;
    }

    public void setCambio_in(Integer cambio_in) {
        this.cambio_in = cambio_in;
    }

    public Integer getCambio_out() {
        return cambio_out;
    }

    public void setCambio_out(Integer cambio_out) {
        this.cambio_out = cambio_out;
    }

    public Integer getGol() {
        return gol;
    }

    public void setGol(Integer gol) {
        this.gol = gol;
    }

    public Integer getEspulsioni() {
        return espulsioni;
    }

    public void setEspulsioni(Integer espulsioni) {
        this.espulsioni = espulsioni;
    }

    public Integer getAmmonizioni() {
        return ammonizioni;
    }

    public void setAmmonizioni(Integer ammonizioni) {
        this.ammonizioni = ammonizioni;
    }

    public BigDecimal getMedia_punti() {
        return media_punti;
    }

    public void setMedia_punti(BigDecimal media_punti) {
        this.media_punti = media_punti;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public DateTime getUltima_modifica() {
        return ultima_modifica;
    }

    public void setUltima_modifica(DateTime ultima_modifica) {
        this.ultima_modifica = ultima_modifica;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Giocatore giocatore = (Giocatore) o;

        if ( ! Objects.equals(id, giocatore.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Giocatore{" +
                "id=" + id +
                ", codice='" + codice + "'" +
                ", nome='" + nome + "'" +
                ", nome_gazzetta='" + nome_gazzetta + "'" +
                ", ruolo='" + ruolo + "'" +
                ", presenze='" + presenze + "'" +
                ", cambio_in='" + cambio_in + "'" +
                ", cambio_out='" + cambio_out + "'" +
                ", gol='" + gol + "'" +
                ", espulsioni='" + espulsioni + "'" +
                ", ammonizioni='" + ammonizioni + "'" +
                ", media_punti='" + media_punti + "'" +
                ", stato='" + stato + "'" +
                ", ultima_modifica='" + ultima_modifica + "'" +
                '}';
    }
}
