package com.bendalgaard.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.swing.JFrame;

import com.bendalgaard.game.gfx.BufferedImageLoader;
import com.bendalgaard.game.gfx.SpriteSheet;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Game";

	private boolean running = false;
	private int tick_count = 0;
	
	private JFrame frame;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	private BufferedImage spriteSheet = null;
	private Player player = null;
	
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}
	
	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/stick_animations_by_wargamer.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		
		addKeyListener(new KeyInput(this));
		this.requestFocus();
		
		player = new Player(200, 200, ss);
	}

	@Override
	public void run() {
		init();
		long last_time = System.nanoTime();
		double ns_per_tick = 1000000000d/60d;
		
		int ticks = 0;
		int frames = 0;
		
		long last_timer = System.currentTimeMillis();
		double delta = 0;
		
		// Main game loop - 60 frames per second
		while (running) {
			long now = System.nanoTime();
			delta += (now - last_time) / ns_per_tick;
			last_time = now;
			boolean should_render = true;
			
			while (delta >= 1) {
				ticks++;
				delta--;
				tick();
				should_render = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (should_render) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - last_timer >= 1000) {
				last_timer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick() {
		tick_count++;
		player.tick();
//		for (int i=0; i<pixels.length; i++) {
//			pixels[i] = i + tick_count;
//			//pixels[i] = i * tick_count;
//		}
		
		
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			//Triple buffering for reduced tearing (image distortion) and reducing cross-image pixalation
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		player.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// WASD
		if (key == KeyEvent.VK_D) {
			player.setVelX(5);
		} else if (key == KeyEvent.VK_A) {
			player.setVelX(-5);
		} else if (key == KeyEvent.VK_W) {
			player.setVelY(-5);
		} else if (key == KeyEvent.VK_S) {
			player.setVelY(5);
		}
		// Arrows
		if (key == KeyEvent.VK_RIGHT) {
			player.setVelX(5);
		} else if (key == KeyEvent.VK_LEFT) {
			player.setVelX(-5);
		} else if (key == KeyEvent.VK_UP) {
			player.setVelY(-5);
		} else if (key == KeyEvent.VK_DOWN) {
			player.setVelY(5);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		// WASD
		if (key == KeyEvent.VK_D) {
			player.setVelX(0);
		} else if (key == KeyEvent.VK_A) {
			player.setVelX(0);
		} else if (key == KeyEvent.VK_W) {
			player.setVelY(0);
		} else if (key == KeyEvent.VK_S) {
			player.setVelY(0);
		}
		// Arrows
		if (key == KeyEvent.VK_RIGHT) {
			player.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			player.setVelX(0);
		} else if (key == KeyEvent.VK_UP) {
			player.setVelY(0);
		} else if (key == KeyEvent.VK_DOWN) {
			player.setVelY(0);
		}
	}

	public static void main(String[] args) {
		new Game().start();
	}

}
