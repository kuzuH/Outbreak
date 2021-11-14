package outbreak.main;


public class Outbreak {
	private Outbreak() {
		// Private constructor because a utility class should not be instantiable.
	}

	public static void main(String[] args) {
		// This is a workaround for a known issue when starting JavaFX applications
		OutbreakApplication.startApp(args);
	}
}
