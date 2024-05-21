package backend.dbms.PocketBrawlers.Brawler.Attributes;

import backend.dbms.PocketBrawlers.Brawler.Brawler;

/**
 * @author Andrew Ahrenkiel
 */
public interface AttributeEffect {
    int doAffect(Brawler[] b1, Brawler[] b2, int attributeId, int brawlerPos) throws Exception;

}
