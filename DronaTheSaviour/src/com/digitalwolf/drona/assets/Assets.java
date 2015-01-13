package com.digitalwolf.drona.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.digitalwolf.gamedata.Settings;

public class Assets {

    private final static String FILE_SPRITE_ATLAS = "data/imageatlas.sprites";	
    private final static String FILE_UI_ATLAS = "data/uiAtlas.sprites";
	private final static String FILE_UI_SKIN = "skin/uiskin.json";

	public static TextureAtlas imageAtlas;
	public static TextureAtlas uiSpriteAtlas;
	public static Skin skin;
	
	//Defining the Audio Files here
	public static Music music;
	public static Sound appearing;
	public static Sound dronaFall;
	public static Sound evilLaugh;
	public static Sound grab;
	public static Sound shoot;
	public static Sound victory;
	public static Sound killed,killed1;
	
	//Defining Fonts here
	public static BitmapFont gameFont,whiteFont,smallFont;
	
    public static TextureRegion bg;
    public static TextureRegion rating,star1,star2,star3,star4,star5;
    public static TextureRegion parallax_bg,parallaxbg2;
	public static TextureRegion splash;
	public static TextureRegion magicalPower,lock_icon;
	public static TextureRegion play_button1;
	public static TextureRegion play_button2;
	public static TextureRegion credits_button1;
	public static TextureRegion credits_button2;
	public static TextureRegion highscores_button1;
	public static TextureRegion highscores_button2;
	public static TextureRegion logo;
	public static TextureRegion sound_on;
	public static TextureRegion sound_off;
	public static TextureRegion player;
	public static TextureRegion pawns1,pawns2;
	public static TextureRegion powerup;
	public static TextureRegion knight;
	public static TextureRegion explosion;
	public static TextureRegion nothing;
	public static TextureRegion pause_button;
	public static TextureRegion infoDronaDead,infoKilledByBossEnemy,infoKilledByConspirators,infoKilledByPawns,secondEnemyFace;
	public static TextureRegion highscore_table;
	public static TextureRegion emptyBox,rectangle_button,rectangle_button2;
	public static TextureRegion redboard,transparent;
	public static TextureRegion back_button;
	public static TextureRegion enerylevel,enerylevel1,enerylevel2,enerylevel3,enerylevel4,enerylevel5;
	public static TextureRegion poof1,poof2;
	public static TextureRegion dragon;
	public static Animation dragonAnimation;
	public static TextureRegion conspirator;
	public static Animation conspiratorAnim;
	public static TextureRegion blackDragon;
	public static Animation blackDragonAnim;
	public static TextureRegion credits;
	public static Animation dronaFlying, explosionAnim;
	public static Animation pawn1Flying,pawn2Flying;
	public static Animation powerUpAnimation;
	public static Animation bossEnemyFlying,bossEnemyAppearing,bossEnemyFlyAway;
	public static Animation evilDemonFlying;
	public static Animation poofAnimation,splashAnim;
	
	public static TextureRegion flyButton, shoot_button;
	public static TextureRegion weapon1, weapon2,weapon3;

	public static Texture loadTexture(String file) {
		return new Texture(Gdx.files.internal(file));
	}

	
	public static TextureAtlas getSpriteAtlas() {
		if (imageAtlas == null) {
			imageAtlas = new TextureAtlas(
					Gdx.files.internal(FILE_SPRITE_ATLAS));
		}
		return imageAtlas;
	}
	
	public static TextureAtlas getUISpriteAtlas() {
		if (uiSpriteAtlas == null) {
			uiSpriteAtlas = new TextureAtlas(
					Gdx.files.internal(FILE_UI_ATLAS));
		}
		return uiSpriteAtlas;
	}
	
	public static Skin getSkin() {
		if (skin == null) {
			FileHandle skinFile = Gdx.files.internal(FILE_UI_SKIN);
			skin = new Skin(skinFile);
		}
		return skin;
	}

	public static void loadAll() {
		relaseResources();
		loadImages();
		loadFonts();
		loadAnimations();
		loadSoundsAndMusics();
	}

	private static void loadSoundsAndMusics() {
		// Load all the Music & Audio Files Here
		music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		music.setLooping(true);
		//Start Playing the Music if the sound is enabled
		if (Settings.isSoundEnabled()) Assets.music.play();
		
		appearing = Gdx.audio.newSound(Gdx.files.internal("data/appearing.mp3"));
		dronaFall = Gdx.audio.newSound(Gdx.files.internal("data/dronaFall.ogg"));
		evilLaugh = Gdx.audio.newSound(Gdx.files.internal("data/evilLaugh.mp3"));
		grab = Gdx.audio.newSound(Gdx.files.internal("data/grab.ogg"));
		shoot = Gdx.audio.newSound(Gdx.files.internal("data/shoot.ogg"));
		victory = Gdx.audio.newSound(Gdx.files.internal("data/victory.mp3"));
		killed = Gdx.audio.newSound(Gdx.files.internal("data/killed.mp3"));
		killed1 = Gdx.audio.newSound(Gdx.files.internal("data/killed1.mp3"));
	}

