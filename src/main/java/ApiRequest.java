import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequest {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.zippopotam.us/us/90201");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                ZippopotamResponse response = objectMapper.readValue(content.toString(), ZippopotamResponse.class);

                System.out.println("Resposta convertida em objeto:");
                System.out.println(response);

            } else {
                System.out.println("GET request não funcionou. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Data
class ZippopotamResponse {
    @JsonProperty("post code")
    private String postCode;

    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    private Place[] places;

    @Data
    static class Place {
        @JsonProperty("place name")
        private String placeName;

        private String longitude;
        private String state;

        @JsonProperty("state abbreviation")
        private String stateAbbreviation;

        private String latitude;
    }
}
