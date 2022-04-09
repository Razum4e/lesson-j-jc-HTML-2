import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.net.URL;

public class Main {
    public static final String URL_NASA = "https://api.nasa.gov/planetary/apod?api_key=fhl4d8oaufCXCEaDlCRvZP2XlpkTjebQU8iCe9dp";
    public static final String PATH_RESOURCES = "C:\\Users\\Asus\\Documents\\JavaProjects\\Http-2\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException, JSONException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(URL_NASA).openStream()));
//        JSONObject jsonObject = new JSONObject(reader.readLine());
//        reader.close();
//        String urlImage = (String) jsonObject.get("url");
//        Image image = ImageIO.read(new URL(urlImage));
//        File file = new File(PATH_RESOURCES + "FIRST_" + FilenameUtils.getName(urlImage));
//        ImageIO.write((RenderedImage) image, FilenameUtils.getExtension(urlImage), file);

        byte[] bodyNasa = getBody(URL_NASA);
        String bodyNasaString = new String(bodyNasa, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(bodyNasaString);
        String urlImage = (String) jsonObject.get("url");
        byte[] bodyImage = getBody(urlImage);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(PATH_RESOURCES
                + "SECOND_" + FilenameUtils.getName(urlImage)));
        out.write(bodyImage);
        out.close();
    }

    public static byte[] getBody(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        get.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        return httpResponse.getEntity().getContent().readAllBytes();
    }
}
