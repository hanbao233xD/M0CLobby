package ltd.m0c.moclobby;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class Utils {

    public static void log(String log){
        getServer().getLogger().info(log);

    }
    public static void banplayer(String bancmd){getServer().dispatchCommand(getServer().getConsoleSender(), bancmd);}
    public static void sendok(Player player,String message){
        player.sendMessage(ChatColor.GREEN+message);
    }
    public  static  void senderr(Player player,String message){
        player.sendMessage(ChatColor.RED+message);
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    public  static  String readLastLine(File file, String charset)  throws  IOException {
        if  (!file.exists() || file.isDirectory() || !file.canRead()) {
            return  null ;
        }
        RandomAccessFile raf =  null ;
        try  {
            raf =  new  RandomAccessFile(file,  "r" );
            long  len = raf.length();
            if  (len == 0L) {
                return  "" ;
            }  else  {
                long  pos = len -  50 ;
                while  (pos >  0 ) {
                    pos--;
                    raf.seek(pos);
                    if  (raf.readByte() ==  '\n' ) {
                        break ;
                    }
                }
                if  (pos ==  0 ) {
                    raf.seek( 0 );
                }
                byte [] bytes =  new  byte [( int ) (len - pos)];
                raf.read(bytes);
                if  (charset ==  null ) {
                    return  new  String(bytes);
                }  else  {
                    return  new  String(bytes, charset);
                }
            }
        }  catch  (FileNotFoundException e) {
        }  finally  {
            if  (raf !=  null ) {
                try  {
                    raf.close();
                }  catch  (Exception e2) {
                }
            }
        }
        return  null ;
    }
    public static String getRandomString(int minLength, int maxLength) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length = random.nextInt(maxLength) % (maxLength - minLength + 1) + minLength;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }


}
