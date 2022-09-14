package com.TrovãoGamesStudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.TrovãoGameStudios.main.Game;
import com.TrovãoGameStudios.world.Camera;
import com.TrovãoGameStudios.world.World;

public class Enemy extends Entity {
	
	
	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1;
	private boolean moved = false;
	public int dir = right_dir;
	public double speed = 0.7;
	
	private int maskx = 8,masky = 8 , maskw= 10 , maskh = 10;
	
	private int frames = 0,maxFrames=5,index=0,maxIndex=3;
	
	private BufferedImage[] rhightEnemy;
	private BufferedImage[] leftEnemy;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rhightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage [4];
		for(int i = 0;i<4;i++) {
			
			leftEnemy[i] = Game.spritesheet.getSprite(33 + (i*16), 33, 16, 16);
			
		}
		
		for(int i = 0;i<4;i++) {
			
			rhightEnemy[i] = Game.spritesheet.getSprite(33 + (i*16), 49, 16, 16);
			}
				
				
	}
	
	public void tick() {
		
		
			
		/*maskx = 8;masky = 8 ; maskw= 7 ; maskh = 7;*/
			
		moved = false;
		
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed),this.getY())
				&& !isColidding((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
			
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed),this.getY())
				&& !isColidding((int)(x-speed),this.getY())) {
			
			moved = true;
			dir = left_dir;
			x-=speed;
			
			
		}
		 if((int)y < Game.player.getY()&& World.isFree(this.getX(),(int)(y+speed))
				 && !isColidding(this.getX(),(int)(y+speed))) {
			 moved = true;
			y+=speed;
			
		}
		else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-speed))
				&& !isColidding(this.getX(),(int)(y-speed))) {
			moved = true;
			y-=speed;
			
			
		}
		 
		 
		 if(moved) {
				
				frames++;
				if(frames == maxFrames) {
					
					frames = 0;
					index++;
					if(index > maxIndex) 
						
						index = 0;
					
					
				}
				}
		
	}
	public boolean isColidding(int xnext,int ynext ) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky ,maskw,maskh);
		
		for (int i= 0;i< Game.enemies.size();i++) {
			
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY()+ masky,maskw,maskh);
		    if(enemyCurrent.intersects(targetEnemy)) {
		    	return true;
		    	
		    }
		 
		}
		
		return false;
		
	}
	public void render(Graphics g) {
	
		if(dir == right_dir) {
			g.drawImage(rhightEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
			else if (dir == left_dir) {
				
		    g.drawImage(leftEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				
			}
		
	//g.setColor(Color.blue);
	//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y,maskw,maskh);
		
		
		
	}
	}

