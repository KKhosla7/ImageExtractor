package http;

/**
 * Created by karan.khosla
 */
public class TunesDiary {

  private String album;
  private String songID;
  private int pcCount;
  private int lyricsAvailable;
  private String name;
  private String lyrics;
  private String artist;
  private String seoId;

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getSongID() {
    return songID;
  }

  public void setSongID(String songID) {
    this.songID = songID;
  }

  public int getPcCount() {
    return pcCount;
  }

  public void setPcCount(int pcCount) {
    this.pcCount = pcCount;
  }

  public int getLyricsAvailable() {
    return lyricsAvailable;
  }

  public void setLyricsAvailable(int lyricsAvailable) {
    this.lyricsAvailable = lyricsAvailable;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLyrics() {
    return lyrics;
  }

  public void setLyrics(String lyrics) {
    this.lyrics = lyrics;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getSeoId() {
    return seoId;
  }

  public void setSeoId(String seoId) {
    this.seoId = seoId;
  }
}
