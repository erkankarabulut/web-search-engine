package util;

import bean.Document;
import bean.Row;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RankUtil {

    public JSONArray getRankedList(ArrayList<Document> documents, ArrayList<Row> rows,
                                   String searchQuery, Integer start, Integer length){
        JSONArray rankedList = new JSONArray();

        String[] words = searchQuery.split(" ");
        HashMap<Integer, Integer> valueList = createValueList(documents);
        for(int i=0; i<words.length; i++){
            for(Row row : rows){
                if(row.getWords()[0].equalsIgnoreCase(words[i])){
                    valueList.put(row.getDocumentPointer(),
                            (valueList.get(row.getDocumentPointer()) + ((row.getWords().length - row.getRotateCount()) * (words.length-i))));
                }
            }
        }

        List<Integer> sortedDocumentIndexList = sortValueList(valueList, start, length);
        Integer order = 0;
        for (Integer documentIndex : sortedDocumentIndexList){
            JSONObject temp = new JSONObject();
            temp.put("documentId", documentIndex);
            temp.put("order", order++);
            temp.put("url", findDocumentURLHeaderFromIndex(documentIndex, documents).get(0));
            temp.put("header", findDocumentURLHeaderFromIndex(documentIndex, documents).get(1));

            rankedList.put(temp);
        }

        return rankedList;
    }

    public List<Integer> sortValueList(HashMap<Integer, Integer> valueList, Integer start, Integer length){
        ArrayList<Integer> sortedList = new ArrayList<>();
        Object[] a = valueList.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        for (Object e : a) {
            if(((Map.Entry<Integer, Integer>) e).getValue() != 0){
                sortedList.add(((Map.Entry<Integer, Integer>) e).getKey());
            }
        }

        return sortedList.subList(start, (sortedList.size() > (start+length) ? start+length : sortedList.size()));
    }

    public HashMap<Integer, Integer> createValueList(ArrayList<Document> documents){
        HashMap<Integer, Integer> values = new HashMap();
        for(Document document : documents){
            if(!values.containsKey(document.getDocumentId())){
                values.put(document.getDocumentId(), 0);
            }
        }

        return values;
    }

    public ArrayList<String> findDocumentURLHeaderFromIndex(Integer index, ArrayList<Document> list){
        ArrayList<String> urlAndHeader = new ArrayList<>();
        for(Document document : list){
            if(document.getDocumentId() == index){
                urlAndHeader.add(document.getUrl());
                urlAndHeader.add(document.getDocument());
                break;
            }
        }

        return urlAndHeader;
    }
}
