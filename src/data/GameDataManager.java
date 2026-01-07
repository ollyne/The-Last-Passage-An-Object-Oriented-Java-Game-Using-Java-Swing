package data;

import java.io.*;
import java.util.*;

public class GameDataManager {

    private static final String FILE_NAME = "scores.txt";

    // SIMPAN SCORE
    public static void saveScore(String name, int score) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(name + "," + score + "\n");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan score");
        }
    }
    
    // AMBIL RECORD TERAKHIR
    public static List<Record> loadLatestRecords(int limit) {

        List<Record> records = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return records;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 2) {
                    records.add(new Record(p[0], Integer.parseInt(p[1])));
                }
            }
        } catch (Exception e) {
            return records;
        }

        Collections.reverse(records);
        return records.subList(0, Math.min(limit, records.size()));
    }

    // AMBIL BEST RECORD (TERCEPAT)
    public static Record loadBestRecord() {

        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        Record best = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 2) {
                    Record r = new Record(p[0], Integer.parseInt(p[1]));
                    if (best == null || r.getTime() < best.getTime()) {
                        best = r;
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return best;
    }
}
