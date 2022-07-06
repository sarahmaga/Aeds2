import java.util.Random;
public class test {

    public static boolean end(String phrase) {
        return (phrase.length() == 3 && phrase.charAt(0) == 'F' && phrase.charAt(1) == 'I' && phrase.charAt(2) == 'M');
    }

    public static String code(String phrase, char l1, char l2) {
        String answer = "";

        for(int i = 0; i < phrase.length(); i++) {
            if(phrase.charAt(i) == l1) {
                answer += l2;
            }else {
                answer += phrase.charAt(i);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Random spawn = new Random();
        String phrase = "";
        spawn.setSeed(4);
        char l1 , l2;
        String answer = "";

        while(!end(phrase)) {
            phrase = MyIO.readLine();

            if(!end(phrase)) {
                l1 = (char)('a'+(Math.abs(spawn.nextInt()) % 26 ));
                l2 = (char)('a'+(Math.abs(spawn.nextInt()) % 26 ));
                answer = code(phrase, l1, l2);
                MyIO.println(answer);
            }
        }
    }
}

