import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("""
                1) AED – Dirham de los Emiratos Árabes Unidos
               \s
                2) AFN – Afgani Afgano
               \s
                3) ALL – Lek Albanés
               \s
                4) AMD – Dram Armenio
               \s
                5) CNY - Yuan Chino
               \s
                6) JPY - Yen Japones\s
               \s
                7) ARS – Peso Argentino
               \s
                8) AUD – Dólar Australiano
               \s
                9) EUR - Euro
           \s
                10)USD - Dolar Americano
               \s""");
        do {
            RecordMoney record;
            Double Odato = 0.0;
            List<String> list = menu();
            if(list.get(0).equals("exit")){break;}
            if(list.get(1).equals("exit")){break;}
            if(list.get(2).equals("exit")){break;}
            int Idato = Integer.parseInt(list.get(1));
            record = requestData(list.get(0));
            for (var i : record.conversion_rates().entrySet()) {
                if (list.get(2).equals(i.getKey())) {
                    Odato = i.getValue();
                    break;
                }
            }
            double result = calculation(Idato, Odato);
            System.out.println("El resultado de la conversión es: " + result + " " + list.get(2));
            list.clear();
        } while (true);

    }
    public static double calculation(int i,double o){
        return Math.round(i*o * 10000.0) / 10000.0;
    }
    public static List<String> menu(){
        System.out.println("Conversor de monedas");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la clase de moneda a convertir :");
        String  inCoin = scanner.nextLine();
        System.out.println("Ingrese la cantidad :");
        String amount = scanner.nextLine();
        System.out.println("Ingrese el tipo de moneda que desea convertir :");
        String ouCoin = scanner.nextLine();

        List<String> list = new ArrayList<>();
        Collections.addAll(list,inCoin,amount,ouCoin);
        System.out.println("exit para salir");
        return list;
    }
    public static RecordMoney requestData(String s) throws IOException, InterruptedException {
        String api = "https://v6.exchangerate-api.com/v6/3fae5947c6d00f460bdbf9d4/latest/"+s;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(api)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson.fromJson(json, RecordMoney.class);
    }


}
