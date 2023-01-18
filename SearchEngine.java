import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> searchList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String result = "";
            for(int i = 0; i < searchList.size(); i++) {
                result += searchList.get(i) + "\n";
            }
            return "List of Searches:\n" + result;
        } else if (url.getPath().contains("/search")) {
            String[] parameters2 = url.getQuery().split("=");
            if (parameters2[0].equals("s")) {
                String result = "";
                for(int i = 0; i < searchList.size(); i++) {
                if(searchList.get(i).contains(parameters2[1])){
                    result += searchList.get(i) + "\n";
                }
                return result;
            }
            return "Not Found!";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) { 
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    searchList.add(parameters[1]);
                    return "Increased list by one element!";
                }
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
