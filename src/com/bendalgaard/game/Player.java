package com.bendalgaard.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bendalgaard.game.gfx.SpriteSheet;

public class Player {
	
	private double x;
	private double y;
	private double velX;
	private double velY;
	
	private SpriteSheet ss;
	private BufferedImage playerImage;
	
	private boolean x_direction_positive = true;
	
	public Player(double x, double y, SpriteSheet ss) {
		this.x = x;
		this.y = y;
		this.ss = ss;
		playerImage = this.ss.grabImage(1, 1, 70, 70);
	}

	public void tick() {
		x+=velX;
		y+=velY;
		
		
//		if (x_direction_positive) {
//			x+=2;
//		} else {
//			x-=2;
//		}
//		
//		if (x >= (Game.WIDTH * Game.SCALE - 70)) {
//			x_direction_positive = false;
//		}
//		if (x <= 0) {
//			x_direction_positive = true;
//		}
	}
	
	public void render(Graphics g) {
		g.drawImage(playerImage, (int)x, (int)y, null);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return y;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}
}
