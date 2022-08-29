package com.TrovãoGamesStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.TrovãoGameStudios.graficos.Spritesheet;
import com.TrovãoGameStudios.main.Game;
import com.TrovãoGameStudios.world.Camera;
import com.TrovãoGameStudios.world.World;


public class Player extends Entity {

	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1;
	
	public int dir = right_dir;
	public double speed = 1.2;
	
	
	private int frames = 0,maxFrames=5,index=0,maxIndex=3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	
	private BufferedImage playerDamage;
	
	private boolean arma = false;
	
	public int bala = 0 ;
	
	public boolean dano =  false;
	private int danoFrames = 0;
	
	
	public boolean atirou = false;
	
	
	
	public double vida = 100,maxlife=100;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage [4];
		
		
		playerDamage = Game.spritesheet.getSprite(0,16,16, 16);
		
		
		
		for(int i = 0;i<4;i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
				
		}
		for(int i = 0;i<4;i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
					
			}
				
				
	}
	
	public void tick() {
		moved = false;
		if(right && World.isFree((int) (x+speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
			
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
			
		if(up && World.isFree(this.getX(), (int)(y-speed))) { 
			moved = true;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed))) {
			moved = true;
			y+=speed;
		
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
		
		checkItemslife();
		checkItemsAmmo();
		checkItemsArma();
		
		if(dano) {
			
			
			this.danoFrames++;
			if(this.danoFrames == 10) {
				
				this.danoFrames = 0;
				dano = false;
				
				
			}
			
		}
		
		
		if(atirou && arma && bala > 0 ) {
			
			//criar bala e atirar
			
			bala--;
			
			atirou = false;
			
			
			//System.out.println("atirou!!");
			
			int dx = 0;
			
			int px = 0;
			
			int py = 3;
			
			if(dir == right_dir) {
				
			px = 18;			
			 dx = 1;
				
			}else {
				
			px = -8;
			dx = -1;
				
			}
			
			Atirando bullet = new Atirando(this.getX() + px,this.getY()+ py,3,3,null,dx,0);
		
			Game.Balas.add(bullet);			
		}
		
		
		if(vida <= 0) {
			
			Game.entities.clear();
			
			Game.enemies.clear();
			
			Game.entities = new ArrayList<Entity>();
			
			Game.enemies = new ArrayList<Enemy>();
			
			Game.spritesheet = new Spritesheet("/SpriteSheet.png");
	      
			Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32,0,16,16));
			
			Game.entities.add(Game.player);
	
			Game.world = new World("/map.png");
			
			
			
			return;
			
			
		}
		
		
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
		
		
		
	}
	public void checkItemsArma() {
		
        for(int i = 0; i< Game.entities.size();i++) {
	
	
	      Entity atual = Game.entities.get(i);
	      if(atual instanceof Weppon) {
		
		
		if (Entity.isColidding(this, atual)) {
			
			arma = true;
			
			
			Game.entities.remove(atual);
		}
	  }	


  }

}
	
	
	public void checkItemsAmmo() {
		
                for(int i = 0; i< Game.entities.size();i++) {
			
			
			      Entity atual = Game.entities.get(i);
			      if(atual instanceof Bala) {
				
				
				if (Entity.isColidding(this, atual)) {
					
					bala+=10;
					System.out.println("Munição: " +bala);
					Game.entities.remove(atual);
				}
			  }	
		
		
          }
		
	}
	
	
	
	public void checkItemslife() {
		
		
		for(int i = 0; i< Game.entities.size();i++) {
			
			
			Entity e = Game.entities.get(i);
			if(e instanceof Life) {
				
				
				if (Entity.isColidding(this, e)) {
					
					
					vida +=8;
					if(vida >= 100)
						vida = 100;
					Game.entities.remove(i);
					return;
					
					
				}
				
			}
			
			
		}
		
		
	}
	
	
	
	
	public void render(Graphics g) {
		if(!dano) {
		if(dir == right_dir) {
		g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		
		if(arma) {
			
			//desenho arma direita
			 g.drawImage(Entity.GUN_RIGTH,this.getX()+8 - Camera.x ,this.getY() - Camera.y,null);
			
		}
		
		}
		else if (dir == left_dir) {
			
	    g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
	    
	    if(arma) {
	    	
	    	//desenho arma esquerda
	   
	    	 g.drawImage(Entity.GUN_LEFT,this.getX()-10 - Camera.x,this.getY() - Camera.y,null);
	    }
			
		}
		}else {
			
			g.drawImage(playerDamage, this.getX()-Camera.x, this.getY() - Camera.y, null);
			
			
		}
	}

}
