package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 17:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BuchAbsatzKommentar {
    private static final Logger logger = Logger.getLogger(BuchAbsatzKommentar.class);


    @TableGenerator(name = "PKGenBuchAbsatzKommentar", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "BuchAbsatzKommentar_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenBuchAbsatzKommentar")
    @Id
    private Long id;
    @Basic
    String kommentar;
    @Basic
    Date datum;
    @OneToOne
    private Benutzer kommentator;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(final String kommentar) {
        this.kommentar = kommentar;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(final Date datum) {
        this.datum = datum;
    }

    public Benutzer getKommentator() {
        return kommentator;
    }

    public void setKommentator(final Benutzer kommentator) {
        this.kommentator = kommentator;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BuchAbsatzKommentar");
        sb.append("{id=").append(id);
        sb.append(", kommentar='").append(kommentar).append('\'');
        sb.append(", datum=").append(datum);
        //        sb.append(", kommentator=").append(kommentator);
        sb.append('}');
        return sb.toString();
    }
}
