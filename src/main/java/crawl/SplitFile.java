package crawl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class SplitFile {
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("comment1");
        BufferedReader br = new BufferedReader(new FileReader(file));
        JSONParser parser = new JSONParser();
        BufferedWriter bw3sao = new BufferedWriter(new FileWriter(new File("5sao")));
//        BufferedWriter bw4sao = new BufferedWriter(new FileWriter(new File("1sao")));

        while (br.ready()) {
            String line = br.readLine();
            JSONObject json = (JSONObject) parser.parse(line);
            String numberStar = json.get("star").toString();
            if (numberStar.equals("5.0")) {
                bw3sao.write(line);
                bw3sao.newLine();
            }
//            if (numberStar.equals("1.0")){
//                bw4sao.write(line);
//                bw4sao.newLine();
//            }
        }
        br.close();
        bw3sao.close();
//        bw4sao.close();
    }
}
