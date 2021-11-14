package outbreak.controller;

public class AudioPlayerAdapter {
	
	private AudioPlayer audioPlayer;
	
	public AudioPlayerAdapter() {
		this.audioPlayer = new AudioPlayer();
	}
	
	public void startMusic() {
		this.audioPlayer.playBackgroundMusic();
	}
	
	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}
}
