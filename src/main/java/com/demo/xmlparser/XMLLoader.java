package com.demo.xmlparser;

import com.demo.xmlparser.dto.DistrictDTO;
import com.demo.xmlparser.dto.MunicipalityDTO;
import com.demo.xmlparser.service.DistrictService;
import com.demo.xmlparser.service.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class XMLLoader {


    MunicipalityService municipalityService;
    DistrictService districtService;
    FileDownloader fileDownloader;
    private final String URL = "https://www.smartform.cz/download/20210331_OB_573060_UZSZ.xml.zip";
    private final String FILE_PATH = "data.xml";

    @Autowired
    public XMLLoader(MunicipalityService municipalityService, DistrictService districtService, FileDownloader fileDownloader) {
        this.municipalityService = municipalityService;
        this.districtService = districtService;
        this.fileDownloader = fileDownloader;
        loadData();
    }

    public void loadData(){
        try {
            fileDownloader.downloadFile(URL, FILE_PATH+ ".zip");
            fileDownloader.unzipFile(FILE_PATH+".zip", FILE_PATH);

            File inputFile = new File(FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList municipalityNodeList = doc.getElementsByTagName("vf:Obec");
            for (int temp = 0; temp < municipalityNodeList.getLength(); temp++) {
                String code = loadStringFromAttribute("obi:Kod", municipalityNodeList.item(temp));
                String name = loadStringFromAttribute("obi:Nazev", municipalityNodeList.item(temp));
                municipalityService.create(new MunicipalityDTO(code,name));
            }
            NodeList districtNodeList = doc.getElementsByTagName("vf:CastObce");
            for (int temp = 0; temp < districtNodeList.getLength(); temp++) {
                String code = loadStringFromAttribute("coi:Kod", districtNodeList.item(temp));
                String name = loadStringFromAttribute("coi:Nazev", districtNodeList.item(temp));
                Node municipality = null;
                for(int i = 0; i < districtNodeList.item(temp).getChildNodes().getLength(); i++){
                    if(districtNodeList.item(temp).getChildNodes().item(i).getNodeName().equals("coi:Obec")){
                        municipality = districtNodeList.item(temp).getChildNodes().item(i);
                        break;
                    }
                }
                String municipalityCode = "";
                if(municipality != null) {
                    municipalityCode = loadStringFromAttribute("obi:Kod", municipality);
                }
                districtService.create(new DistrictDTO(code,name,municipalityCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadStringFromAttribute(String attribute, Node node){
        NodeList attributes = node.getChildNodes();
        for(int i = 0; i < attributes.getLength(); i++){
            if(attributes.item(i).getNodeName().equals(attribute)){
                return attributes.item(i).getTextContent();
            }
        }
        return "";
    }


}
