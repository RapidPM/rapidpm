package org.rapidpm.orm.prj.book.kommentar;

import org.rapidpm.orm.EntityFactory;
import org.rapidpm.orm.security.BenutzerEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.10.11
 * Time: 12:33
 */
public class BuchSeitenKommentarEntityFactory extends EntityFactory<BuchSeitenKommentar> {

    public BuchSeitenKommentarEntityFactory() {
        super(BuchSeitenKommentar.class);
    }

    @Override
    public BuchSeitenKommentar createRandomEntity() {
        final BuchSeitenKommentar kommentar = new BuchSeitenKommentar();
        kommentar.setKommentar(RND.nextSentence(3, 20, 2, 12));
        kommentar.setDatum(RND.nextDate());
        kommentar.setKommentator(new BenutzerEntityFactory().createRandomEntity());
        return kommentar;
    }
}
