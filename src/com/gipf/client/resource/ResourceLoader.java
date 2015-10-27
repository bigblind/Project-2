package com.gipf.client.resource;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader {

	public static File ON_VALID_MOVE, ON_INVALID_MOVE, BACKGROUND, WIN_SOUND, LOSE_SOUND;
	public static Image WHITE_STONE, BLACK_STONE, OUTER_DOT, WHITE_STONE_TRANSPARENT, BLACK_STONE_TRANSPARENT, GIPF_WHITE_STONE, GIPF_BLACK_STONE;
	public static Image MENU_ICON, SOUND_ICON, SOUND_MUTED_ICON, SETTINGS_ICON, HELP_ICON;
	public static Image MENU_ICON_SMALL, SOUND_ICON_SMALL, SOUND_MUTED_ICON_SMALL, SETTINGS_ICON_SMALL, HELP_ICON_SMALL;
	public static Image CHECK_ICON;
	public static Image ICON;
	
	public void init() throws IOException {
		WHITE_STONE = ImageIO.read(this.getResource("res/icons/WhiteStone2.png"));
		BLACK_STONE = ImageIO.read(this.getResource("res/icons/BlackStone2.png"));
		OUTER_DOT = ImageIO.read(this.getResource("res/icons/OuterDot.png"));
		
		MENU_ICON_SMALL = ImageIO.read(this.getResource("res/icons/Menu.png"));
		MENU_ICON = ImageIO.read(this.getResource("res/icons/Menu48.png"));
		SOUND_ICON_SMALL = ImageIO.read(this.getResource("res/icons/Volume.png"));
		SOUND_ICON = ImageIO.read(this.getResource("res/icons/Volume48.png"));
		SOUND_MUTED_ICON_SMALL = ImageIO.read(this.getResource("res/icons/MutedVolume.png"));
		SOUND_MUTED_ICON = ImageIO.read(this.getResource("res/icons/MutedVolume48.png"));
		SETTINGS_ICON_SMALL = ImageIO.read(this.getResource("res/icons/Settings.png"));
		SETTINGS_ICON = ImageIO.read(this.getResource("res/icons/Settings48.png"));
		HELP_ICON_SMALL = ImageIO.read(this.getResource("res/icons/QuestionMark.png"));
		HELP_ICON = ImageIO.read(this.getResource("res/icons/QuestionMark48.png"));
		CHECK_ICON =  ImageIO.read(this.getResource("res/icons/check64.png"));
		ICON = ImageIO.read(this.getResource("res/icons/Icon.png"));
		
		WHITE_STONE_TRANSPARENT = ImageIO.read(this.getResource("res/icons/WhiteStoneTransparent2.png"));
		BLACK_STONE_TRANSPARENT = ImageIO.read(this.getResource("res/icons/BlackStoneTransparent2.png"));
		
		GIPF_WHITE_STONE = ImageIO.read(this.getResource("res/icons/GipfWhiteStone.png"));
		GIPF_BLACK_STONE = ImageIO.read(this.getResource("res/icons/GipfBlackStone.png"));
		
		ON_INVALID_MOVE = new File("res/sounds/inValidMove.wav");
		BACKGROUND = new File("res/sounds/Background.wav");
		ON_VALID_MOVE = new File("res/sounds/ValidMove.wav");
		WIN_SOUND = new File("res/sounds/WinApplause.wav");
		LOSE_SOUND = new File("res/sounds/failSound.wav");
	}
	
//	public InputStream getResource(String path) {
//		return ResourceLoader.class.getClassLoader().getResourceAsStream(path);
//	}
	
	public File getResource(String path) {
		return new File(path);
	}
}
