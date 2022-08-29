package com.Trov�oGamesStudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.Trov�oGameStudios.main.Game;
import com.Trov�oGameStudios.world.Camera;

public class Atirando extends Entity{

	
	private int dx;
	private int dy;
	private double spd = 4;
	private int life = 30,curLife = 0;
	
	public Atirando(int x, int y, int width, int height, BufferedImage sprite,int dx ,int dy) {
		super(x, y, width, height, sprite);	
		this.dx = dx;
		this.dy =  dy;
		
		
	}
	
	
	
	public void  tick() {

		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(curLife == life) {
			
			
			Game.Balas.remove(this);
			return;
			
		}
		
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y, width, height);
		
	}
	
}
