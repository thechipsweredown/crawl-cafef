package crawl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class SplitFile {
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("dien_lanh");
        BufferedReader br = new BufferedReader(new FileReader(file));
        JSONParser parser = new JSONParser();
        BufferedWriter bw3sao = new BufferedWriter(new FileWriter(new File("negative_dien_lanh")));

        while (br.ready()) {
            String line = br.readLine();
            try {

                JSONObject json = (JSONObject) parser.parse(line);
                json.put("sentiment", "n");
                String numberStar = json.get("star").toString();
                if (numberStar.equals("1.0")||numberStar.equals("2.0") ) {
                    bw3sao.write(json.toString());
                    bw3sao.newLine();
                }
            } catch (Exception e) {
                System.out.println(line);
            }
        }

        br.close();
        bw3sao.close();
    }
}
