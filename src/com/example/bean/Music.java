package com.example.bean;

import android.widget.ImageView;

public class Music {
	private String musicName;
	private String Artist;
	private String musicPath;
	private int image;
	
	public Music(String musicName, String artist, String musicPath) {
		super();
		this.musicName = musicName;
		Artist = artist;
		this.musicPath = musicPath;
	}
	
	public Music(String musicName, String artist, String musicPath,
			int image) {
		super();
		this.musicName = musicName;
		Artist = artist;
		this.musicPath = musicPath;
		this.image = image;
	}

	/**
	 * @return the musicName
	 */
	
	public String getMusicName() {
		return musicName;
	}
	/**
	 * @return the image
	 */
	public int getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(int image) {
		this.image = image;
	}
	/**
	 * @param musicName the musicName to set
	 */
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	/**
	 * @return the artist
	 */
	public String getArtist() {
		return Artist;
	}
	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		Artist = artist;
	}
	/**
	 * @return the musicPath
	 */
	public String getMusicPath() {
		return musicPath;
	}
	/**
	 * @param musicPath the musicPath to set
	 */
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	
}
