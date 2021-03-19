package snmp;

import snmp.entities.DeviceHolder;
import snmp.entities.OidHolder;

import java.io.*;
import java.util.*;

public class FileManager {

    public static PrintWriter writeFile(String file) {
        StringBuilder sb = new StringBuilder();
        PrintWriter out = null;
        try {
            String nl;
            File f = new File(file);
            out = new PrintWriter(f);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return out;
    }

    private static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        try {
            String cl;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((cl = br.readLine()) != null) {
                sb.append(cl).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static Set<DeviceHolder> getDeviceInfo() {
        HashMap<Map<String, String>, Map<String, String>> info = new HashMap<>();

        String data = readFile("devicefiles.txt");
        String[] d = data.split("\n");
        String[] g;
        Set<DeviceHolder> deviceHolder = new HashSet<>();
        for (String variables : d) {
            g = variables.split(" ");
            // id - 0 | device name(unique) - 1 | ip - 2 | oid - 3
            deviceHolder.add(new DeviceHolder(g[0], g[1], g[2], g[3]));
        }

        return deviceHolder;


    }

    public static Set<OidHolder> getOidInfo() {
        Set<OidHolder> oidHolder = new HashSet<>();
        String data = FileManager.readFile("oidfiles.txt");
        String[] d = data.split("\n");
        String[] g;
        for (String s : d) {
            g = s.split(" ");
            oidHolder.add(new OidHolder(g[0], g[1]));
        }
        return oidHolder;
    }

    public static Set<OidHolder> getDynamicOidInfo() {
        Set<OidHolder> dynamicOidHolder = new HashSet<>();
        String data = FileManager.readFile("dynamicoids.txt");
        String[] d = data.split("\n");
        String[] g;
        for (int i = 0; i < d.length; i++) {
            if (i != 0) {
                g = d[i].split(" ");
                dynamicOidHolder.add(new OidHolder(g[0], g[1]));
            }
        }
        return dynamicOidHolder;
    }


}
