package minesweeper.views.fx;

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import minesweeper.views.utils.AssetsManager;

/**
 *  Sound Player Helper
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class SoundPlayer
{
	// player fields
	private static final int EXTERNAL_BUFFER_SIZE = 128000;
	private static final String SOUNDS_PATH = "sounds/"; // path for sounds
	private static SourceDataLine _line;
	private static boolean _playSound = true;
	
	/**
	 * toggle sounds on/off
	 */
	public static void toggleSound() {
		_line.close();
		_playSound = !_playSound;
	}
	
	/**
	 * play sound for new game
	 */
	public static void playNewGameSound() {
		// try play level's sound. if not exists- play the default sound
		URL soundFile = AssetsManager.getResource(SOUNDS_PATH + AssetsManager.getCurrentLevelPrefix() + "_newgame.wav");
		
		if (soundFile == null)
			soundFile = AssetsManager.getResource(SOUNDS_PATH + "newgame.wav");
		
		play(soundFile);
	}
	
	/**
	 * play sound for win game
	 */
	public static void playWinGameSound() {
		// try play level's sound. if not exists- play the default sound
		URL soundFile = AssetsManager.getResource(SOUNDS_PATH + AssetsManager.getCurrentLevelPrefix() + "_wingame.wav");
		
		if (soundFile == null)
			soundFile = AssetsManager.getResource(SOUNDS_PATH + "wingame.wav");
		
		play(soundFile);
	}
	
	/**
	 * play sound for flag
	 */
	public static void playFlagSound() {
		play(AssetsManager.getResource(SOUNDS_PATH + "mark.wav"));
	}
	
	/**
	 * play sound for lose game
	 */
	public static void playLoseGameSound() {
		// try play level's sound. if not exists- play the default sound
		URL soundFile = AssetsManager.getResource(SOUNDS_PATH + AssetsManager.getCurrentLevelPrefix() + "_losegame.wav");
		
		if (soundFile == null)
			soundFile = AssetsManager.getResource(SOUNDS_PATH + "losegame.wav");
		
		play(soundFile);
	}
	
	/**
	 * play sound for unflag
	 */
	public static void playUnflagSound() {
		play(AssetsManager.getResource(SOUNDS_PATH + AssetsManager.getCurrentLevelPrefix() + "_unmark.wav"));
	}
	
	/**
	 * play sound
	 * @param inputFile file's URL
	 */
	private static void play(URL inputFile) {
		if (_playSound) {		// check if sound is on, and stop any sound
			if (_line != null)
				_line.close();
			
			// final variable, for accessing from the sound's Thread
			final URL soundFile = inputFile;
			
			// play the sound in a different thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					// read file
					AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(soundFile);
						/*
						  From the AudioInputStream, i.e. from the sound file, we fetch information about the format of the audio data.
						  These information include the sampling frequency, the number of channels and the size of the samples.
						  These information are needed to ask Java Sound for a suitable output line for this audio file.
						 */
						AudioFormat	audioFormat = audioInputStream.getFormat();
			
						/*
						  Asking for a line is a rather tricky thing. We have to construct an Info object that specifies the desired properties for the line.
						  First, we have to say which kind of line we want. The possibilities are: SourceDataLine (for playback), Clip (for repeated playback) and TargetDataLine (forrecording).
						  Here, we want to do normal playback, so we ask for a SourceDataLine. Then, we have to pass an AudioFormat object, so that
						  the Line knows which format the data passed to it will have.
						  Furthermore, we can give Java Sound a hint about how big the internal buffer for the line should be. This
						  isn't used here, signaling that we don't care about the exact size. Java Sound will use some default value for the buffer size.
						 */
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			
						_line = (SourceDataLine) AudioSystem.getLine(info);
			
						/* The line is there, but it is not yet ready to receive audio data. We have to open the line. */
						_line.open(audioFormat);
			
						/* Still not enough. The line now can receive data, but will not pass them on to the audio output device (which means to your sound card). This has to be activated. */
						_line.start();
			
						/*
						  Ok, finally the line is prepared. Now comes the real job: we have to write data to the line. We do this in a loop. First, we read data from the
						  AudioInputStream to a buffer. Then, we write from this buffer to the Line. This is done until the end of the file is reached, which is detected by a
						  return value of -1 from the read method of the AudioInputStream.
						 */
						int	nBytesRead = 0;
						byte[]	abData = new byte[EXTERNAL_BUFFER_SIZE];
						while (nBytesRead != -1) {
							nBytesRead = audioInputStream.read(abData, 0, abData.length);
							if (nBytesRead >= 0) {
								_line.write(abData, 0, nBytesRead);
							}
						}
			
						/* Wait until all data are played. This is only necessary because of the bug noted below. (If we do not wait, we would interrupt the playback by prematurely closing the line and exiting the VM.) */
						_line.drain();
			
						/* All data are played. We can close the shop. */
						_line.close();
					}
					catch (Exception e)
					{
						System.err.println(e.getMessage());
					}
				}
			}).start();
		}
	}
}