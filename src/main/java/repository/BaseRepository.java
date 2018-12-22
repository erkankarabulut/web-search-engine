package repository;

import bean.Document;
import bean.Row;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Scanner;

public class BaseRepository {

    public ArrayList<Document> readDocuments(){
        ArrayList<Document> documentList = new ArrayList<>();

        try{
            File file =
                    new File("/home/erkan/Desktop/web-search-engine/data/source.txt");
            Scanner sc = new Scanner(file);

            Integer documentId = new Integer(0);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] components = line.split("/\\*/");

                Document document = new Document();
                document.setDocument(components[0]);
                document.setUrl(components[1]);
                document.setDocumentId(documentId++);

                documentList.add(document);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return documentList;
    }

    public ArrayList<Row> readInputFile(){
        ArrayList<Row> inputList = new ArrayList<>();

        try{
            File file = new File("/home/erkan/Desktop/web-search-engine/data/input.txt");
            Scanner sc   = new Scanner(file);

            while (sc.hasNextLine()){
                String line         = sc.nextLine();
                String[] components = line.split("/\\*/");
                Row tempRow         = new Row();
                tempRow.setRotateCount(Integer.parseInt(components[1]));
                tempRow.setDocumentPointer(Integer.parseInt(components[2].substring(0, components[2].length()-1)));
                tempRow.setWords(components[0].split(" "));

                inputList.add(tempRow);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return inputList;
    }

    public String findDocumentFromUrl(String url, ArrayList<Document> documents){
        String document = new String();
        for(Document doc : documents){
            if(doc.getUrl().equalsIgnoreCase(url)){
                document = doc.getDocument();
                break;
            }
        }

        return document;
    }
}
