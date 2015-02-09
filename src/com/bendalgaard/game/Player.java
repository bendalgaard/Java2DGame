package com.bendalgaard.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bendalgaard.game.gfx.SpriteSheet;

public class Player {
	
	private double x;
	private double y;
	private SpriteSheet ss;
	private BufferedImage playerImage;
	
	public Player(double x, double y, SpriteSheet ss) {
		this.x = x;
		this.y = y;
		this.ss = ss;
		playerImage = this.ss.grabImage(1, 1, 70, 70);
	}

	public void tick() {
		x++;
	}
	
	public void render(Graphics g) {
		g.drawImage(playerImage, (int)x, (int)y, null);
	}
}
