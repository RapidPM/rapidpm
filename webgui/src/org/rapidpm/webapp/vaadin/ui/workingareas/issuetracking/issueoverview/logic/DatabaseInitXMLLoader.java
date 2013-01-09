package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.persistence.GraphBaseDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.12.12
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseInitXMLLoader {
    private static final Logger logger = Logger.getLogger(DatabaseInitXMLLoader.class);

    public DatabaseInitXMLLoader() {
    }

    public <T> List<T> initDatatype(final Class typeClass, final GraphBaseDAO<T> dao) {
        final List<T> list = dao.loadAllEntities();
        if (list.isEmpty()) {
            if (logger.isDebugEnabled())
                logger.debug("Create entity for: "+ typeClass.getSimpleName());
            try {
                final InputStream inputStream;
                inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.ISSUE_SETTINGS_XML_PATH);
                if (inputStream == null) {
                    throw new FileNotFoundException(Constants.ISSUE_SETTINGS_XML_PATH);
                }
                final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                final Document doc = dBuilder.parse(inputStream);
                doc.getDocumentElement().normalize();

                final NodeList nodes = doc.getElementsByTagName(typeClass.getSimpleName());

                for (int i = 0; i < nodes.getLength(); i++) {
                    final T instance = (T) typeClass.newInstance();
                    final NodeList childNodes = nodes.item(i).getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        final Node node = childNodes.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            final Element element = (Element) node;
                            final Field field = typeClass.getDeclaredField(getValue("name", element));
                            boolean isAccessible = field.isAccessible();
                            field.setAccessible(true);
                            if (field.getType().equals(Integer.class)) {
                                field.set(instance, Integer.valueOf(getValue("value", element)));
                            } else {
                                field.set(instance, getValue("value", element));
                            }


                            field.setAccessible(isAccessible);
                        }
                    }
                    list.add(dao.persist(instance));
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | IOException | SAXException | ParserConfigurationException e) {
             logger.error(e);
            }
        }  else {
            if (logger.isDebugEnabled())
                logger.debug(typeClass.getSimpleName() + " has at least one entry");
        }

        return list;
    }

    private static String getValue(String tag, Element element) {
        final NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        final Node node = nodes.item(0);
        return node.getNodeValue();
    }

//    public static void main(String[] args) {
//        IssuePriorityDAO dao = DaoFactorySingelton.getInstance().getIssuePriorityDAO();
//        DatabaseInitXMLLoader init = new DatabaseInitXMLLoader();
//        init.initDatatype(IssuePriority.class, dao.loadAllEntities(), dao);
//    }

}
