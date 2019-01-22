package andreiiih.hypercubelib;

public final class HypercubeLibConstants {

    /* INTERNAL CONSTANTS */
    public static final String CACHE_FILE_EXT = ".cf";
    public static final byte[] CACHE_FILE_ID = "HLCF".getBytes();

    /* INTERNAL CACHETYPE-DEPENDANT CONSTANTS */
    public static final byte TYPE_MODEL = 0x01;
    public static final short MODEL_VERSION = 0x0001;

    /* MOD-DEPENDANT VARIABLES */
    private static String modId;

    /* HELPER METHODS */
    public static void setModId(String modId) {
        HypercubeLibConstants.modId = modId;
    }

    public static String getModId() {
        return modId;
    }

    protected static void writeModId(byte[] data, int offset) {
        // TODO: Implement writing of Mod ID directly to cache file
    }
}