	public static void playSound (Sound sound) {
		if (Settings.isSoundEnabled()) sound.play(1);
	}
	
	private static void loadFonts() {
		// Load the fonts
		gameFont = new BitmapFont(Gdx.files.internal("data/gamefont.fnt"),
				Gdx.files.internal("data/gamefont.png"), false);
		
		whiteFont = new BitmapFont(Gdx.files.internal("data/whiteFont.fnt"),
				Gdx.files.internal("data/whiteFont.png"), false);
		
		smallFont = new BitmapFont(Gdx.files.internal("data/smallFont.fnt"),
				Gdx.files.internal("data/smallFont.png"), false);
	}

	private static void loadAnimations(){
		//Load the Animations from the Textures in the Atlas

	    TextureRegion[][] knightRegions = Assets.player.split(288/3, 384/4);		
	    bossEnemyFlying = new Animation(0.30f, knightRegions[1][0], knightRegions[1][1], knightRegions[1][2]);		
	    bossEnemyFlying.setPlayMode(Animation.LOOP);
	    
	    bossEnemyAppearing = new Animation(0.30f, knightRegions[0][0], knightRegions[0][1], knightRegions[0][2]);		
	    bossEnemyAppearing.setPlayMode(Animation.LOOP);
	    
	    bossEnemyFlyAway = new Animation(0.30f, knightRegions[3][0], knightRegions[3][1], knightRegions[3][2]);		
	    bossEnemyFlyAway.setPlayMode(Animation.LOOP);
	    
		TextureRegion[][] dronaSpriteRegions = Assets.knight.split(2048/8, 275);
		dronaFlying  = new Animation(0.15f, dronaSpriteRegions[0][0],dronaSpriteRegions[0][1],dronaSpriteRegions[0][2],
				dronaSpriteRegions[0][3],dronaSpriteRegions[0][4],dronaSpriteRegions[0][5],
				dronaSpriteRegions[0][6],dronaSpriteRegions[0][7]);
		
		dronaFlying.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] splashRegions = Assets.splash.split(282/6, 66);
		splashAnim  = new Animation(0.15f, splashRegions[0][0], splashRegions[0][1], splashRegions[0][2],
				splashRegions[0][3], splashRegions[0][4], splashRegions[0][5]);
		splashAnim.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] pawnsRegions = Assets.pawns1.split(288/3, 384/4);
		pawn1Flying  = new Animation(0.15f, pawnsRegions[0][0], pawnsRegions[0][1], pawnsRegions[0][2]);
		pawn1Flying.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] pawnsRegions2 = Assets.pawns2.split(512/4, 112);
		pawn2Flying  = new Animation(0.15f, pawnsRegions2[0][0], pawnsRegions2[0][1], pawnsRegions2[0][2], pawnsRegions2[0][3]);
		pawn2Flying.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] powerUpRegions = Assets.powerup.split(837/9, 63);	
		powerUpAnimation  = new Animation(0.15f,powerUpRegions[0][0],powerUpRegions[0][1],powerUpRegions[0][2]
						,powerUpRegions[0][3],powerUpRegions[0][4],powerUpRegions[0][5]
						,powerUpRegions[0][6],powerUpRegions[0][7],powerUpRegions[0][8]);
		powerUpAnimation.setPlayMode(Animation.LOOP);
		
		
		TextureRegion[][] explosionRegions = Assets.explosion.split(64,64);
		explosionAnim  = new Animation(0.1f, 
				explosionRegions[0][0],explosionRegions[0][1],explosionRegions[0][2],explosionRegions[0][3],explosionRegions[0][4],
				explosionRegions[1][0],explosionRegions[1][1],explosionRegions[1][2],explosionRegions[1][3],explosionRegions[1][4],
				explosionRegions[2][0],explosionRegions[2][1],explosionRegions[2][2],explosionRegions[2][3],explosionRegions[2][4],
				explosionRegions[3][0],explosionRegions[3][1],explosionRegions[3][2],explosionRegions[3][3],explosionRegions[3][4],
				explosionRegions[4][0],explosionRegions[4][1],explosionRegions[4][2],explosionRegions[4][3],explosionRegions[4][4]);
	
		explosionAnim.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] dragonRegions = Assets.dragon.split(100,100);
		dragonAnimation = new Animation(0.15f, dragonRegions[0][0], dragonRegions[0][1], dragonRegions[0][2], dragonRegions[0][3], dragonRegions[0][4]);
		dragonAnimation.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] conspiratorRegions = Assets.conspirator.split(500/4, 564/4);	
		conspiratorAnim  = new Animation(0.15f,conspiratorRegions[0][0],conspiratorRegions[0][1],conspiratorRegions[0][2],conspiratorRegions[0][3]
						,conspiratorRegions[1][0],conspiratorRegions[1][1],conspiratorRegions[1][2],conspiratorRegions[1][3]
						,conspiratorRegions[2][0],conspiratorRegions[2][1],conspiratorRegions[2][2],conspiratorRegions[2][3],
						conspiratorRegions[3][0],conspiratorRegions[3][1],conspiratorRegions[3][2],conspiratorRegions[3][3]);
		conspiratorAnim.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] blackdragonRegions = Assets.blackDragon.split(2048/8, 275);
		
		blackDragonAnim  = new Animation(0.15f, blackdragonRegions[0][0],blackdragonRegions[0][1],blackdragonRegions[0][2],
				blackdragonRegions[0][3],blackdragonRegions[0][4],blackdragonRegions[0][5],
				blackdragonRegions[0][6],blackdragonRegions[0][7]);		
		blackDragonAnim.setPlayMode(Animation.LOOP);
		
		poofAnimation = new Animation(0.15f, poof1, poof2);
		poofAnimation.setPlayMode(Animation.LOOP);
		
		TextureRegion[][] ratingRegions = Assets.rating.split(400,85);
		
		star1 = ratingRegions[4][0];
		star2 = ratingRegions[3][0];
		star3 = ratingRegions[2][0];
		star4 = ratingRegions[1][0];
		star5 = ratingRegions[0][0];
		
	}

	private static void loadImages() {
		// Load the static Images/Texture Regions
		
		bg = getSpriteAtlas().findRegion("bg");
		parallax_bg = getSpriteAtlas().findRegion("parallax_bg");
		parallaxbg2 = getSpriteAtlas().findRegion("parallaxbg2");
		
		sound_on = getUISpriteAtlas().findRegion("button_on");
		sound_off = getUISpriteAtlas().findRegion("button_off");
		play_button1 = getUISpriteAtlas().findRegion("startgame_button");
		play_button2 = getUISpriteAtlas().findRegion("startgame_button2");
		credits_button1 = getUISpriteAtlas().findRegion("button_credits");
		credits_button2 = getUISpriteAtlas().findRegion("button_credits2");
		highscores_button1 = getUISpriteAtlas().findRegion("button_highscore");
		highscores_button2 = getUISpriteAtlas().findRegion("button_highscore2");
		logo = getSpriteAtlas().findRegion("logo");
		player = getSpriteAtlas().findRegion("drona");
		pawns1 = getSpriteAtlas().findRegion("pawns1");
		pawns2 = getSpriteAtlas().findRegion("pawns2");
		
		powerup = getSpriteAtlas().findRegion("powerup");
		
		flyButton = getUISpriteAtlas().findRegion("flyButton");
		shoot_button = getUISpriteAtlas().findRegion("shoot_button");
		
		weapon1 = getUISpriteAtlas().findRegion("weapon1");
		weapon2 = getUISpriteAtlas().findRegion("weapon2");
		weapon3 = getUISpriteAtlas().findRegion("weapon3");
		
		
		knight = getSpriteAtlas().findRegion("knightEnemy");
		explosion = getSpriteAtlas().findRegion("explosion");
		
		nothing  = getSpriteAtlas().findRegion("nothing");
		
		magicalPower  = getSpriteAtlas().findRegion("magicalpower");
		emptyBox  = getUISpriteAtlas().findRegion("emptyBox");
		
		rectangle_button = getUISpriteAtlas().findRegion("button_menu");
		rectangle_button2 = getUISpriteAtlas().findRegion("button_menu2");
		
		back_button  = getUISpriteAtlas().findRegion("back_button");
		transparent  = getUISpriteAtlas().findRegion("transparent");
		
		enerylevel  = getUISpriteAtlas().findRegion("enerylevel");
		TextureRegion[][] energyLevelRegions = Assets.enerylevel.split(310/5,128);
		
		enerylevel1  = energyLevelRegions[0][0];
		enerylevel2  = energyLevelRegions[0][1];
		enerylevel3  = energyLevelRegions[0][2];
		enerylevel4  = energyLevelRegions[0][3];
		enerylevel5  = energyLevelRegions[0][4];
		
		pause_button  = getUISpriteAtlas().findRegion("pause_button");
		highscore_table = getUISpriteAtlas().findRegion("highscore_table");
		
		dragon = getSpriteAtlas().findRegion("dragon");
		blackDragon = getSpriteAtlas().findRegion("knightEnemy2");
		
		secondEnemyFace = getSpriteAtlas().findRegion("aispriteface");
		conspirator = getSpriteAtlas().findRegion("conspirator");
		splash = getSpriteAtlas().findRegion("splash");
		
		infoDronaDead = getSpriteAtlas().findRegion("infoDronaDead");
		infoKilledByBossEnemy = getSpriteAtlas().findRegion("infoKilledByBossEnemy");
		infoKilledByConspirators = getSpriteAtlas().findRegion("infoKilledByConspirators");
		infoKilledByPawns = getSpriteAtlas().findRegion("infoKilledByPawns");
		
		poof1 = getUISpriteAtlas().findRegion("poof-1");
		poof2 = getUISpriteAtlas().findRegion("poof-2");
		lock_icon  = getUISpriteAtlas().findRegion("lock_icon");
		credits = getUISpriteAtlas().findRegion("credits");
		rating = getUISpriteAtlas().findRegion("rating");
	}


	private static void relaseResources() {
		skin = null;
		imageAtlas = null;
		uiSpriteAtlas = null;
	}
}
