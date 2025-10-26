package com.sema.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ReportPathResolver {

    private ReportPathResolver() {}

    /**
     * Öncelik sırası:
     * 1) -DreportPath=... ile açıkça verilmiş yol
     * 2) Jenkins WORKSPACE env (varsa) + target/...
     * 3) user.dir (proje kökü) + target/...
     */
    public static Path resolveCucumberHtml() {
        // 1) Manuel override (CI’de parametre verebilirsin)
        String explicit = System.getProperty("reportPath");
        if (explicit != null && !explicit.isBlank()) {
            return Paths.get(explicit).toAbsolutePath().normalize();
        }

        // 2) Jenkins WORKSPACE varsa onu kök al, yoksa user.dir
        String workspace = System.getenv("WORKSPACE");
        Path root = (workspace != null && !workspace.isBlank())
                ? Paths.get(workspace)
                : Paths.get(System.getProperty("user.dir"));

        // 3) Yaygın iki konumu sırayla dene
        Path p1 = root.resolve("target").resolve("cucumber-reports").resolve("cucumber.html");
        if (Files.exists(p1)) return p1.toAbsolutePath().normalize();

        Path p2 = root.resolve("target").resolve("cucumber-html-report").resolve("index.html");
        return p2.toAbsolutePath().normalize(); // yoksa yine de bunu döndür (göndermeden önce exists kontrolü yaparız)
    }

    /** Rapor var mı ve boş değil mi? */
    public static boolean isReportReady(Path report) {
        try {
            return Files.exists(report) && Files.size(report) > 0;
        } catch (IOException e) {
            return false;
        }
    }
}

