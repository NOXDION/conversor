import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ClienteTasaDeCambio {
    public OkHttpClient cliente = new OkHttpClient();
    public String claveApi = "d5b4328757fd3722093ec313";
    public String urlBase = "https://v6.exchangerate-api.com/v6/";
    private static final Logger LOGGER = Logger.getLogger(ClienteTasaDeCambio.class.getName());
    private final Gson gson = new Gson();

    public Map<String, Double> obtenerTasasPrincipales(String monedaBase) {
        Map<String, Double> tasasPrincipales = new HashMap<>();
        String[] monedasPrincipales = {"USD", "EUR", "GBP", "JPY", "CAD", "CHF", "AUD", "NZD"};

        for (String moneda : monedasPrincipales) {
            String tasa = obtenerTasaDeCambio(monedaBase, moneda);
            try {
                JsonObject respuestaJson = gson.fromJson(tasa, JsonObject.class);
                double tasaDeCambio = respuestaJson.get("conversion_rate").getAsDouble();
                tasasPrincipales.put(moneda, tasaDeCambio);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al parsear la tasa de cambio para " + moneda, e);
            }
        }

        return tasasPrincipales;
    }

    public String obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) {
        String url = urlBase + claveApi + "/pair/" + monedaOrigen + "/" + monedaDestino;
        Request solicitud = new Request.Builder()
                .url(url)
                .build();

        try (Response respuesta = cliente.newCall(solicitud).execute()) {
            assert respuesta.body() != null;
            String respuestaString = respuesta.body().string();
            // Parsear el JSON para obtener realmente la tasa de cambio
            JsonObject respuestaJson = gson.fromJson(respuestaString, JsonObject.class);
            return respuestaJson.get("conversion_rate").getAsString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la tasa de cambio", e);
            return null;
        }
    }
}