/* Author: Julia Bristow
 * CS 2113
 * 
 * Frame that shows a user-made playlist, info about 
 * the current song, and allows songs to be played
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jm.util.Play;

public class PlaylistFrame extends JFrame
{
	private JButton goToAdd;
	private JButton playAll;
	private JButton playNext;
	private JButton playRandom;
	private JLabel received;
	private JPanel south;
	private JPanel playButtons;
	private JPanel songInfo;
	private ListNode currSong;
	private JLabel currTitle;
	private JLabel currArtist;
	private JLabel currAlbum;
	
	void go (int width, int height, Playlist playlist, JTextArea list)
	{
		// set up initial frame
		this.setTitle("Music Player");
		this.setResizable(true);
		this.setSize(width, height);
		
		// start current song at the front of playlist
		currSong = playlist.head;
		// create a panel to put the song info in
		songInfo = new JPanel();
		// set Box Layout for songInfo
		songInfo.setLayout(new BoxLayout(songInfo, BoxLayout.Y_AXIS));
		// initialize all the current song info labels
		currTitle = new JLabel();
		currArtist = new JLabel();
		currAlbum = new JLabel();
		
		// get the content pane
		Container cPane = this.getContentPane();
		cPane.setLayout(new BorderLayout());
				
		// show the playlist on the right side of the frame
		cPane.add(BorderLayout.EAST, list);
		
		// filler message that will eventually be received over network
		received = new JLabel("Julia is listening to a song");
		
		
		// button to play ALL the songs in the playlist
		playAll = new JButton("Play All");
		// local class definition for the Action Listener for play
		class PlayAllAL implements ActionListener {
			public void actionPerformed (ActionEvent a) {
				// stop playing the current song
				// NEED A STOP METHOD										****
				while (currSong != null) {
					// change current song info
					songInfo = refreshSongInfo(currSong.song);
					// play the song in currNode
					currSong.song.play();
					// advance currSong
					currSong = currSong.next;
				}
			}
		}
		// create the action listener
		PlayAllAL playAllAL = new PlayAllAL();
		// add Action Listener to play button
		playAll.addActionListener(playAllAL);
		
		
		// button to play the next song in the playlist
		playNext = new JButton("Play Next Song");
		// local class definition for the Action Listener for play
		class PlayNextAL implements ActionListener {
			public void actionPerformed (ActionEvent a) {
				// stop playing the current song
				// NEED A STOP METHOD								****
				// move currSong to the next song
				currSong = currSong.next;
				// change current song info
				songInfo = refreshSongInfo(currSong.song);
				// if not at the end
				if (currSong!=null) {
					// play the song
					currSong.song.play();
				}
				// else if the end has been reached
				else {
					// go back to the beginning
					currSong = playlist.head;
					// play that
					currSong.song.play();
				}
			}
		}
		// create the action listener
		PlayNextAL playNextAL = new PlayNextAL();
		// add Action Listener to play button
		playNext.addActionListener(playNextAL);
		
		
		// button to play a random song in the playlist
		playRandom = new JButton("Play Random Song");
		// local class definition for the Action Listener for playRandom
		class PlayRandomAL implements ActionListener {
			public void actionPerformed (ActionEvent a) {
				// stop playing the current song
				// NEED A STOP METHOD										****
				// pick a random number from 1 to # of songs
				int random = (int)((Math.random()*playlist.size)+1);
				// move currSong to the first node
				currSong = playlist.head;
				// move currSong to the node chosen by random
				for (int i=0; i<random; i++) {
					currSong = currSong.next;
				}
				// change current song info
				songInfo = refreshSongInfo(currSong.song);
				// if not at the end
				if (currSong!=null) {
					// play the song
					currSong.song.play();
				}
				// else if the end has been reached
				else {
					// go back to the beginning
					currSong = playlist.head;
					// play that
					currSong.song.play();
				}
			}
		}
		// create the action listener
		PlayRandomAL playRandomAL = new PlayRandomAL();
		// add Action Listener to play button
		playNext.addActionListener(playRandomAL);
		
		
		// button to go back to the Add Songs interface
		goToAdd = new JButton("Make A New Playlist");
		// local class definition for an Action Listener for goToAdd
		class AddMoreAL implements ActionListener {
			public void actionPerformed(ActionEvent a) {
				AddSongFrame addFrame = new AddSongFrame();
				addFrame.go(500,500);
			}
		}
		// create action listener
		AddMoreAL goToAddAL = new AddMoreAL();
		// add action listener to the goToAdd button
		goToAdd.addActionListener(goToAddAL);
		
		
		// new panel to hold play buttons
		playButtons = new JPanel();
		// set Flow Layout
		playButtons.setLayout(new FlowLayout());
		// add buttons to panel
		playButtons.add(playAll);
		playButtons.add(playNext);
		playButtons.add(playRandom);
		
		// new panel to put all buttons and message at the bottom
		south = new JPanel();
		// give the south panel a Box Layout
		south.setLayout(new BoxLayout(south,BoxLayout.Y_AXIS));
		// add message to south panel
		south.add(received);
		// add play buttons to south panel
		south.add(playButtons);
		// add "go back" button to south panel
		south.add(goToAdd);
		// add panel with the message and button to the South area
		cPane.add(BorderLayout.SOUTH, south);
		
		// add the song information to the content pane
		cPane.add(BorderLayout.NORTH,songInfo);
		
		// put image in center
		// image code not ready yet, text instead						*****
		JTextArea coverArt = new JTextArea("COVER ART HERE");
		cPane.add(coverArt);
		
		// exit operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		// make visible
		this.setVisible(true);
	}
	
	public JPanel refreshSongInfo (Song song)
	{
		// make labels for the current song information
		currTitle = new JLabel("Current song: " + song.getTitle());
		currArtist = new JLabel("\nArtist: " + song.getArtist());
		currAlbum = new JLabel("\nAlbum: " + song.getAlbum());
		
		// add the current song info to the panel
		songInfo.add(currTitle);
		songInfo.add(currArtist);
		songInfo.add(currAlbum);
		
		// return the panel
		return songInfo;
	}
}
