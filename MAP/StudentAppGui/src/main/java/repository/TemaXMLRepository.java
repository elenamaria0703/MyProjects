package repository;

import config.IOHandler;
import domain.Tema;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;


public class TemaXMLRepository extends InMemoryRepository<Integer, Tema> {
    private String filename;

    public TemaXMLRepository(Validator<Tema> validator,String fileN){
        super(validator);
        try {
            this.filename= IOHandler.getProperties().getProperty(fileN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        loadData();
    }

    private void loadData(){
        try{
            Document document= DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(filename);
            Element root= document.getDocumentElement();
            NodeList children=root.getChildNodes();
            for(int i=0;i<children.getLength();i++){
                Node temaElement=children.item(i);
                if(temaElement instanceof Element){
                    Tema tema=createTemaFromElement((Element)temaElement);
                    super.save(tema);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Tema createTemaFromElement(Element temaElement) {
        Integer id=Integer.parseInt(temaElement.getAttribute("id"));
        String descriere=temaElement.getElementsByTagName("descriere").item(0).getTextContent();
        Integer start=Integer.parseInt(temaElement.getElementsByTagName("startWeek").item(0).getTextContent());
        Integer deadline=Integer.parseInt(temaElement.getElementsByTagName("deadlineWeek").item(0).getTextContent());
        Tema tema=new Tema(descriere);
        tema.setDeadlineWeek(deadline);
        tema.setStartWeek(start);
        tema.setId(id);
        return tema;
    }


    private  void writeToFile(){
        try{
            Document document=DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root=document.createElement("teme");
            document.appendChild(root);
            super.findAll().forEach(tema->{
                Element element=createElementFromTema(document,tema);
                root.appendChild(element);
            });
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            Source source=new DOMSource(document);
            transformer.transform(source,new StreamResult(filename));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Element createElementFromTema(Document document, Tema tema) {
        Element element=document.createElement("tema");
        element.setAttribute("id",tema.getId().toString());

        Element descriere=document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        element.appendChild(descriere);

        Element start=document.createElement("startWeek");
        start.setTextContent(tema.getStartWeek().toString());
        element.appendChild(start);

        Element deadline=document.createElement("deadlineWeek");
        deadline.setTextContent(tema.getDeadlineWeek().toString());
        element.appendChild(deadline);

        return element;
    }

    @Override
    public Tema save(Tema entity) {
        Tema tema=super.save(entity);
        if(tema==null){
            writeToFile();
        }
        return tema;
    }
}
