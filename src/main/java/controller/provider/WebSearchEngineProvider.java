package controller.provider;

import bean.Document;
import bean.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.BaseRepository;
import util.RankUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class WebSearchEngineProvider {

    // Index document by extracting keywords

    // @param request       HttpServletRequest
    // @param form          MultivaluedMap

    // @return JSONObject   result

    public static JSONObject getUrlList(String searchQuery, Integer start, Integer length){
        JSONObject result = new JSONObject();
        BaseRepository baseRepository = new BaseRepository();

        ArrayList<Document> documents = baseRepository.readDocuments();
        ArrayList<Row>      inputFile = baseRepository.readInputFile();

        JSONArray rankedList = new RankUtil().getRankedList(documents, inputFile, searchQuery, start, length);

        result.put("rankedList", rankedList);
        result.put("status", 200);

        return result;
    }

    public static JSONObject getDefinitionsAndImages(MultivaluedMap<String, String> form){
        JSONObject result               = new JSONObject();
        BaseRepository baseRepository   = new BaseRepository();
        List<String> urlList            = form.get("urlList[]");
        List<String> orderList          = form.get("orderList[]");
        ArrayList<Document> documents   = baseRepository.readDocuments();
        JSONArray resultList            = new JSONArray();

        try {
            for(int i=0; i<urlList.size(); i++){
                JSONObject tempObject           = new JSONObject();
                org.jsoup.nodes.Document doc    = Jsoup.connect(urlList.get(i)).get();

                tempObject.put("order", orderList.get(i));
                tempObject.put("url", urlList.get(i));

                Elements metaTags = doc.getElementsByTag("meta");

                for (Element metaTag : metaTags) {
                    String name = metaTag.attr("name");

                    if(name.equals("description")) {
                        tempObject.put("description", metaTag.attr("content"));
                        break;
                    }
                }

                Elements img = doc.getElementsByTag("img");
                String header = baseRepository.findDocumentFromUrl(urlList.get(i), documents);
                for (Element el : img) {
                    if(el.attr("alt").contains(header)){
                        tempObject.put("image", el.attr("src"));

                        break;
                    }
                }

                if(tempObject.length() == 3){
                    tempObject.put("image", "no image found");
                }

                resultList.put(tempObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        result.put("data", resultList);
        result.put("status", 200);
        return result;
    }

}
