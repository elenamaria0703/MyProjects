package domain;


import java.io.*;

public class ThemeChanger {
    public static String getTheme(){
        try {
            File file = new File("C:\\Users\\Maria\\Desktop\\StudentAppGui\\src\\main\\resources\\files\\theme.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String theme=bufferedReader.readLine();
            return theme;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setTheme(String theme){
        try {
            FileWriter writer=new FileWriter("C:\\Users\\Maria\\Desktop\\StudentAppGui\\src\\main\\resources\\files\\theme.txt");
            writer.write(theme);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
