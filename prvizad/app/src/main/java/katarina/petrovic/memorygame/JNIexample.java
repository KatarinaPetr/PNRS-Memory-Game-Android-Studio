package katarina.petrovic.memorygame;

public class JNIexample {
    static {
        System.loadLibrary("GameLibrary");
    }
    public native double racunajProcenat(double score);
}
