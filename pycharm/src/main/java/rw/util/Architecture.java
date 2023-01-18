package rw.util;
import java.io.File;

public enum Architecture {
    X64("x64"),
    ARM64("arm64");

    public final String value;

    public static Architecture DETECTED;

    Architecture(String value) {
        this.value = value;
    }

    static {
        if (OsType.DETECTED == OsType.MacOS) {
            try {
                String brand = getBrandString();
                if (brand.contains("M1")) {
                    DETECTED = ARM64;
                }
                else {
                    DETECTED = X64;
                }
            }
            catch (java.io.IOException exception) {
                File rosetta = new File("/usr/libexec/rosetta/");
                String arch = System.getProperty("os.arch");
                if (rosetta.exists() || arch.contains("arm")) {
                    DETECTED = ARM64;
                } else {
                    DETECTED = X64;
                }
            }
        }
        else {
            DETECTED = X64;
        }
    }

    private static String getBrandString() throws java.io.IOException {
            java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec("sysctl -n machdep.cpu.brand_string").getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
    }
}