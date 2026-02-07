package me.dartanman.duels.utils;

public class PaperUtils {
    private static Boolean isPaperServer;

    public static boolean isPaperServer() {
        if (isPaperServer != null) {
            return isPaperServer;
        }

        boolean isPaper = false;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
        }

        isPaperServer = isPaper;
        return isPaperServer;
    }
}
